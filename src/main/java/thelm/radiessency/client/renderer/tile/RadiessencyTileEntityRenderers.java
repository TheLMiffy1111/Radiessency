package thelm.radiessency.client.renderer.tile;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import thelm.radiessency.tile.MatrixExtractorTile;
import thelm.radiessency.tile.RayTowerTile;

public class RadiessencyTileEntityRenderers {

	public static void registerTileEntityRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(MatrixExtractorTile.class, new MatrixExtractorRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(RayTowerTile.class, new RayTowerRenderer());
	}
}
