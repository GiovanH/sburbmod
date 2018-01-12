package gio.sburbmod.playerdata;

import net.minecraftforge.common.capabilities.CapabilityManager;

public class StartupCommon {

	public static void init() {
		CapabilityManager.INSTANCE.register(IPlayerData.class, new DataStorage(), PlayerData.class);
	}
}
