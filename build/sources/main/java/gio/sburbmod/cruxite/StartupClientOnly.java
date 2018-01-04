package gio.sburbmod.cruxite;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

/**
 * User: The Grey Ghost
 * Date: 24/12/2014
 *
 * The Startup classes for this example are called during startup, in the following order:
 *  preInitCommon
 *  preInitClientOnly
 *  initCommon
 *  initClientOnly
 *  postInitCommon
 *  postInitClientOnly
 *  See MinecraftByExample class for more information
 */
public class StartupClientOnly
{
  public static void preInitClientOnly()
  {
    // required in order for the renderer to know how to render your item.
    ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation("sburbmod:dowel_empty", "inventory");
	
    final int DEFAULT_ITEM_SUBTYPE = 0;
    ModelLoader.setCustomModelResourceLocation(StartupCommon.dowelPlain, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
  }

  public static void initClientOnly()
  {
		System.out.println("item" + " is loading!");
  }

  public static void postInitClientOnly()
  {
	    Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new DowelColor(), StartupCommon.dowelPlain);
  }
}
