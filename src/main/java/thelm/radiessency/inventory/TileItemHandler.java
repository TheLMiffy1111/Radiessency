package thelm.radiessency.inventory;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class TileItemHandler<T extends TileEntity> extends ItemStackHandler {

	public final T tile;
	protected Map<EnumFacing, IItemHandlerModifiable> wrapperMap = new IdentityHashMap<>(7);

	public TileItemHandler(T tile, int size) {
		super(size);
		this.tile = tile;
	}

	public List<ItemStack> getStacks() {
		return Collections.unmodifiableList(stacks);
	}

	@Override
	protected void onContentsChanged(int slot) {
		if(tile != null) {
			tile.markDirty();
		}
	}

	public IItemHandlerModifiable getWrapperForDirection(EnumFacing direction) {
		return this;
	}
}
