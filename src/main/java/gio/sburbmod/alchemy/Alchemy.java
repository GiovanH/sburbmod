package gio.sburbmod.alchemy;

import java.util.HashSet;
import java.util.Set;

import gio.sburbmod.Util;
import net.minecraft.item.Item;

public class Alchemy {
	private static final boolean DEBUG_ALCHEMY = true;

	public static Set<Item> getResultsForAND(Item i1, Item i2) {
		Set<Item> query = new HashSet<Item>();
		query.add(i1);
		query.add(i2);
		// god i miss python
		return getResultsForAND(query);
	}

	public static Set<Item> getResultsForAND(Set<Item> items) {
		if (DEBUG_ALCHEMY)
			System.out.println();

		if (items.size() == 1) {
			return items;
		} 									// If all the items are the same, you can only get the same item back.

		Set<Item> uses = null; 				// There isn't even an empty list.
		for (Item item : items) {
			Set<Item> newUses = Recipes.getUsesForIngredient(item); // Get the uses for the next item
			if (DEBUG_ALCHEMY)
				System.out.println("Uses for item " + item.getUnlocalizedName() + ": " + Util.prettifyItemSet(newUses));
			if (uses == null) 				// If this is the first item
				uses = newUses; 			// Its uses are the uses
			else { 							// If this isn't the first item
				if (DEBUG_ALCHEMY)
					System.out.println("Retaining items " + Util.prettifyItemSet(newUses));
				uses.retainAll(newUses); 	// Result = INTERSECTION of previous list and new list
			}
			if (DEBUG_ALCHEMY)
				System.out.println(Util.prettifyItemSet(uses));
		}
		uses.removeAll(items);				//Never return one of the original items
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

		if (DEBUG_ALCHEMY)
			System.out.println();

		if (items.size() == 1) {
			return items;
		} 									// If all the items are the same, you can only get the same item back.

		Set<Item> uses = null; 				// There isn't even an empty list.
		for (Item item : items) {
			Set<Item> newUses = Recipes.getUsesForIngredient(item); // Get the uses for the next item
			if (DEBUG_ALCHEMY)
				System.out.println("Uses for item " + item.getUnlocalizedName() + ": " + Util.prettifyItemSet(newUses));
			if (uses == null) 				// If this is the first item
				uses = newUses; 			// Its uses are the uses
			else { 							// If this isn't the first item
				Set<Item> intersection = new HashSet<Item>(uses);

				if (DEBUG_ALCHEMY)
					System.out.println("Retaining items " + Util.prettifyItemSet(newUses));
				intersection.retainAll(newUses);	//Get the intersection, as usual

				if (DEBUG_ALCHEMY)
					System.out.println("Removing items " + Util.prettifyItemSet(intersection));
				uses.removeAll(intersection);		// Result = Previous list MINUS the intersection
			}
			if (DEBUG_ALCHEMY)
				System.out.println(Util.prettifyItemSet(uses));
		}
		uses.removeAll(items);				//Never return one of the original items
		return uses;
	}

}
