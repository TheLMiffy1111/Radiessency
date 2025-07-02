package thelm.radiessency.client.event;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thelm.radiessency.block.RadiessencyBlocks;
import thelm.radiessency.client.gui.RadiessencyIcons;
import thelm.radiessency.client.model.IModelRegister;
import thelm.radiessency.client.renderer.RenderEventHandler;
import thelm.radiessency.client.renderer.tile.RadiessencyTileEntityRenderers;
import thelm.radiessency.event.CommonLifecycleEventHandler;
import thelm.radiessency.item.RadiessencyItems;

public class ClientLifecycleEventHandler extends CommonLifecycleEventHandler {

	@Override
	public void onPreInit(FMLPreInitializationEvent event) {
		super.onPreInit(event);
		OBJLoader.INSTANCE.addDomain("radiessency");
		MinecraftForge.EVENT_BUS.register(RenderEventHandler.INSTANCE);
	}

	@Override
	public void onBlockRegister(RegistryEvent.Register<Block> event) {
		super.onBlockRegister(event);
		RadiessencyTileEntityRenderers.registerTileEntityRenderers();
	}

	@SubscribeEvent
	public void onModelRegistry(ModelRegistryEvent event) {
		for(Block block : RadiessencyBlocks.BLOCKS) {
			if(block instanceof IModelRegister) {
				((IModelRegister)block).registerModels();
			}
		}
		for(Item item : RadiessencyItems.ITEMS) {
			if(item instanceof IModelRegister) {
				((IModelRegister)item).registerModels();
			}
		}
	}

	@SubscribeEvent
	public void onTextureStitchPre(TextureStitchEvent.Pre event) {
		RadiessencyIcons.registerIcons(event.getMap());
	}
}
