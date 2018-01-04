package gio.sburbmod.punchcard;

import java.awt.Color;
import java.util.UUID;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class PunchCard extends Item {

	private static final boolean DEBUG_COLOR = false;

	public PunchCard() {
		this.setMaxStackSize(8);
		this.setCreativeTab(CreativeTabs.MISC); // the item will appear on the Miscellaneous tab in creative
	}

	void setColor(ItemStack stack, int color) {
		if (DEBUG_COLOR) System.out.println(color);

		NBTTagCompound nbtTagCompound = stack.getTagCompound();
		if (nbtTagCompound == null) {
			nbtTagCompound = new NBTTagCompound();
			stack.setTagCompound(nbtTagCompound);
		}
		nbtTagCompound.setInteger("Color", color);
	}

	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		UUID uuid = playerIn.getUniqueID();
		setColor(stack, Color.HSBtoRGB((uuid.hashCode() / 100), 1, 1));
	}

	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		ItemStack stack = playerIn.getHeldItem(hand);
		NBTTagCompound nbtTagCompound = stack.getTagCompound();

		if (nbtTagCompound == null) {
			nbtTagCompound = new NBTTagCompound();
			stack.setTagCompound(nbtTagCompound);
		}

		//float hash = playerIn.getName().hashCode();
		double hash = playerIn.getUniqueID().hashCode();
		
		hash += stack.getCount() * 12;
		
		float hue = (float)((hash/100) - Math.floor(hash/100));
		if (DEBUG_COLOR) System.out.println(hue);
				
		setColor(stack, Color.HSBtoRGB(hue, 1, 1));

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}
}
