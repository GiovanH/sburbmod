package gio.sburbmod.punchcard;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.awt.*;
import gio.sburbmod.alchemy.Algorithms;

public class CardColor implements IItemColor {
	@Override
	public int getColorFromItemstack(ItemStack stack, int layerIndex) {
		{
			// int metadata = stack.getMetadata();
			// int contentsBits = metadata & 0x03;
			// ItemVariants.EnumBottleContents contents =
			// ItemVariants.EnumBottleContents.byMetadata(contentsBits);
			// return contents.getRenderColour().getRGB();
			//
			// return Color.BLACK.getRGB();
			switch (layerIndex) {
				case 0: {
					NBTTagCompound nbtTagCompound = stack.getTagCompound();
					if (nbtTagCompound == null || !nbtTagCompound.hasKey("Item"))
						return Color.WHITE.getRGB();
	
					String code = nbtTagCompound.getTag("Item").toString();
					long hash = Algorithms.hashString(code, 10000);
					float hue = (float) hash / 100; // Todo; make this readable
					return Color.HSBtoRGB(hue, 1, 1);
				}
				default: {
					return Color.WHITE.getRGB();
				}
			}
		}
	}
}
