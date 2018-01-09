package gio.sburbmod;

import java.util.Set;

import net.minecraft.item.Item;

public class Util {

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
