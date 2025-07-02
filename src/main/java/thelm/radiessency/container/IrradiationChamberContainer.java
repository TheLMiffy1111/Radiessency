package thelm.radiessency.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.SlotItemHandler;
import thelm.radiessency.tile.IrradiationChamberTile;

public class IrradiationChamberContainer extends BaseTileContainer<IrradiationChamberTile> {

	public IrradiationChamberContainer(IrradiationChamberTile tile, InventoryPlayer playerInventory) {
		super(tile, playerInventory);
		addSlotToContainer(new SlotItemHandler(itemHandler, 3, 8, 53));
		addSlotToContainer(new SlotItemHandler(itemHandler, 0, 62, 26));
		addSlotToContainer(new SlotItemHandler(itemHandler, 1, 62, 44));
		addSlotToContainer(new SlotItemHandler(itemHandler, 2, 98, 35));
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
		return 4;
	}

	@Override
	public int getField(int id) {
		switch(id) {
		case 0: return Float.floatToIntBits(tile.radiessenceStorage.getBalance());
		case 1: return tile.radiessenceStorage.getAmount();
		case 2: return tile.cost;
		case 3: return tile.progress;
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
		case 2:
			tile.cost = value;
			break;
		case 3:
			tile.progress = value;
			break;
		}
	}
}
