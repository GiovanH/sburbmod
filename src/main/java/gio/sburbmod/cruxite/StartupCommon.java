package gio.sburbmod.cruxite;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
//import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * User: The Grey Ghost Date: 24/12/2014
 *
 * The Startup classes for this example are called during startup, in the
 * following order: preInitCommon preInitClientOnly initCommon initClientOnly
 * postInitCommon postInitClientOnly See MinecraftByExample class for more
 * information
 */
public class StartupCommon {
	public static DowelPlain dowelPlain; // this holds the unique instance of your block
	public static DowelCarved dowelCarved; // this holds the unique instance of your block

	public static CreativeTabs cruxiteTab;

	public static void preInitCommon() {

		cruxiteTab = new CreativeTabs("dowel_creative_tab") {
			@Override
			@SideOnly(Side.CLIENT)
			public ItemStack getTabIconItem() {
				return new ItemStack(dowelPlain);
			}
		};
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
		dowelPlain = (DowelPlain) (new DowelPlain().setUnlocalizedName("sburbmod_dowel_empty"));
		dowelPlain.setRegistryName("dowel_empty");
		ForgeRegistries.ITEMS.register(dowelPlain);

		dowelCarved = (DowelCarved) (new DowelCarved().setUnlocalizedName("sburbmod_dowel_carved"));
		dowelCarved.setRegistryName("dowel_carved");
		ForgeRegistries.ITEMS.register(dowelCarved);
		

		dowelCarved.setCreativeTab(cruxiteTab);
		
	}

	public static void initCommon() {
	}

	public static void postInitCommon() {
	}
}
