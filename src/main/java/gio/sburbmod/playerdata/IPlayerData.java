package gio.sburbmod.playerdata;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IPlayerData {
	
	public void setInt(int i);
	public int getInt();
	
	//Item code
	public boolean knowsItemCode(ItemStack item);
	public void learnItemCode(ItemStack item);

	public Set<String> getKnownItems();
	public void setKnownItems(Set<String> s);
	
	//Grist collection
	public Map<Integer, Integer> getGristCollection();
	public void setGristCollection(Map<Integer, Integer> m);
	public void modGristValue(int type, int amount);
}
