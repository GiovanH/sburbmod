package gio.sburbmod;

import gio.sburbmod.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = SburbMod.modId, name = SburbMod.name, version = SburbMod.version)
public class SburbMod {
	
	public static final String modId = "sburbmod";
	public static final String name = "sburb friendly name";
	public static final String version = "0.1";
	
    // The instance of your mod that Forge uses.  Optional.
    @Mod.Instance(SburbMod.modId)
    public static SburbMod instance;

    // Says where the client and server 'proxy' code is loaded.
	@SidedProxy(serverSide = "gio.sburbmod.proxy.DedicatedServerProxy", clientSide = "gio.sburbmod.proxy.ClientOnlyProxy")
	public static CommonProxy proxy;
	
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
      proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
      proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
      proxy.postInit();
    }
	
    public static String prependModID(String name) {return modId + ":" + name;}
    public void log(String msg) {
		System.out.println("[Sburb] " + msg);
    }
}