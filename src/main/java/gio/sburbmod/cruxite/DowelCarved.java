package gio.sburbmod.cruxite;

import java.awt.Color;
import java.util.List;

import javax.annotation.Nullable;

import gio.sburbmod.FmtCodes;
import gio.sburbmod.alchemy.Algorithms;
import gio.sburbmod.alchemy.Captcha;
import gio.sburbmod.alchemy.SburbItemTooltip;
import net.minecraft.client.util.ITooltipFlag;

//import java.awt.Color;
//import java.util.UUID;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.EnumActionResult;
//import net.minecraft.util.EnumHand;
//import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class DowelCarved extends DowelPlain {

	final static int VARIANTS = 196; // indexes at 1, sorry
	private static final boolean SUBITEM_DEBUG = false;

	public static int getVariants() {
		return VARIANTS;
	}

	public DowelCarved() {
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.MISC); // the item will appear on the Miscellaneous tab in creative
	}

	public static void setMetadata(ItemStack containerStack) {
		try {
			ItemStack input = new ItemStack(containerStack.getTagCompound().getCompoundTag("Item"));
			int dmg = 1 + (int) Algorithms.hashString(input.toString(), getVariants());
			System.out.println("Setting metadata for " + input.toString() + " to " + dmg);
			containerStack.setItemDamage(dmg);
		} catch (Exception e) {
			e.printStackTrace();
			containerStack.setItemDamage(0);
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		setMetadata(playerIn.getHeldItem(handIn));
		return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

		SburbItemTooltip.addInformation(stack, worldIn, tooltip, flagIn);
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			for (int i = 1; i <= VARIANTS; i++) {
				if (SUBITEM_DEBUG)
					System.out.println("sburbmod:dowel_carved_" + i + "/" + i);
				items.add(new ItemStack(this, 1, i));
			}
		}
	}
}
