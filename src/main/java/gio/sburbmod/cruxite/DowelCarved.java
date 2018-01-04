package gio.sburbmod.cruxite;

import java.util.List;

import javax.annotation.Nullable;

import gio.sburbmod.alchemy.Util;
import net.minecraft.client.util.ITooltipFlag;

//import java.awt.Color;
//import java.util.UUID;

import net.minecraft.creativetab.CreativeTabs;
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
import net.minecraft.world.World;

public class DowelCarved extends DowelPlain {

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

		super.addInformation(stack, worldIn, tooltip, flagIn);

		NBTTagCompound nbtTagCompound = stack.getTagCompound();
		if (nbtTagCompound == null) {
			nbtTagCompound = new NBTTagCompound();
			stack.setTagCompound(nbtTagCompound);
		}
		if (nbtTagCompound.hasKey("Code")) {
			String code = nbtTagCompound.getString("Code");
			tooltip.add("§o" + code + "");
			tooltip.add("§o\"" + Util.getCaptchaCode(code) + "\"");
		}
	}
}
