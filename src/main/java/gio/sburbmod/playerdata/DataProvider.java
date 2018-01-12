package gio.sburbmod.playerdata;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraft.nbt.NBTBase;

public class DataProvider implements ICapabilitySerializable<NBTBase> {

	 @CapabilityInject(IPlayerData.class)

	 public static final Capability<IPlayerData> CAP = null;

	 
	 private IPlayerData instance = CAP.getDefaultInstance();


	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CAP;
	}


	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CAP ? CAP.<T> cast(this.instance) : null;
	}


	@Override
	public NBTBase serializeNBT() {
		 return CAP.getStorage().writeNBT(CAP, this.instance, null);
	}


	@Override
	public void deserializeNBT(NBTBase nbt) {
		CAP.getStorage().readNBT(CAP, this.instance, null, nbt);
	}

	 

}
