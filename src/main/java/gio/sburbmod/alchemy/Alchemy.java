package gio.sburbmod.alchemy;

import java.util.HashSet;
import java.util.Set;

import gio.sburbmod.Util;
import net.minecraft.item.Item;

public class Alchemy {
	private static final boolean DEBUG_ALCHEMY = false;
	
	public static Set<Item> getResultsForAND(Item i1, Item i2) {
		Set<Item> query = new HashSet<Item>();
		query.add(i1);
		query.add(i2);
		// god i miss python
		return getResultsForAND(query);
	}
	

	public static Set<Item> getResultsForAND(Set<Item> items) {
		if (DEBUG_ALCHEMY) System.out.println();
		Set<Item> uses = new HashSet<Item>();
		for (Item item : items) {
			Set<Item> newUses = Recipes.getUsesForIngredient(item);
			if (DEBUG_ALCHEMY) System.out.println(item.getUnlocalizedName() + ": " + Util.prettifyItemSet(newUses));
			if (uses.isEmpty()) 
				uses = newUses;
			else 
				uses.retainAll(newUses);
			if (DEBUG_ALCHEMY) System.out.println(Util.prettifyItemSet(uses));
		}
		return uses;
	}

	public static Set<Item> getResultsForOR(Item i1, Item i2) {
		Set<Item> query = new HashSet<Item>();
		query.add(i1);
		query.add(i2);
		// god i miss python
		return getResultsForOR(query);
	}

	public static Set<Item> getResultsForOR(Set<Item> items) {
		if (DEBUG_ALCHEMY) System.out.println();
		Set<Item> uses = new HashSet<Item>();
		for (Item item : items) {
			Set<Item> newUses = Recipes.getUsesForIngredient(item);
			if (DEBUG_ALCHEMY) System.out.println(item.getUnlocalizedName() + ": " + Util.prettifyItemSet(newUses));
			if (uses.isEmpty()) 
				uses = newUses;
			else {
				Set<Item> intersection = new HashSet<Item>(uses);
				intersection.retainAll(newUses);
				uses.addAll(newUses);
				uses.removeAll(intersection);
			}
			if (DEBUG_ALCHEMY) System.out.println(Util.prettifyItemSet(uses));
		}
		return uses;
	}

}
