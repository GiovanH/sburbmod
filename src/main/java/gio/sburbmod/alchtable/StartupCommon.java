package gio.sburbmod.alchtable;

import gio.sburbmod.GuiHandlerRegistry;
import gio.sburbmod.SburbMod;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

/**
 * User: brandon3055 Date: 06/01/2015
 *
 * The Startup class for this example is called during startup, in the following
 * order: preInitCommon preInitClientOnly initCommon initClientOnly
 * postInitCommon postInitClientOnly See MinecraftByExample class for more
 * information
 */
public class StartupCommon {
	private static final int ALCHTABLE_MESSAGE_ID = 30;
	public static Block blockPuncher; // this holds the unique instance of your block
	public static ItemBlock itemBlockPuncher; // this holds the unique instance of the ItemBlock corresponding
												// to your block
	public static SimpleNetworkWrapper simpleNetworkWrapper; // used to transmit your network messages

	
	
	public static void preInitCommon() {
		
	    simpleNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("AlchtableChannel");
	    simpleNetworkWrapper.registerMessage(MessageHandlerOnServer.class, PacketMessageToServer.class,
	                                          ALCHTABLE_MESSAGE_ID, Side.SERVER);
		
		// each instance of your block should have two names:
		// 1) a registry name that is used to uniquely identify this block. Should be
		// unique within your mod. use lower case.
		// 2) an 'unlocalised name' that is used to retrieve the text name of your block
		// in the player's language. For example-
		// the unlocalised name might be "water", which is printed on the user's screen
		// as "Wasser" in German or
		// "aqua" in Italian.
		//
		// Multiple blocks can have the same unlocalised name - for example
		// +----RegistryName----+---UnlocalisedName----+
		// | flowing_water + water |
		// | stationary_water + water |
		// +--------------------+----------------------+
		//
		blockPuncher = new BlockTable().setUnlocalizedName("sburbmod_alchtable");
		blockPuncher.setRegistryName("alchtable");
		ForgeRegistries.BLOCKS.register(blockPuncher);

		// We also need to create and register an ItemBlock for this block otherwise it
		// won't appear in the inventory
		itemBlockPuncher = new ItemBlock(blockPuncher);
		itemBlockPuncher.setRegistryName(blockPuncher.getRegistryName());
		ForgeRegistries.ITEMS.register(itemBlockPuncher);

		// Each of your tile entities needs to be registered with a name that is unique
		// to your mod.
		GameRegistry.registerTileEntity(TileTable.class, "alchtable_tile_entity");

		// You need to register a GUIHandler for the container. However there can be
		// only one handler per mod, so for the purposes
		// of this project, we create a single GuiHandlerRegistry for all examples.
		// We register this GuiHandlerRegistry with the NetworkRegistry, and then tell
		// the GuiHandlerRegistry about
		// each example's GuiHandler, in this case GuiHandlerMBE31, so that when it gets
		// a request from NetworkRegistry,
		// it passes the request on to the correct example's GuiHandler.
		NetworkRegistry.INSTANCE.registerGuiHandler(SburbMod.instance, GuiHandlerRegistry.getInstance());
		GuiHandlerRegistry.getInstance().registerGuiHandler(new GuiHandlerTable(), GuiHandlerTable.getGuiID());

		// it is possible to register the same message class on both sides if you want,
		// eg,
		// simpleNetworkWrapper.registerMessage(ServerMessageHandler.class,
		// AirstrikeMessageBothDirections.class, AIRSTRIKE_MESSAGE_ID, Side.SERVER);
		// simpleNetworkWrapper.registerMessage(ClientMessageHandler.class,
		// AirstrikeMessageBothDirections.class, AIRSTRIKE_MESSAGE_ID, Side.CLIENT);

		// it is also possible to register the same message handler on both sides. I
		// recommend that you don't do this because it can lead to
		// crashes (and in particular dedicated server problems) if you aren't very
		// careful to keep the client-side and server-side code separate

	}

	public static void initCommon() {
	}

	public static void postInitCommon() {
	}
}
