package gio.sburbmod.punchcard;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
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
	public static PunchCard punchCard; // this holds the unique instance of your block
	// public static PunchCard punchCardPunched = punchCard; // this holds the
	// unique instance of your block
	// public static PunchCard punchCardUnified;
	public static CreativeTabs cardTab;

	public static void preInitCommon() {

		cardTab = new CreativeTabs("punchcard_creative_tab") {
			@Override
			@SideOnly(Side.CLIENT)
			public ItemStack getTabIconItem() {
				return new ItemStack(punchCard);
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
		punchCard = (PunchCard) (new PunchCard().setUnlocalizedName("sburbmod_punchcard"));
		punchCard.setRegistryName("punchcard");
		ForgeRegistries.ITEMS.register(punchCard);


		punchCard.setCreativeTab(cardTab);
		
		// punchCardUnified = (PunchCard) (new PunchCard());
		// punchCardUnified.setRegistryName("punchcard_u");
		// ForgeRegistries.ITEMS.register(punchCardUnified);

		// punchCardPunched = (PunchCard) (new
		// PunchCard().setUnlocalizedName("sburbmod_punchcard_punched"));
		// punchCardPunched.setRegistryName("punchcard_punched");
		// ForgeRegistries.ITEMS.register(punchCardPunched);

	}

	public static void initCommon() {
	}

	public static void postInitCommon() {
	}
}
