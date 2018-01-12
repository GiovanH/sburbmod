package gio.sburbmod.alchemy;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import gio.sburbmod.FmtCodes;
import gio.sburbmod.playerdata.DataProvider;
import gio.sburbmod.playerdata.IPlayerData;

public class SburbItemTooltip {
	
	final static boolean DEBUG_PLAYER = false;
	
	@SideOnly(Side.CLIENT) 
	public static void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip,
			ITooltipFlag flagIn, EntityPlayer player) {

		NBTTagCompound nbtTagCompound = stack.getTagCompound();
		if (nbtTagCompound == null)  {
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
				boolean knowledge = false;
				if (DEBUG_PLAYER && player != null) System.out.println(player.toString());
				if (player != null) knowledge = player.getCapability(DataProvider.CAP, null).knowsItemCode(item);
				//if (knowledge) {
					tooltip.add((knowledge ? FmtCodes.CGREYDARK : FmtCodes.CGREYDARK + FmtCodes.SPMAGIC ) + item.getDisplayName() + "");
				//}
				
			}
		}
	}
}
