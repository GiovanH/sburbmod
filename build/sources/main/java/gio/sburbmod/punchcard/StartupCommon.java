package gio.sburbmod.punchcard;

import net.minecraftforge.fml.common.registry.ForgeRegistries;
//import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * User: The Grey Ghost Date: 24/12/2014
 *
 * The Startup classes for this example are called during startup, in the
 * following order: preInitCommon preInitClientOnly initCommon initClientOnly
 * postInitCommon postInitClientOnly See MinecraftByExample class for more
 * information
 */
public class StartupCommon {
	public static PunchCard dowelPlain; // this holds the unique instance of your block

	public static void preInitCommon() {
		// each instance of your item should have two names:
		// 1) a registry name that is used to uniquely identify this item. Should be
		// unique within your mod. use lower case.
		// 2) an 'unlocalised name' that is used to retrieve the text name of your item
		// in the player's language. For example-
		// the unlocalised name might be "water", which is printed on the user's screen
		// as "Wasser" in German or
		// "aqua" in Italian.
		//
		// Multiple items can have the same unlocalised name - for example
		// +----RegistryName-------+----UnlocalisedName----+
		// | burning_candle + candle |
		// | extinguished_candle + candle |
		// +-----------------------+-----------------------+
		//
		dowelPlain = (PunchCard) (new PunchCard().setUnlocalizedName("sburbmod_dowel_empty"));
		dowelPlain.setRegistryName("dowel_empty");
		ForgeRegistries.ITEMS.register(dowelPlain);
	}

	public static void initCommon() {
	}

	public static void postInitCommon() {
	}
}
