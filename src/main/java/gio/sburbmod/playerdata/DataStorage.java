package gio.sburbmod.playerdata;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class DataStorage implements IStorage<IPlayerData> {

	static int KNOWNITEMS_TYPE = 0; //The "type" byte: we learn this in writeNBT

/* 
 * Data Structure:
 * Player
 * - Known Items (List of Strings)
 * - Grist (Compound key:value representing gristtype:amount)
 */

	@Override
	public NBTBase writeNBT(Capability<IPlayerData> capability, IPlayerData instance, EnumFacing side) {
		NBTTagCompound tag = new NBTTagCompound();
		
		//Known items
		NBTTagList knownItems = new NBTTagList();        
		for (String itemName : instance.getKnownItems()) {
			knownItems.appendTag(new NBTTagString(itemName));
		}
        tag.setTag("KnownItems", knownItems);
		KNOWNITEMS_TYPE = knownItems.getTagType();
		
 		return tag;
	}


	@Override
	public void readNBT(Capability<IPlayerData> capability, IPlayerData instance, EnumFacing side, NBTBase nbt) {
        try {
			NBTTagCompound tag = (NBTTagCompound) nbt;
			
			NBTTagList knownItems = tag.getTagList("KnownItems", KNOWNITEMS_TYPE);
			Set<String> knownItemsSet  = new HashSet<String>();
			
			for (int i = 0; i < knownItems.tagCount(); i++) { 
				 String item = knownItems.getStringTagAt(i);
				 knownItemsSet.add(item);
			}
			
			instance.setKnownItems(knownItemsSet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
