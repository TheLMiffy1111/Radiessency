package thelm.radiessency;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import thelm.radiessency.event.CommonLifecycleEventHandler;

@Mod(
		modid = Radiessency.MOD_ID,
		name = Radiessency.NAME,
		version = Radiessency.VERSION,
		dependencies = Radiessency.DEPENDENCIES,
		guiFactory = Radiessency.GUI_FACTORY
		)
public class Radiessency {

	public static final String MOD_ID = "radiessency";
	public static final String NAME = "Radiessency";
	public static final String VERSION = "0@VERSION@";
	public static final String DEPENDENCIES = "";
	public static final String GUI_FACTORY = "";
	@SidedProxy(
			clientSide = "thelm.radiessency.client.event.ClientLifecycleEventHandler",
			serverSide = "thelm.radiessency.event.CommonLifecycleEventHandler",
			modId = Radiessency.MOD_ID)
	public static CommonLifecycleEventHandler lifecycleEventHandler;

	@EventHandler
	public void onConstruction(FMLConstructionEvent event) {
		MinecraftForge.EVENT_BUS.register(lifecycleEventHandler);
	}

	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event) {
		lifecycleEventHandler.onPreInit(event);
	}

	@EventHandler
	public void onInit(FMLInitializationEvent event) {
		lifecycleEventHandler.onInit(event);
	}

	@EventHandler
	public void onPostInit(FMLPostInitializationEvent event) {
		lifecycleEventHandler.onPostInit(event);
	}
}
