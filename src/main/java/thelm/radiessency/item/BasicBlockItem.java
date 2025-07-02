package thelm.radiessency.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class BasicBlockItem extends ItemBlock {

	public BasicBlockItem(Block block) {
		super(block);
		setRegistryName(block.getRegistryName());
	}
}
