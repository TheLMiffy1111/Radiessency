package thelm.radiessency.integration.jei;

import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thelm.radiessency.api.recipe.IIrradiationChamberRecipe;
import thelm.radiessency.block.RadiessencyBlocks;
import thelm.radiessency.client.gui.IrradiationChamberGui;
import thelm.radiessency.integration.jei.category.IrradiationChamberCategory;
import thelm.radiessency.integration.jei.recipe.IrradiationChamberRecipeWrapper;
import thelm.radiessency.integration.jei.renderer.ResourceDrawable;
import thelm.radiessency.recipe.IrradiationChamberRecipes;

@JEIPlugin
public class RadiessencyJEIPlugin implements IModPlugin {

	public static final ResourceLocation ELEMENTS = new ResourceLocation("radiessency:textures/gui/elements.png");

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(new IrradiationChamberCategory());
	}

	@Override
	public void register(IModRegistry registry) {
		// Recipe Wrappers
		registry.handleRecipes(IIrradiationChamberRecipe.class, IrradiationChamberRecipeWrapper::new, IrradiationChamberCategory.UID);

		// Recipes
		registry.addRecipes(IrradiationChamberRecipes.RECIPES.values(), IrradiationChamberCategory.UID);

		// Transfer Handlers

		// Catalysts
		registry.addRecipeCatalyst(new ItemStack(RadiessencyBlocks.IRRADIATION_CHAMBER.get()), IrradiationChamberCategory.UID);

		// Click Areas
		registry.addRecipeClickArea(IrradiationChamberGui.class, 82, 38, 12, 10, IrradiationChamberCategory.UID);
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
	}
}
