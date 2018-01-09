package gio.sburbmod.punchcard;

import gio.sburbmod.alchemy.Captcha;
import gio.sburbmod.alchemy.SburbItemTooltip;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
//import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

import javax.annotation.Nullable;

public class PunchCardPunched extends PunchCard {

	//private static final boolean DEBUG_COLOR = false;

	public PunchCardPunched() {
		this.setMaxStackSize(64);
		this.setCreativeTab(CreativeTabs.MISC); // the item will appear on the Miscellaneous tab in creative
	}
	
	
}

// inputUsed = new ItemStack(nbtTagCompound.getCompoundTag("ItemUsed"));

//NBTTagCompound inputItemItemTag = new NBTTagCompound();
//if (inputUsed != null)
//	inputUsed.writeToNBT(inputItemItemTag);
//parentNBTTagCompound.setTag("ItemUsed", inputItemItemTag);