package gio.sburbmod.punchcard;

import java.util.List;

import javax.annotation.Nullable;

import gio.sburbmod.alchemy.Algorithms;
import gio.sburbmod.alchemy.SburbItemTooltip;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PunchCard extends Item {

	// private static final boolean DEBUG_COLOR = false;

	final static int VARIANTS = 1024; // indexes at 1, sorry
	private static final boolean SUBITEM_DEBUG = false;

	public static int getVariants() {
		return VARIANTS;
	}

	private EntityPlayer player;

	public PunchCard() {
		this.setMaxStackSize(64);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.MISC); // the item will appear on the Miscellaneous tab in creative
	}

	public static void setMetadata(ItemStack punchedStack) {
		try {
			ItemStack input = new ItemStack(punchedStack.getTagCompound().getCompoundTag("Item"));
			int dmg = 1 + (int) Algorithms.hashString(input.toString(), PunchCard.getVariants());
			punchedStack.setItemDamage(dmg);
		} catch (Exception e) {
			e.printStackTrace();
			punchedStack.setItemDamage(0);
		}
	}

	// @Override
	// public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer
	// playerIn, EnumHand handIn)
	// {
	// setMetadata(playerIn.getHeldItem(handIn));
	// return new ActionResult<ItemStack>(EnumActionResult.PASS,
	// playerIn.getHeldItem(handIn));
	// }

	// @Override
	// public void onUpdate(ItemStack stack, World world, Entity entity, int slot,
	// boolean isSelected) {
	// setMetadata(stack);
	// }

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {

			items.add(new ItemStack(this, 1));
			// for (int i = 0; i <= VARIANTS; i++) {
			// if (SUBITEM_DEBUG) System.out.println("sburbmod:punched_card_" + i + "/" +
			// i);
			// items.add(new ItemStack(this, 1, i));
			// }
			if (tab.equals(StartupCommon.cardTab)) {
				for (Item i : Item.REGISTRY) {
					ItemStack s = new ItemStack(this, 1);
					ItemStack is = new ItemStack(i, 1);
					NBTTagCompound nbtTagCompound = new NBTTagCompound();
					s.setTagCompound(nbtTagCompound);

					NBTTagCompound inputItemItemTag = new NBTTagCompound();
					is.writeToNBT(inputItemItemTag);
					nbtTagCompound.setTag("Item", inputItemItemTag);

					setMetadata(s);

					items.add(s);
				}
			}
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		// this.setDamage(stack, (stack.getTagCompound().hasKey("Item") ? 1 : 0));
		return stack.getMetadata() == 0 ? "item.sburbmod_punchcard" : "item.sburbmod_punchcard_punched";
	}

	
	@Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
		if (entityIn instanceof EntityPlayer && !entityIn.world.isRemote) this.player = (EntityPlayer)entityIn;
    }

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

		SburbItemTooltip.addInformation(stack, worldIn, tooltip, flagIn, this.player);
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

}
