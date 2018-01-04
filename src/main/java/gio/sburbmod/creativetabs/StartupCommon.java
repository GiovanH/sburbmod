package gio.sburbmod.creativetabs;

public class StartupCommon {
	public static SearchTab allModItemsTab; // will hold our second custom creative tab displaying all MBE items

	public static void preInitCommon() {

		allModItemsTab = new SearchTab("Sburb");
	}

	public static void initCommon() {
	}

	public static void postInitCommon() {
	}
}
