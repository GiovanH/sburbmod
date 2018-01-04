package gio.sburbmod.cardprinter;

import gio.sburbmod.SburbMod;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//import javax.annotation.Nullable;

/**
 * User: brandon3055 Date: 06/01/2015
 *
 * BlockInventoryAdvanced is an advanced furnace with 5 input, 4 output and 4
 * fuel slots that smelts at twice the speed of a regular furnace. The block
 * itself doesn't do much more then any regular block except create a tile
 * entity when placed, open a gui when right clicked and drop tne inventory's
 * contents when harvested. Everything else is handled by the tile entity.
 *
 * The block model will change appearance depending on how many fuel slots are
 * burning. The amount of "block light" produced by the furnace will also
 * depending on how many fuel slots are burning.
 *
 * //Note that in 1.10.*, extending BlockContainer can cause rendering problems
 * if you don't extend getRenderType() // If you don't want to extend
 * BlockContainer, make sure to add the tile entity manually, // using
 * hasTileEntity() and createTileEntity(). See BlockContainer for a couple of
 * other important methods you may // need to implement.
 */
public class BlockPrinter extends BlockContainer {
	public BlockPrinter() {
		super(Material.CIRCUITS);
		this.setCreativeTab(CreativeTabs.DECORATIONS);
		this.setHardness(1.0F);
	}

	// Called when the block is placed or loaded client side to get the tile entity
	// for the block
	// Should return a new instance of the tile entity for the block
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TilePrinter();
	}

	// Called when the block is right clicked
	// In this block it is used to open the blocks gui when right clicked by a
	// player
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {

		// Uses the gui handler registered to your mod to open the gui for the given gui
		// id
		// open on the server side only (not sure why you shouldn't open client side
		// too... vanilla doesn't, so we better not either)
		if (worldIn.isRemote)
			return true;

		playerIn.openGui(SburbMod.instance, GuiHandlerPrinter.getGuiID(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	// This is where you can do something when the block is broken. In this case
	// drop the inventory's contents
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof IInventory) {
			InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) tileEntity);
		}

		// if (inventory != null){
		// // For each slot in the inventory
		// for (int i = 0; i < inventory.getSizeInventory(); i++){
		// // If the slot is not empty
		// if (inventory.getStackInSlot(i) != null)
		// {
		// // Create a new entity item with the item stack in the slot
		// EntityItem item = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.5,
		// pos.getZ() + 0.5, inventory.getStackInSlot(i));
		//
		// // Apply some random motion to the item
		// float multiplier = 0.1f;
		// float motionX = worldIn.rand.nextFloat() - 0.5f;
		// float motionY = worldIn.rand.nextFloat() - 0.5f;
		// float motionZ = worldIn.rand.nextFloat() - 0.5f;
		//
		// item.motionX = motionX * multiplier;
		// item.motionY = motionY * multiplier;
		// item.motionZ = motionZ * multiplier;
		//
		// // Spawn the item in the world
		// worldIn.spawnEntityInWorld(item);
		// }
		// }
		//
		// // Clear the inventory so nothing else (such as another mod) can do anything
		// with the items
		// inventory.clear();
		// }

		// Super MUST be called last because it removes the tile entity

		this.dropBlockAsItem(worldIn, pos, state, 0);
		super.breakBlock(worldIn, pos, state);
	}

	public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, IBlockAccess worldIn, BlockPos pos,
			IBlockState state, int fortune) {
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TilePrinter) {
				TilePrinter tilePrinter = (TilePrinter) tileentity;

				Item cruxtruderItem = Item.REGISTRY.getObject(new ResourceLocation("sburbmod:cardprinter"));
				// System.out.println(cruxItem.toString());

				ItemStack itemstack = new ItemStack(cruxtruderItem, 1);

				itemstack.setTagCompound(new NBTTagCompound());
				itemstack.getTagCompound().setInteger("Uses", tilePrinter.uses);

				drops.add(itemstack);
			}
		}
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		final TilePrinter tile = (TilePrinter) world.getTileEntity(pos);
		if (stack.hasTagCompound())
			tile.uses = stack.getTagCompound().getInteger("Uses");
	}

	// ------------------------------------------------------------
	// The code below isn't necessary for illustrating the Inventory Furnace
	// concepts, it's just used for rendering.
	// For more background information see MBE03

	// we will give our Block a property which tracks the number of burning sides, 0
	// - 4.
	// This will affect the appearance of the block model, but does not need to be
	// stored in metadata (it's stored in
	// the tileEntity) so we only need to implement getActualState.
	// getStateFromMeta, getMetaFromState aren't required
	// but we give defaults anyway because the base class getMetaFromState gives an
	// error if we don't

	// update the block state depending on the number of slots which contain burning
	// fuel
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof TilePrinter) {
			TilePrinter tileCruxtruder = (TilePrinter) tileEntity;
			int inventoryFilled = tileCruxtruder.inventoryFullness();
			// System.out.println(System.currentTimeMillis() + ": " + inventoryFilled);
			inventoryFilled = MathHelper.clamp(inventoryFilled, 0, 1);
			return getDefaultState().withProperty(INVENTORY_FILLED, inventoryFilled);
		}
		return state;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState();
		// return this.getDefaultState().withProperty(INVENTORY_FILLED,
		// Integer.valueOf(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
		// return ((Integer)state.getValue(INVENTORY_FILLED)).intValue();
	}

	// necessary to define which properties your blocks use
	// will also affect the variants listed in the blockstates model file. See MBE03
	// for more info.
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { INVENTORY_FILLED });
	}

	public static final PropertyInteger INVENTORY_FILLED = PropertyInteger.create("inventory_filled", 0, 1);

	// change the furnace emitted light ("block light") depending on how many slots
	// are burning
	private static final int FOUR_SIDE_LIGHT_VALUE = 15; // light value for four sides burning
	private static final int ONE_SIDE_LIGHT_VALUE = 15; // light value for a single side burning

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		int lightValue = 0;
		IBlockState blockState = getActualState(getDefaultState(), world, pos);
		int inventoryFilled = (Integer) blockState.getValue(INVENTORY_FILLED);

		if (inventoryFilled == 0) {
			lightValue = 0;
		} else {
			// linearly interpolate the light value depending on how many slots are burning
			lightValue = ONE_SIDE_LIGHT_VALUE
					+ (int) ((FOUR_SIDE_LIGHT_VALUE - ONE_SIDE_LIGHT_VALUE) / (4.0 - 1.0) * inventoryFilled);

		}
		lightValue = MathHelper.clamp(lightValue, 0, FOUR_SIDE_LIGHT_VALUE);
		return lightValue;
	}

	// the block will render in the SOLID layer. See
	// http://greyminecraftcoder.blogspot.co.at/2014/12/block-rendering-18.html for
	// more information.
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.SOLID;
	}

	// used by the renderer to control lighting and visibility of other blocks.
	// set to false because this block doesn't fill the entire 1x1x1 space
	@Override
	public boolean isOpaqueCube(IBlockState iBlockState) {
		return false;
	}

	// used by the renderer to control lighting and visibility of other blocks, also
	// by
	// (eg) wall or fence to control whether the fence joins itself to this block
	// set to false because this block doesn't fill the entire 1x1x1 space
	@Override
	public boolean isFullCube(IBlockState iBlockState) {
		return false;
	}

	// render using a BakedModel
	// required because the default (super method) is INVISIBLE for BlockContainers.
	@Override
	public EnumBlockRenderType getRenderType(IBlockState iBlockState) {
		return EnumBlockRenderType.MODEL;
	}
}