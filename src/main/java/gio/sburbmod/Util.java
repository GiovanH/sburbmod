package gio.sburbmod;

/**
 * Author: GiovanH
 * Odd sburbmod utility functions
 */

import java.util.Set;

import net.minecraft.item.Item;

public class Util {

	/**
	 * For debugging, prints human-readable sets of items.
	 * @param itemlist A set of items
	 * @return a string representing a human-readable array of items
	 */
	public static String prettifyItemSet(Set<Item> itemlist) {
		StringBuilder s = new StringBuilder();
		s.append("[");
		boolean comma = false;
		for (Item item : itemlist) {
			if (comma) s.append(',');
			comma = true;
			s.append(item.getUnlocalizedName());
		}
		s.append("]");
		return s.toString();
	}
}
