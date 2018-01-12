package gio.sburbmod.playerdata;

import java.util.Collection;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IPlayerData {
	
	public void setInt(int i);
	
	public int getInt();

	public boolean knowsItemCode(ItemStack item);
	public void learnItemCode(ItemStack item);

	public Iterable<String> getKnownItems();
	public void setKnownItems(Set<String> s);
	
}
