package thelm.radiessency.tile;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RadiessencyTileEntities {

	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(DoubleDelegateTile.class, new ResourceLocation("radiessency:double_delegate"));
		GameRegistry.registerTileEntity(MatrixExtractorTile.class, new ResourceLocation("radiessency:matrix_absorber"));
		GameRegistry.registerTileEntity(RayTowerTile.class, new ResourceLocation("radiessency:radiessence_ray_tower"));
		GameRegistry.registerTileEntity(IrradiationChamberTile.class, new ResourceLocation("radiessency:irradiation_chamber"));
	}
}
