package gio.sburbmod.punchcard;

import gio.sburbmod.alchemy.Captcha;
import gio.sburbmod.alchemy.SburbItemTooltip;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

import javax.annotation.Nullable;

public class PunchCard extends Item {

	//private static final boolean DEBUG_COLOR = false;

	public PunchCard() {
		this.setMaxStackSize(64);
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.MISC); // the item will appear on the Miscellaneous tab in creative
	}

//	@Override
//    public String getUnlocalizedName(ItemStack stack)
//    {
//		if (stack.getTagCompound() == null ) return "item.sburbmod_punchcard";
//        return stack.getTagCompound().hasKey("Item") ? "item.sburbmod_punchcard_punched" : "item.sburbmod_punchcard";
//    }
    
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

		super.addInformation(stack, worldIn, tooltip, flagIn);
		SburbItemTooltip.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	
//	@Override
//	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
//
//		super.addInformation(stack, worldIn, tooltip, flagIn);
//
//		NBTTagCompound nbtTagCompound = stack.getTagCompound();
//		if (nbtTagCompound == null) {
//			nbtTagCompound = new NBTTagCompound();
//			stack.setTagCompound(nbtTagCompound);
//		}
//		if (nbtTagCompound.hasKey("Code")) {
//			String code = nbtTagCompound.getString("Code");
//			tooltip.add("§o\"" + Util.getCaptchaCode(code) + "\"");
//		}
//	}
	
}
