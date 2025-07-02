package thelm.radiessency.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import thelm.radiessency.item.RadiessencyCreativeTabs;

public class MithrilineFurnaceBlock extends PartialBlock {

	public MithrilineFurnaceBlock() {
		super("radiessency:mithriline_furnace", Material.IRON, MapColor.GREEN, SoundType.METAL);
		setHardness(10F);
		setCreativeTab(RadiessencyCreativeTabs.MACHINES);
	}
}
