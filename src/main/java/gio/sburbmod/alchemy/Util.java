package gio.sburbmod.alchemy;

import net.minecraft.item.ItemStack;


import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

import gio.sburbmod.alchemy.Algorithms;

@SuppressWarnings("unused")
public class Util {
	private static final long CAPTCHASIZE = (long) (Math.pow(64, 8));
	private static final int CODE_LENGTH = 6;

	static char charref[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C',
			'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z', '?', '!' };

	public static String getCaptchaCode(long seed) {
		final int BASE = 64;
		// int[] code_i = new int[LEN];
		char[] code = new char[CODE_LENGTH];
		for (int i = 0; i < CODE_LENGTH; i++) {
			int q = (int) Math.floor(seed / BASE);
			int r = (int) (seed % BASE);
			// code_i[i] = r;
			code[i] = charref[r];
			seed = q;
		}
		return String.valueOf(code);
	}

	public static String getCaptchaCode(String input) {
		// TODO Auto-generated method stub
		if (input.equals("sburbmod:generic")) {
			StringBuilder s = new StringBuilder();
			for (int i = 0; i < CODE_LENGTH; i++)
				s.append("0");
			return s.toString();
		}
		return getCaptchaCode(Algorithms.hashString(input, CAPTCHASIZE));
	}

	public static String getCaptchaCode(ItemStack input) {
		// TODO Auto-generated method stub
		return getCaptchaCode(input.getItem().getRegistryName().toString());
	}
	
	
}
