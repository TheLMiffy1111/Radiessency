package thelm.radiessency.item;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thelm.radiessency.api.item.IBindingGemItem;

public class BindingGemItem extends MarkerItem implements IBindingGemItem {

	public static final ModelResourceLocation MODEL_LOCATION = new ModelResourceLocation("radiessency:binding_gem#inventory");
	public static final ModelResourceLocation MODEL_LOCATION_BOUND = new ModelResourceLocation("radiessency:binding_gem_bound#inventory");

	protected BindingGemItem() {
		super("radiessency:binding_gem");
		setCreativeTab(RadiessencyCreativeTabs.TOOLS);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		ModelLoader.setCustomMeshDefinition(this, stack->isBound(stack) ? MODEL_LOCATION_BOUND : MODEL_LOCATION);
		ModelBakery.registerItemVariants(this, MODEL_LOCATION, MODEL_LOCATION_BOUND);
	}
}
