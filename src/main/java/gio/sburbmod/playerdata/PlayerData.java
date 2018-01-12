package gio.sburbmod.playerdata;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.ItemStack;

public class PlayerData implements IPlayerData {

	private int i = 0;
	private Set<String> knownItems = new HashSet<String>();
	final static boolean DEBUG = true;
	
	@Override
	public void setInt(int i) {
		this.i = i;
	}

	@Override
	public int getInt() {
		return i;
	}

	@Override
	public boolean knowsItemCode(ItemStack item) {
		return knownItems.contains(item.getUnlocalizedName());
	}

	private void dumpKnownItems() {
		System.out.println(knownItems.toString());
//		StringBuilder n = new StringBuilder();
//		n.append(knownItems.size() + " Known Items: ");
//		for (String i : knownItems) {
//			n.append(", " + i);
//		}
//		System.out.println(n);
	}

	@Override
	public void learnItemCode(ItemStack item) {
		knownItems.add(item.getUnlocalizedName());
	}

	@Override
	public Iterable<String> getKnownItems() {
		return knownItems;
	}

	@Override
	public void setKnownItems(Set<String> s) {
		knownItems = s;
	}	
}