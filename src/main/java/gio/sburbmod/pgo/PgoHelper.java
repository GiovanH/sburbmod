package gio.sburbmod.pgo;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import gio.sburbmod.pgo.StartupCommon;

public class PgoHelper {

	public static NBTBase newTagCompound() {
		return newTagCompound(1);
	}
	public static NBTBase newTagCompound(int count) {
		NBTTagCompound inputItemItemTag = new NBTTagCompound();
		ItemStack stack = newItemStack(count);
		if (stack != null)
			stack.writeToNBT(inputItemItemTag);
		return inputItemItemTag;
	}
	public static ItemStack newItemStack() {
		return newItemStack(1);
	}
	public static ItemStack newItemStack(int count) {
		return new ItemStack(getItem(), count);
	}
	public static Item getItem() {return StartupCommon.itemBlockSimple;}
	public static boolean isInstance(ItemStack input) {
		Item potential = input.getItem();
		return potential.getRegistryName() == getItem().getRegistryName();
	}

}
