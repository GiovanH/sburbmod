package gio.sburbmod.alchemy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import gio.sburbmod.Util;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

//import net.minecraftforge.fml.common.registry.GameRegistry;
//import net.minecraftforge.oredict.OreDictionary;
//import net.minecraftforge.oredict.ShapedOreRecipe;
//import net.minecraftforge.common.crafting.CraftingHelper;
//import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
//import net.minecraft.item.crafting.IRecipe;
//import net.minecraft.item.crafting.Ingredient;
//import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;

public class Recipes {
	// Bookshelf: [wood, book]
	private static final Map<Item, Set<Item>> INGREDIENTS_BY_PRODUCT = new HashMap<Item, Set<Item>>();
	// Stick: [torch, pickaxe...]
	private static final Map<Item, Set<Item>> PRODUCTS_BY_INGREDIENT = new HashMap<Item, Set<Item>>();
	
	private static final Set<Item> INGREDIENTS_ALL = new HashSet<Item>();
	public static final Set<Item> FUNDAMENTAL_ELEMENTS = new HashSet<Item>();
	private static final boolean DEBUG_RECIPE_CONSTRUCTION = false;

	/* Singleton code */
	private static Recipes singleton = new Recipes();

	private Recipes() {
		generateComponentMap();
	}

	public static Recipes getInstance() {
		return singleton;
	}

	/**
	 * Finds the crafting components of item key.
	 * 
	 * @param key
	 * @return A set containing all components. May be empty.
	 */
	public static Set<Item> getIngredientsOfItem(Item key) {
		Set<Item> components = INGREDIENTS_BY_PRODUCT.get(key);
		if (components == null)
			return new HashSet<Item>();
		return new HashSet<Item>(components);
	}

	/**
	 * Finds the items that can be crafted by using key as an ingredient.
	 * 
	 * @param key
	 * @return A set containing all items requiring key as an ingredient. May be empty.
	 */
	public static Set<Item> getUsesForIngredient(Item key) {
		Set<Item> components = PRODUCTS_BY_INGREDIENT.get(key);
		if (components == null)
			return new HashSet<Item>();
		return new HashSet<Item>(components);
	}

	private static void generateComponentMap() {
		for (IRecipe irecipe : CraftingManager.REGISTRY) {
			// Make new set
			java.util.Set<Item> componentsOfItem = new HashSet<Item>();
			for (Ingredient ingredient : irecipe.getIngredients()) {
				for (ItemStack stack : ingredient.getMatchingStacks()) {

					// Insert item into set
					componentsOfItem.add(stack.getItem());
					INGREDIENTS_ALL.add(stack.getItem());
				}
			}
			// Insert set
			INGREDIENTS_BY_PRODUCT.put(irecipe.getRecipeOutput().getItem(), componentsOfItem);
		}
		// Make the other registry too
		for (Item i : Item.REGISTRY) {										//For every item
			Set<Item> uses = new HashSet<Item>();							//Make a list for its uses
			for (Item product : INGREDIENTS_BY_PRODUCT.keySet()) {			//For every product
				Set<Item> ingredients = INGREDIENTS_BY_PRODUCT.get(product);	//Get that product's ingredients
				if (ingredients.contains(i))								//If our first item is one of its ingredients
					uses.add(product);										//add this product to its list of uses.
												
			}
			if (!uses.isEmpty()) PRODUCTS_BY_INGREDIENT.put(i, uses);			//If our item has any uses, remember them.
		}
		
		//Substitutions
		for (Item product : INGREDIENTS_BY_PRODUCT.keySet()) {
			Set<Item> ingredients = getIngredientsOfItem(product);	//Get that product's ingredients
			if (ingredients.size() == 1) {
				if (DEBUG_RECIPE_CONSTRUCTION) System.out.println("\nSingle-ingredient product: " + product.getUnlocalizedName());
				for (Item theIngredient : ingredients) {
					if (DEBUG_RECIPE_CONSTRUCTION) System.out.println("Ingredient: " + theIngredient.getUnlocalizedName());
					
					Set<Item> itsUses = getUsesForIngredient(theIngredient);
					if (DEBUG_RECIPE_CONSTRUCTION) System.out.println("Ingredient uses: " + Util.prettifyItemSet(itsUses));
					Set<Item> combinedUses = getUsesForIngredient(product);
					if (DEBUG_RECIPE_CONSTRUCTION) System.out.println("Product uses: " + Util.prettifyItemSet(combinedUses));
					combinedUses.addAll(itsUses);
					if (DEBUG_RECIPE_CONSTRUCTION) System.out.println("Combined uses: " + Util.prettifyItemSet(combinedUses));
					PRODUCTS_BY_INGREDIENT.put(product, combinedUses);
					PRODUCTS_BY_INGREDIENT.put(theIngredient, combinedUses);
					//add all of that ingredient's uses
					//to the product's uses.
				}
			}
		}

		// What ingredients can't be crafted?
		if (DEBUG_RECIPE_CONSTRUCTION) System.out.println("Fundamental elements:");
		for (Item i : Item.REGISTRY) {
			if (!INGREDIENTS_BY_PRODUCT.containsKey(i)) {
				FUNDAMENTAL_ELEMENTS.add(i);
				if (DEBUG_RECIPE_CONSTRUCTION) System.out.println(i.getUnlocalizedName());
			}
		}

		if (DEBUG_RECIPE_CONSTRUCTION) System.out.println("Done.");
	}

}
