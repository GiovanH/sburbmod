package gio.sburbmod.alchemy;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import gio.sburbmod.FmtCodes;

public class SburbItemTooltip {

	public static void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip,
			ITooltipFlag flagIn) {

		NBTTagCompound nbtTagCompound = stack.getTagCompound();
		if (nbtTagCompound == null) {
			nbtTagCompound = new NBTTagCompound();
			stack.setTagCompound(nbtTagCompound);
		}
		// TODO: Migrate from code key to item nbt
		// if (nbtTagCompound.hasKey("Code")) {
		// String code = nbtTagCompound.getString("Code");
		// tooltip.add("§o" + code + "");
		// tooltip.add("§o\"" + Captcha.getCaptchaCode(code) + "\"");
		// }

		if (nbtTagCompound.hasKey("Item")) {
			ItemStack item = new ItemStack(nbtTagCompound.getCompoundTag("Item"));
			if (item != null) {
				tooltip.add(FmtCodes.SPCLEAN + "\"" + Captcha.getCaptchaCode(item) + "\"");
				tooltip.add(FmtCodes.SPCLEAN + FmtCodes.CGREYDARK + item.getDisplayName() + "");
			}
		}
	}
}
