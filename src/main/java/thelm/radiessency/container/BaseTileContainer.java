package thelm.radiessency.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import thelm.radiessency.tile.BaseTile;

public abstract class BaseTileContainer<T extends BaseTile> extends BaseContainer {

	public final T tile;

	public BaseTileContainer(T tile, InventoryPlayer playerInventory) {
		super(tile != null ? tile.getItemHandler() : null, playerInventory);
		this.tile = tile;
	}

	@Override
	public ITextComponent getDisplayName() {
		return tile.getDisplayName();
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		if(tile != null) {
			BlockPos pos = tile.getPos();
			return tile.getWorld().getTileEntity(pos) == tile && pos.distanceSqToCenter(player.posX, player.posY, player.posZ) <= 64;	
		}
		return true;
	}
}
