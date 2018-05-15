package gio.sburbmod.alchemy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class Grist {
		
	public static enum GristType{
		BUILD(0), SHALE(1);
		
		private final int id;
	    private GristType(int value) {
	        this.id = value;
	    }
	    public int getId() {
	        return id;
	    }
	}
	

	int count = 0;
	
	public Grist() {	}
	public int getCount() {		return count;	}
	public void add(int i){count += i;}
	public void subtract(int i) {count -= i;}
	public boolean isAtLeast(int i) {return count >= i;}
	
}
