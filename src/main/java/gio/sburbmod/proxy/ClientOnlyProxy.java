package gio.sburbmod.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * ClientProxy is used to set up the mod and start it running on normal
 * minecraft. It contains all the code that should run on the client side only.
 * For more background information see here
 * http://greyminecraftcoder.blogspot.com/2013/11/how-forge-starts-up-your-code.html
 */
public class ClientOnlyProxy extends CommonProxy {

	/**
	 * Run before anything else. Read your config, create blocks, items, etc, and
	 * register them with the GameRegistry
	 */
	public void preInit() {
		super.preInit();
		
		gio.sburbmod.pgo.StartupClientOnly.preInitClientOnly();
		
		gio.sburbmod.cruxite.StartupClientOnly.preInitClientOnly();
		gio.sburbmod.cruxtruder.StartupClientOnly.preInitClientOnly();
		
		gio.sburbmod.punchcard.StartupClientOnly.preInitClientOnly();
		gio.sburbmod.cardprinter.StartupClientOnly.preInitClientOnly();
		  
		gio.sburbmod.puncher.StartupClientOnly.preInitClientOnly();
		gio.sburbmod.lathe.StartupClientOnly.preInitClientOnly();
		gio.sburbmod.alchemiter.StartupClientOnly.preInitClientOnly();
		gio.sburbmod.alchtable.StartupClientOnly.preInitClientOnly();
	}

	/**
	 * Do your mod setup. Build whatever data structures you care about. Register
	 * recipes, send FMLInterModComms messages to other mods.
	 */
	public void init() {
		super.init();
		
		gio.sburbmod.pgo.StartupClientOnly.initClientOnly();
		
		gio.sburbmod.cruxite.StartupClientOnly.initClientOnly();
		gio.sburbmod.cruxtruder.StartupClientOnly.initClientOnly();
		
		gio.sburbmod.punchcard.StartupClientOnly.initClientOnly();
		gio.sburbmod.cardprinter.StartupClientOnly.initClientOnly();
		  
		gio.sburbmod.puncher.StartupClientOnly.initClientOnly();
		gio.sburbmod.lathe.StartupClientOnly.initClientOnly();
		gio.sburbmod.alchemiter.StartupClientOnly.initClientOnly();
		gio.sburbmod.alchtable.StartupClientOnly.preInitClientOnly();
	}

	/**
	 * Handle interaction with other mods, complete your setup based on this.
	 */
	public void postInit() {
		super.postInit();
		
		gio.sburbmod.pgo.StartupClientOnly.postInitClientOnly();
		
		gio.sburbmod.cruxite.StartupClientOnly.postInitClientOnly();
		gio.sburbmod.cruxtruder.StartupClientOnly.postInitClientOnly();
		
		gio.sburbmod.punchcard.StartupClientOnly.postInitClientOnly();
		gio.sburbmod.cardprinter.StartupClientOnly.postInitClientOnly();
		  
		gio.sburbmod.puncher.StartupClientOnly.postInitClientOnly();
		gio.sburbmod.lathe.StartupClientOnly.postInitClientOnly();
		gio.sburbmod.alchemiter.StartupClientOnly.postInitClientOnly();
		gio.sburbmod.alchtable.StartupClientOnly.preInitClientOnly();
	}

	@Override
	public boolean playerIsInCreativeMode(EntityPlayer player) {
		if (player instanceof EntityPlayerMP) {
			EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
			return entityPlayerMP.interactionManager.isCreative();
		} else if (player instanceof EntityPlayerSP) {
			return Minecraft.getMinecraft().playerController.isInCreativeMode();
		}
		return false;
	}

	@Override
	public boolean isDedicatedServer() {
		return false;
	}

}
