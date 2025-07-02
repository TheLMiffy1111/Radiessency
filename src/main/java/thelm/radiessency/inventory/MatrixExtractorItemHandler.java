package thelm.radiessency.inventory;

import net.minecraft.item.ItemStack;
import thelm.radiessency.api.item.ISoulStoneItem;
import thelm.radiessency.tile.MatrixExtractorTile;

public class MatrixExtractorItemHandler extends TileItemHandler<MatrixExtractorTile> {

	public MatrixExtractorItemHandler(MatrixExtractorTile tile) {
		super(tile, 1);
	}

	@Override
	public boolean isItemValid(int slot, ItemStack stack) {
		return stack.getItem() instanceof ISoulStoneItem;
	}

	@Override
	protected void onContentsChanged(int slot) {
		if(tile.hasWorld()) {
			tile.updateMatrix();
		}
		super.onContentsChanged(slot);
	}

	@Override
	protected void onLoad() {
		if(tile.hasWorld()) {
			tile.updateMatrix();
		}
	}
}
