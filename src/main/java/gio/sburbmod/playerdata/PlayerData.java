package gio.sburbmod.playerdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import gio.sburbmod.alchemy.Grist.GristType;
import net.minecraft.item.ItemStack;

public class PlayerData implements IPlayerData {
	
	//tablenum
	private int tableNum;
	@Override
	public void setInt(int i) {
		this.tableNum = i;
	}

	@Override
	public int getInt() {
		return tableNum;
	}
	
	//Known items
	private Set<String> knownItems = new HashSet<String>();
	final static boolean DEBUG = true;

	
	@Override
	public boolean knowsItemCode(ItemStack item) {
		return knownItems.contains(item.getUnlocalizedName());
	}


	@Override
	public void learnItemCode(ItemStack item) {
		knownItems.add(item.getUnlocalizedName());
	}

	@Override
	public Set<String> getKnownItems() {
		return knownItems;
	}

	@Override
	public void setKnownItems(Set<String> s) {
		knownItems = s;
	}

	//Grist
	
	private Map<Integer, Integer> gristCollection = new HashMap<Integer, Integer>();


	@Override
	public Map<Integer, Integer> getGristCollection() {
		return gristCollection;
	}


	@Override
	public void setGristCollection(Map<Integer, Integer> gristCollection) {
		this.gristCollection = gristCollection;
		
	}

	@Override
	public void modGristValue(int type, int amount) {
		gristCollection.put(type, ((gristCollection.get(type) == null) ? amount : gristCollection.get(type) + amount));
		
	}


	
}