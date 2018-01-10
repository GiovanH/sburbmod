package gio.sburbmod.alchtable;

import gio.sburbmod.GuiHandlerRegistry;
import gio.sburbmod.SburbMod;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * User: brandon3055 Date: 06/01/2015
 *
 * The Startup class for this example is called during startup, in the following
 * order: preInitCommon preInitClientOnly initCommon initClientOnly
 * postInitCommon postInitClientOnly See MinecraftByExample class for more
 * information
 */
public class StartupCommon {
	public static Block blockPuncher; // this holds the unique instance of your block
	public static ItemBlock itemBlockPuncher; // this holds the unique instance of the ItemBlock corresponding
														// to your block

	public static void preInitCommon() {
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
	}

	public static void initCommon() {
	}

	public static void postInitCommon() {
	}
}
