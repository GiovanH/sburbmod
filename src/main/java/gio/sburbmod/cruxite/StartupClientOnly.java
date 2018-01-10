package gio.sburbmod.cruxite;

import gio.sburbmod.cruxite.StartupCommon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

/**
 * User: The Grey Ghost Date: 24/12/2014
 *
 * The Startup classes for this example are called during startup, in the
 * following order: preInitCommon preInitClientOnly initCommon initClientOnly
 * postInitCommon postInitClientOnly See MinecraftByExample class for more
 * information
 */
public class StartupClientOnly {
	public static void preInitClientOnly() {
		// required in order for the renderer to know how to render your item.
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation("sburbmod:dowel_empty",
				"inventory");

		final int DEFAULT_ITEM_SUBTYPE = 0;
		ModelLoader.setCustomModelResourceLocation(StartupCommon.dowelPlain, DEFAULT_ITEM_SUBTYPE,
				itemModelResourceLocation);

		final int VARIANTS = DowelCarved.getVariants();
		final String PUNCHED_BASE_RESOURCE = "sburbmod:carvings/dowel_carved_";
		for (int i = 1; i <= VARIANTS; i++) {
			int subtype = DEFAULT_ITEM_SUBTYPE + i;
			System.out.println(PUNCHED_BASE_RESOURCE + i + "/" + subtype);
			ModelLoader.setCustomModelResourceLocation(StartupCommon.dowelCarved, subtype,
					new ModelResourceLocation(PUNCHED_BASE_RESOURCE + i, "inventory"));
		}

		// ModelLoader.setCustomModelResourceLocation(StartupCommon.dowelCarved,
		// DEFAULT_ITEM_SUBTYPE,
		// new ModelResourceLocation("sburbmod:dowel_carved", "inventory"));
	}

	public static void initClientOnly() {
	}

	public static void postInitClientOnly() {
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new DowelColor(), StartupCommon.dowelPlain);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new DowelColor(), StartupCommon.dowelCarved);
	}
}
