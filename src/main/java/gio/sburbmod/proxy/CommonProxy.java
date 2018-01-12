package gio.sburbmod.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

/**
 * CommonProxy is used to set up the mod and start it running.  It contains all the code that should run on both the
 *   Standalone client and the dedicated server.
 *   For more background information see here http://greyminecraftcoder.blogspot.com/2013/11/how-forge-starts-up-your-code.html
 */
public abstract class CommonProxy {

  /**
   * Run before anything else. Read your config, create blocks, items, etc, and register them with the GameRegistry
   */
  public void preInit()
  {
	   //read config first
	  
	  gio.sburbmod.pgo.StartupCommon.preInitCommon();
	  
	  gio.sburbmod.creativetabs.StartupCommon.preInitCommon();
	  
	  gio.sburbmod.cruxite.StartupCommon.preInitCommon();
	  gio.sburbmod.cruxtruder.StartupCommon.preInitCommon();

	  gio.sburbmod.punchcard.StartupCommon.preInitCommon();
	  gio.sburbmod.cardprinter.StartupCommon.preInitCommon();
	  
	  gio.sburbmod.puncher.StartupCommon.preInitCommon();
	  gio.sburbmod.lathe.StartupCommon.preInitCommon();
	  gio.sburbmod.alchemiter.StartupCommon.preInitCommon();
	  gio.sburbmod.alchtable.StartupCommon.preInitCommon();
  }

  /**
   * Do your mod setup. Build whatever data structures you care about. Register recipes,
   * send FMLInterModComms messages to other mods.
   */
  public void init()
  {
	  gio.sburbmod.pgo.StartupCommon.initCommon();
	  
	  gio.sburbmod.playerdata.StartupCommon.init();
	  MinecraftForge.EVENT_BUS.register(new gio.sburbmod.playerdata.Handler());
	  
	  gio.sburbmod.creativetabs.StartupCommon.initCommon();
	  
	  gio.sburbmod.cruxite.StartupCommon.initCommon();
	  gio.sburbmod.cruxtruder.StartupCommon.initCommon();
	  
	  gio.sburbmod.punchcard.StartupCommon.initCommon();
	  gio.sburbmod.cardprinter.StartupCommon.initCommon();
	  
	  gio.sburbmod.puncher.StartupCommon.initCommon();
	  gio.sburbmod.lathe.StartupCommon.initCommon();
	  gio.sburbmod.alchemiter.StartupCommon.initCommon();
	  gio.sburbmod.alchtable.StartupCommon.initCommon();
	  
  }

  /**
   * Handle interaction with other mods, complete your setup based on this.
   */
  public void postInit()
  {
	  
	  gio.sburbmod.pgo.StartupCommon.postInitCommon();
	  
	  gio.sburbmod.creativetabs.StartupCommon.postInitCommon();
	  
	  gio.sburbmod.cruxite.StartupCommon.postInitCommon();
	  gio.sburbmod.cruxtruder.StartupCommon.postInitCommon();
	  
	  gio.sburbmod.punchcard.StartupCommon.postInitCommon();
	  gio.sburbmod.cardprinter.StartupCommon.postInitCommon();
	  
	  gio.sburbmod.puncher.StartupCommon.postInitCommon();
	  gio.sburbmod.lathe.StartupCommon.postInitCommon();
	  gio.sburbmod.alchemiter.StartupCommon.postInitCommon();
	  gio.sburbmod.alchtable.StartupCommon.postInitCommon();
	  
  }

  // helper to determine whether the given player is in creative mode
  //  not necessary for most examples
  abstract public boolean playerIsInCreativeMode(EntityPlayer player);

  /**
   * is this a dedicated server?
   * @return true if this is a dedicated server, false otherwise
   */
  abstract public boolean isDedicatedServer();
}
