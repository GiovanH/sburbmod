package gio.sburbmod.playerdata;

import gio.sburbmod.SburbMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Handler {

	public static final ResourceLocation CAP = new ResourceLocation(SburbMod.modId, "playerdata");

	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if (!(event.getObject() instanceof EntityPlayer))
			return;
		event.addCapability(CAP, new DataProvider());
	}

	/**
	 * Copy data from dead player to the new player
	 */

	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event) {
		EntityPlayer player = event.getEntityPlayer();
		IPlayerData cap = player.getCapability(DataProvider.CAP, null);
		IPlayerData oldCap = event.getOriginal().getCapability(DataProvider.CAP, null);
		cap.setInt(oldCap.getInt());
		cap.setGristCollection(oldCap.getGristCollection());
		cap.setKnownItems(oldCap.getKnownItems());
	}

}
