package thelm.radiessency.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.IForgeRegistry;
import thelm.radiessency.block.RadiessencyBlocks;

public class RadiessencyRecipes {

	public static void registerRecipes(IForgeRegistry<IRecipe> registry) {
		registerCraftingRecipes(registry);
		registerIrradiationChamberRecipes();
	}

	private static void registerCraftingRecipes(IForgeRegistry<IRecipe> registry) {
	}

	private static void registerIrradiationChamberRecipes() {
		IrradiationChamberRecipes.registerRecipe(
				"radiessency:fortified_stone",
				ing("stone"), ing("ingotIron"),
				new ItemStack(RadiessencyBlocks.FORTIFIED_STONE.get(), 2),
				0, 2, 10);
		IrradiationChamberRecipes.registerRecipe(
				"radiessency:fortified_glass",
				ing("blockGlass"), ing("ingotIron"),
				new ItemStack(RadiessencyBlocks.FORTIFIED_GLASS.get(), 2),
				0, 2, 10);
	}

	private static Ingredient ing(Object obj) {
		return CraftingHelper.getIngredient(obj);
	}
}
