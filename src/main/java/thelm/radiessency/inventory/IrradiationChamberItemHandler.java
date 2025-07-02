package thelm.radiessency.inventory;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.IItemHandlerModifiable;
import thelm.radiessency.tile.IrradiationChamberTile;

public class IrradiationChamberItemHandler extends TileItemHandler<IrradiationChamberTile> {

	public IrradiationChamberItemHandler(IrradiationChamberTile tile) {
		super(tile, 4);
	}

	@Override
	public IItemHandlerModifiable getWrapperForDirection(EnumFacing direction) {
		return wrapperMap.computeIfAbsent(direction, d->new IrradiationChamberItemHandlerWrapper(this, d));
	}

	@Override
	protected void onContentsChanged(int slot) {
		switch(slot) {
		case 0: case 1:
			tile.recipeChanged = true;
			break;
		case 3:
			tile.radiessenceTransferHandler = null;
			break;
		}
		super.onContentsChanged(slot);
	}
}
