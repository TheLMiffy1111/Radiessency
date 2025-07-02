package thelm.radiessency.inventory;

import thelm.radiessency.tile.IrradiationChamberTile;

public class IrradiationChamberUpgradeItemHandler extends TileItemHandler<IrradiationChamberTile> {

	public IrradiationChamberUpgradeItemHandler(IrradiationChamberTile tile) {
		super(tile, 1);
	}

	@Override
	protected void onContentsChanged(int slot) {
		super.onContentsChanged(slot);
		if(tile != null) {
			tile.syncTile(false);
		}
	}
}
