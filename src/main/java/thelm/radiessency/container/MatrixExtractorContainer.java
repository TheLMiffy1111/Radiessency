package thelm.radiessency.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.SlotItemHandler;
import thelm.radiessency.tile.MatrixExtractorTile;

public class MatrixExtractorContainer extends BaseTileContainer<MatrixExtractorTile> {

	public MatrixExtractorContainer(MatrixExtractorTile tile, InventoryPlayer playerInventory) {
		super(tile, playerInventory);
		addSlotToContainer(new SlotItemHandler(itemHandler, 0, 80, 53));
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
		return 2;
	}

	@Override
	public int getField(int id) {
		switch(id) {
		case 0: return Float.floatToIntBits(tile.radiessenceStorage.getBalance());
		case 1: return tile.radiessenceStorage.getAmount();
		}
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		switch(id) {
		case 0:
			tile.radiessenceStorage.setBalance(Float.intBitsToFloat(value));
			break;
		case 1:
			tile.radiessenceStorage.setAmount(value);
			break;
		}
	}
}
