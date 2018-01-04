package gio.sburbmod.cruxite;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.awt.*;

public class DowelColor implements IItemColor {
	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		{
			// int metadata = stack.getMetadata();
			// int contentsBits = metadata & 0x03;
			// ItemVariants.EnumBottleContents contents =
			// ItemVariants.EnumBottleContents.byMetadata(contentsBits);
			// return contents.getRenderColour().getRGB();
			//
			//return Color.BLACK.getRGB();
			NBTTagCompound nbtTagCompound = stack.getTagCompound();
		    if (nbtTagCompound == null || !nbtTagCompound.hasKey("Color")) return Color.CYAN.getRGB();
		    
		    return nbtTagCompound.getInteger("Color");
			
			//
		}
	}
}
