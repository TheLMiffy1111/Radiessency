package thelm.radiessency.tile;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RadiessencyTileEntities {

	public static void registerTileEntities() {
		// Delegates
		GameRegistry.registerTileEntity(DoubleDelegateTile.class, new ResourceLocation("radiessency:double_delegate"));

		// Radiessence Generators
		GameRegistry.registerTileEntity(MatrixExtractorTile.class, new ResourceLocation("radiessency:matrix_absorber"));

		// Transfer
		GameRegistry.registerTileEntity(RayTowerTile.class, new ResourceLocation("radiessency:radiessence_ray_tower"));

		// Functional
		GameRegistry.registerTileEntity(IrradiationChamberTile.class, new ResourceLocation("radiessency:irradiation_chamber"));

		// Creative
		GameRegistry.registerTileEntity(CreativeRadiessenceSourceTile.class, new ResourceLocation("radiessency:creative_radiessence_source"));
	}
}
