package thelm.radiessency.container;

import net.minecraft.entity.player.InventoryPlayer;
import thelm.radiessency.tile.CreativeRadiessenceSourceTile;

public class CreativeRadiessenceSourceContainer extends BaseTileContainer<CreativeRadiessenceSourceTile> {

	public CreativeRadiessenceSourceContainer(CreativeRadiessenceSourceTile tile, InventoryPlayer playerInventory) {
		super(tile, playerInventory);
		setupPlayerInventory();
	}

	@Override
	public int getPlayerInvX() {
		return 8;
	}

	@Override
	public int getPlayerInvY() {
		return 84;
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {}
}
