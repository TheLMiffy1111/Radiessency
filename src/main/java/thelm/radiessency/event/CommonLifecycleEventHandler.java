package thelm.radiessency.event;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.registries.IForgeRegistry;
import thelm.radiessency.Radiessency;
import thelm.radiessency.block.RadiessencyBlocks;
import thelm.radiessency.capability.RadiessencyCapabilities;
import thelm.radiessency.item.RadiessencyItems;
import thelm.radiessency.network.GuiHandler;
import thelm.radiessency.network.PacketHandler;
import thelm.radiessency.recipe.RadiessencyRecipes;
import thelm.radiessency.tile.RadiessencyTileEntities;
import thelm.radiessency.util.ApiImpl;

public class CommonLifecycleEventHandler {

	public void onPreInit(FMLPreInitializationEvent event) {
		ApiImpl.INSTANCE.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(Radiessency.MOD_ID, GuiHandler.INSTANCE);
		RadiessencyCapabilities.registerCapabilities();
		PacketHandler.registerPackets();
		MinecraftForge.EVENT_BUS.register(WorldEventHandler.INSTANCE);
	}

	@SubscribeEvent
	public void onBlockRegister(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();
		RadiessencyBlocks.registerBlocks(registry);
		RadiessencyTileEntities.registerTileEntities();
	}

	@SubscribeEvent
	public void onItemRegister(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		RadiessencyBlocks.registerItemBlocks(registry);
		RadiessencyItems.registerItems(registry);
	}

	@SubscribeEvent
	public void onEntityRegister(RegistryEvent.Register<EntityEntry> event) {
		//IForgeRegistry<EntityEntry> registry = event.getRegistry();
	}

	@SubscribeEvent
	public void onRecipeRegister(RegistryEvent.Register<IRecipe> event) {
		IForgeRegistry<IRecipe> registry = event.getRegistry();
		RadiessencyRecipes.registerRecipes(registry);
	}

	public void onInit(FMLInitializationEvent event) {

	}

	public void onPostInit(FMLPostInitializationEvent event) {

	}
}
