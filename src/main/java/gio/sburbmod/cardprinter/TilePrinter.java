package gio.sburbmod.cardprinter;

//import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
//import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
//import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.EnumSkyBlock;

import javax.annotation.Nullable;

//import gio.sburbmod.cruxite.DowelPlain;

import java.awt.Color;
import java.util.Arrays;

/**
 * User: brandon3055 Date: 06/01/2015
 *
 * TileInventorySmelting is an advanced sided inventory that works like a
 * vanilla furnace except that it has 5 input and output slots, 4 fuel slots and
 * cooks at up to four times the speed. The input slots are used sequentially
 * rather than in parallel, i.e. the first slot cooks, then the second, then the
 * third, etc The fuel slots are used in parallel. The more slots burning in
 * parallel, the faster the cook time. The code is heavily based on
 * TileEntityFurnace.
 */
public class TilePrinter extends TileEntity implements IInventory, ITickable {
	private static final boolean CRUXTRUDER_DEBUG = true;
	// Create and initialize the itemStacks variable that will store store the
	// itemStacks
	public static final int FUEL_SLOTS_COUNT = 0;
	public static final int INPUT_SLOTS_COUNT = 1;
	public static final int OUTPUT_SLOTS_COUNT = 1;
	public static final int TOTAL_SLOTS_COUNT = FUEL_SLOTS_COUNT + INPUT_SLOTS_COUNT + OUTPUT_SLOTS_COUNT;

	public static final int FIRST_FUEL_SLOT = 0;
	public static final int FIRST_INPUT_SLOT = FIRST_FUEL_SLOT + FUEL_SLOTS_COUNT;
	public static final int FIRST_OUTPUT_SLOT = FIRST_INPUT_SLOT + INPUT_SLOTS_COUNT;

	/** The number of ticks the current item has been cooking */
	short cookTime;
	short fuelTime;
	int uses;
	/** The number of ticks required to cook an item */
	private static final short COOK_TIME_FOR_COMPLETION = 150; // vanilla value is 200 = 10 seconds
	private static final short STACK_FUEL_VALUE = 110; // vanilla value is 200 = 10 seconds
	private static final short SINGLE_FUEL_VALUE = 300; // vanilla value is 200 = 10 seconds
	private static final short ADDITIONAL_TIME_WITH_WEAR = 20; // vanilla value is 200 = 10 seconds

	private ItemStack[] itemStacks;

	public TilePrinter() {
		itemStacks = new ItemStack[TOTAL_SLOTS_COUNT];
		clear();
	}

	short getRequiredTime() {
		return (short) ((ADDITIONAL_TIME_WITH_WEAR * uses) + COOK_TIME_FOR_COMPLETION);
	}

	/**
	 * Returns the amount of cook time completed on the currently cooking item.
	 * 
	 * @return fraction remaining, between 0 - 1
	 */
	public double fractionOfCookTimeComplete() // used by gui
	{
		double fraction = cookTime / (double) getRequiredTime();
		return MathHelper.clamp(fraction, 0.0, 1.0);
	}

	public double fractionOfFuelRemaining() // used by gui
	{
		double fraction = fuelTime / (double) STACK_FUEL_VALUE;
		return MathHelper.clamp(fraction, 0.0, 1.0);
	}

	private void extrude() {
		Item cruxItem = gio.sburbmod.punchcard.StartupCommon.punchCard;
		// System.out.println(cruxItem.toString());

		ItemStack cruxiteStack = new ItemStack(cruxItem, 1);
		// System.out.println(cruxiteStack.toString());

		setInventorySlotContents(FIRST_OUTPUT_SLOT, cruxiteStack);
		uses++;
	}

	private boolean needsUpdate = false;

	// This method is called every tick to update the tile entity, i.e.
	// - see if the fuel has run out, and if so turn the furnace "off" and slowly
	// uncook the current item (if any)
	// - see if any of the items have finished smelting
	// It runs both on the server and the client.
	@Override
	public void update() {
		ItemStack output = itemStacks[FIRST_OUTPUT_SLOT];
		ItemStack input = itemStacks[FIRST_INPUT_SLOT];

		// if (input.getCount() == 64) {
		if (input.getCount() >= 1) {
			itemStacks[FIRST_INPUT_SLOT] = ItemStack.EMPTY;
			fuelTime += STACK_FUEL_VALUE;
		}

		if (output.isEmpty() && fuelTime != 0) {
			// We've extruded for one tick.
			cookTime++;
			fuelTime--;

			if (cookTime >= getRequiredTime()) {
				cookTime -= getRequiredTime();
				extrude();
				needsUpdate = true;
			}
		}

		if (needsUpdate) {
			// Update block state in world
			if (world.isRemote) {
				IBlockState iblockstate = this.world.getBlockState(pos);
				final int FLAGS = 3; // Block update, propogate change.
				world.notifyBlockUpdate(pos, iblockstate, iblockstate, FLAGS);
			}
			world.checkLightFor(EnumSkyBlock.BLOCK, pos);

			markDirty();
			needsUpdate = false;

			// System.out.println(System.currentTimeMillis() + ": " + cookTime);
		}

	}

	public int inventoryFullness() {
		// return 0;
		if (itemStacks[FIRST_OUTPUT_SLOT].isEmpty())
			return 0;
		return itemStacks[FIRST_OUTPUT_SLOT].getCount();
	}

	// Gets the number of slots in the inventory
	@Override
	public int getSizeInventory() {
		return itemStacks.length;
	}

	// returns true if all of the slots in the inventory are empty
	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : itemStacks) {
			if (!itemstack.isEmpty()) { // isEmpty()
				return false;
			}
		}

		return true;
	}

	// Gets the stack in the given slot
	@Override
	public ItemStack getStackInSlot(int i) {
		return itemStacks[i];
	}

	/**
	 * Removes some of the units from itemstack in the given slot, and returns as a
	 * separate itemstack
	 * 
	 * @param slotIndex
	 *            the slot number to remove the items from
	 * @param count
	 *            the number of units to remove
	 * @return a new itemstack containing the units removed from the slot
	 */
	@Override
	public ItemStack decrStackSize(int slotIndex, int count) {
		ItemStack itemStackInSlot = getStackInSlot(slotIndex);
		if (itemStackInSlot.isEmpty())
			return ItemStack.EMPTY; // isEmpty(), EMPTY_ITEM

		ItemStack itemStackRemoved;
		if (itemStackInSlot.getCount() <= count) { // getStackSize
			itemStackRemoved = itemStackInSlot;
			setInventorySlotContents(slotIndex, ItemStack.EMPTY); // EMPTY_ITEM
		} else {
			itemStackRemoved = itemStackInSlot.splitStack(count);
			if (itemStackInSlot.getCount() == 0) { // getStackSize
				setInventorySlotContents(slotIndex, ItemStack.EMPTY); // EMPTY_ITEM
			}
		}
		markDirty();
		needsUpdate = true;
		return itemStackRemoved;
	}

	// overwrites the stack in the given slotIndex with the given stack
	@Override
	public void setInventorySlotContents(int slotIndex, ItemStack itemstack) {
		itemStacks[slotIndex] = itemstack;
		if (!itemstack.isEmpty() && itemstack.getCount() > getInventoryStackLimit()) { // isEmpty(); getStackSize()
			itemstack.setCount(getInventoryStackLimit()); // setStackSize()
		}
		markDirty();
	}

	// This is the maximum number if items allowed in each slot
	// This only affects things such as hoppers trying to insert items you need to
	// use the container to enforce this for players
	// inserting items via the gui
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	// Return true if the given player is able to use this block. In this case it
	// checks that
	// 1) the world tileentity hasn't been replaced in the meantime, and
	// 2) the player isn't too far away from the centre of the block
	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		if (this.world.getTileEntity(this.pos) != this)
			return false;
		final double X_CENTRE_OFFSET = 0.5;
		final double Y_CENTRE_OFFSET = 0.5;
		final double Z_CENTRE_OFFSET = 0.5;
		final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
		return player.getDistanceSq(pos.getX() + X_CENTRE_OFFSET, pos.getY() + Y_CENTRE_OFFSET,
				pos.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
	}

	// Return true if the given stack is allowed to be inserted in the given slot
	// Unlike the vanilla furnace, we allow anything to be placed in the fuel slots
	static public boolean isItemValidForFuelSlot(ItemStack itemStack) {
		return true;
	}

	// Return true if the given stack is allowed to be inserted in the given slot
	// Unlike the vanilla furnace, we allow anything to be placed in the fuel slots
	static public boolean isItemValidForInputSlot(ItemStack itemStack) {
		return true;
	}

	// Return true if the given stack is allowed to be inserted in the given slot
	// Unlike the vanilla furnace, we allow anything to be placed in the fuel slots
	static public boolean isItemValidForOutputSlot(ItemStack itemStack) {
		return false;
	}

	// ------------------------------

	// This is where you save any data that you don't want to lose when the tile
	// entity unloads
	// In this case, it saves the state of the furnace (burn time etc) and the
	// itemstacks stored in the fuel, input, and output slots
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound parentNBTTagCompound) {
		super.writeToNBT(parentNBTTagCompound); // The super call is required to save and load the tiles location

		// // Save the stored item stacks

		// to use an analogy with Java, this code generates an array of hashmaps
		// The itemStack in each slot is converted to an NBTTagCompound, which is
		// effectively a hashmap of key->value pairs such
		// as slot=1, id=2353, count=1, etc
		// Each of these NBTTagCompound are then inserted into NBTTagList, which is
		// similar to an array.
		NBTTagList dataForAllSlots = new NBTTagList();
		for (int i = 0; i < this.itemStacks.length; ++i) {
			if (!this.itemStacks[i].isEmpty()) { // isEmpty()
				NBTTagCompound dataForThisSlot = new NBTTagCompound();
				dataForThisSlot.setByte("Slot", (byte) i);
				this.itemStacks[i].writeToNBT(dataForThisSlot);
				dataForAllSlots.appendTag(dataForThisSlot);
			}
		}
		// the array of hashmaps is then inserted into the parent hashmap for the
		// container
		parentNBTTagCompound.setTag("Items", dataForAllSlots);

		// Save everything else
		parentNBTTagCompound.setShort("CookTime", cookTime);
		parentNBTTagCompound.setShort("FuelTime", fuelTime);
		if (uses != 0)
			parentNBTTagCompound.setInteger("Uses", uses);
		return parentNBTTagCompound;
	}

	// This is where you load the data that you saved in writeToNBT
	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound); // The super call is required to save and load the tiles location
		final byte NBT_TYPE_COMPOUND = 10; // See NBTBase.createNewByType() for a listing
		NBTTagList dataForAllSlots = nbtTagCompound.getTagList("Items", NBT_TYPE_COMPOUND);

		Arrays.fill(itemStacks, ItemStack.EMPTY); // set all slots to empty EMPTY_ITEM
		for (int i = 0; i < dataForAllSlots.tagCount(); ++i) {
			NBTTagCompound dataForOneSlot = dataForAllSlots.getCompoundTagAt(i);
			byte slotNumber = dataForOneSlot.getByte("Slot");
			if (slotNumber >= 0 && slotNumber < this.itemStacks.length) {
				this.itemStacks[slotNumber] = new ItemStack(dataForOneSlot);
			}
		}

		// Load everything else. Trim the arrays (or pad with 0) to make sure they have
		// the correct number of elements
		cookTime = nbtTagCompound.getShort("CookTime");
		fuelTime = nbtTagCompound.getShort("FuelTime");
		uses = nbtTagCompound.getInteger("Uses");
	}

	// // When the world loads from disk, the server needs to send the TileEntity
	// information to the client
	// // it uses getUpdatePacket(), getUpdateTag(), onDataPacket(), and
	// handleUpdateTag() to do this
	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound updateTagDescribingTileEntityState = getUpdateTag();
		final int METADATA = 0;
		return new SPacketUpdateTileEntity(this.pos, METADATA, updateTagDescribingTileEntityState);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		NBTTagCompound updateTagDescribingTileEntityState = pkt.getNbtCompound();
		handleUpdateTag(updateTagDescribingTileEntityState);
	}

	/*
	 * Creates a tag containing the TileEntity information, used by vanilla to
	 * transmit from server to client Warning - although our getUpdatePacket() uses
	 * this method, vanilla also calls it directly, so don't remove it.
	 */
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		return nbtTagCompound;
	}

	/*
	 * Populates this TileEntity with information from the tag, used by vanilla to
	 * transmit from server to client Warning - although our onDataPacket() uses
	 * this method, vanilla also calls it directly, so don't remove it.
	 */
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		this.readFromNBT(tag);
	}
	// ------------------------

	// set all slots to empty
	@Override
	public void clear() {
		Arrays.fill(itemStacks, ItemStack.EMPTY); // EMPTY_ITEM
	}

	// will add a key for this container to the lang file so we can name it in the
	// GUI
	@Override
	public String getName() {
		return "container.sburbmod_cardprinter.name";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	// standard code to look up what the human-readable name is
	@Nullable
	@Override
	public ITextComponent getDisplayName() {
		return this.hasCustomName() ? new TextComponentString(this.getName())
				: new TextComponentTranslation(this.getName());
	}

	// Fields are used to send non-inventory information from the server to
	// interested clients
	// The container code caches the fields and sends the client any fields which
	// have changed.
	// The field ID is limited to byte, and the field value is limited to short. (if
	// you use more than this, they get cast down
	// in the network packets)
	// If you need more than this, or shorts are too small, use a custom packet in
	// your container instead.

	private static final byte COOK_FIELD_ID = 0;
	private static final byte FIRST_BURN_TIME_REMAINING_FIELD_ID = 1;
	private static final byte FIRST_BURN_TIME_INITIAL_FIELD_ID = FIRST_BURN_TIME_REMAINING_FIELD_ID
			+ (byte) FUEL_SLOTS_COUNT;
	private static final byte NUMBER_OF_FIELDS = FIRST_BURN_TIME_INITIAL_FIELD_ID + (byte) FUEL_SLOTS_COUNT;

	@Override
	public int getField(int id) {
		if (id == COOK_FIELD_ID)
			return cookTime;
		System.err.println("Invalid field ID in TileInventorySmelting.getField:" + id);
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		if (id == COOK_FIELD_ID) {
			cookTime = (short) value;
		} else {
			System.err.println("Invalid field ID in TileInventorySmelting.setField:" + id);
		}
	}

	@Override
	public int getFieldCount() {
		return NUMBER_OF_FIELDS;
	}

	// -----------------------------------------------------------------------------------------------------------
	// The following methods are not needed for this example but are part of
	// IInventory so they must be implemented

	// Unused unless your container specifically uses it.
	// Return true if the given stack is allowed to go in the given slot
	@Override
	public boolean isItemValidForSlot(int slotIndex, ItemStack itemstack) {
		return false;
	}

	/**
	 * This method removes the entire contents of the given slot and returns it.
	 * Used by containers such as crafting tables which return any items in their
	 * slots when you close the GUI
	 * 
	 * @param slotIndex
	 * @return
	 */
	@Override
	public ItemStack removeStackFromSlot(int slotIndex) {
		ItemStack itemStack = getStackInSlot(slotIndex);
		if (!itemStack.isEmpty())
			setInventorySlotContents(slotIndex, ItemStack.EMPTY); // isEmpty(); EMPTY_ITEM
		return itemStack;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

}
