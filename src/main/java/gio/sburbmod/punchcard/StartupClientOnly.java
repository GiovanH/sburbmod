package gio.sburbmod.punchcard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
//import net.minecraftforge.common.MinecraftForge;

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
		// MinecraftForge.EVENT_BUS.register(ModelBakeEventHandler.instance);
		// required in order for the renderer to know how to render your item.

		final int DEFAULT_ITEM_SUBTYPE = 0;
		final int VARIANTS = PunchCard.getVariants();
		final String PUNCHED_BASE_RESOURCE = "sburbmod:holes/punched_card_";
		ModelLoader.setCustomModelResourceLocation(StartupCommon.punchCard, DEFAULT_ITEM_SUBTYPE,
				new ModelResourceLocation("sburbmod:unpunched_card", "inventory"));
		for (int i = 1; i <= VARIANTS; i++) {
			int subtype = DEFAULT_ITEM_SUBTYPE + i;
					System.out.println(PUNCHED_BASE_RESOURCE + i + "/" + subtype);
			ModelLoader.setCustomModelResourceLocation(StartupCommon.punchCard, subtype,
					new ModelResourceLocation(PUNCHED_BASE_RESOURCE + i, "inventory"));
		}

		// ModelLoader.setCustomModelResourceLocation(StartupCommon.punchCardPunched,
		// DEFAULT_ITEM_SUBTYPE,
		// new ModelResourceLocation("sburbmod:punched_card", "inventory"));
	}

	public static void initClientOnly() {
	}

	public static void postInitClientOnly() {
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new CardColor(), StartupCommon.punchCard);
		// Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new
		// CardColor(), StartupCommon.punchCardUnified);
	}
}
