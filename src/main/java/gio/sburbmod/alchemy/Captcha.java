package gio.sburbmod.alchemy;

import gio.sburbmod.pgo.PgoHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

import gio.sburbmod.alchemy.Algorithms;

@SuppressWarnings("unused")
public class Captcha {
	private static final long CAPTCHASIZE = (long) (Math.pow(64, 8));
	private static final int CODE_LENGTH = 6;

	static char charref[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C',
			'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z', '?', '!' };

	public static String getCaptchaCode(long seed) {
		seed = Math.abs(seed);
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

	enum specialCodes {
		ZEROES, PGO, CARD
	}

	public static String getCaptchaCode(String input) {
		return getCaptchaCode(Algorithms.hashString(input, CAPTCHASIZE));
	}

	static String specialCode(specialCodes code) {
		switch (code) {
		case PGO:
			return repeatCode('0');
		case CARD:
			return repeatCode('1');
		default:
			return "OH_NO_";
		}
	}

	static String repeatCode(char c) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < CODE_LENGTH; i++)
			s.append(c);
		return s.toString();
	}

	public static String getCaptchaCode(ItemStack input) {
		if (PgoHelper.isInstance(input))
			return specialCode(specialCodes.PGO);
		if (input.getItem().getRegistryName() == gio.sburbmod.punchcard.StartupCommon.punchCard.getRegistryName())
			return specialCode(specialCodes.CARD);
		// TODO: Migrate from code key to item nbt
		// StringBuilder extradata = new StringBuilder();
		return getCaptchaCode(input.getItem().getRegistryName().toString());
	}

}
