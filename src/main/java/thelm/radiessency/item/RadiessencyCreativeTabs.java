package thelm.radiessency.item;

import java.util.function.Supplier;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import thelm.radiessency.block.RadiessencyBlocks;

public class RadiessencyCreativeTabs {

	public static final CreativeTabs BLOCKS = new CreativeTabBasic("radiessency.blocks",
			()->new ItemStack(RadiessencyBlocks.RADISTEEL_PANEL.get()));
	public static final CreativeTabs MACHINES = new CreativeTabBasic("radiessency.machines",
			()->new ItemStack(RadiessencyBlocks.RADISTEEL_PANEL.get()));
	public static final CreativeTabs ITEMS = new CreativeTabBasic("radiessency.items",
			()->new ItemStack(RadiessencyItems.RADISTEEL_INGOT.get()));
	public static final CreativeTabs TOOLS = new CreativeTabBasic("radiessency.tools",
			()->new ItemStack(RadiessencyItems.RADISTEEL_INGOT.get()));

	public static class CreativeTabBasic extends CreativeTabs {
		public final Supplier<ItemStack> iconCreator;

		public CreativeTabBasic(String label, Supplier<ItemStack> iconCreator) {
			super(label);
			this.iconCreator = iconCreator;
		}

		@Override
		public ItemStack createIcon() {
			return iconCreator.get();
		}
	}
}
