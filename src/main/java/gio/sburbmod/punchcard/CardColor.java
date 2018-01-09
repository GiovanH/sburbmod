package gio.sburbmod.punchcard;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.awt.*;
import gio.sburbmod.alchemy.Algorithms;

public class CardColor implements IItemColor {
	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		{
			// int metadata = stack.getMetadata();
			// int contentsBits = metadata & 0x03;
			// ItemVariants.EnumBottleContents contents =
			// ItemVariants.EnumBottleContents.byMetadata(contentsBits);
			// return contents.getRenderColour().getRGB();
			//
			// return Color.BLACK.getRGB();
			switch (tintIndex) {
			case 0:
				return Color.WHITE.getRGB();
			case 1: {
				NBTTagCompound nbtTagCompound = stack.getTagCompound();
				if (nbtTagCompound == null || !nbtTagCompound.hasKey("Item"))
					return Color.CYAN.getRGB();

				String code = nbtTagCompound.getTag("Item").toString();
				long hash = Algorithms.hashString(code, 10000);
				float hue = (float)hash/100; //Todo; make this readable
				//System.out.println(String.valueOf(hue));
				return Color.HSBtoRGB(hue, 1, 1);
			}
			default: {
				// oops! should never get here.
				return Color.BLACK.getRGB();
			}
			}

			//
		}
	}
}
