package thelm.radiessency.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import thelm.radiessency.item.RadiessencyCreativeTabs;

public class DarknessObeliskBlock extends DoublePartialBlock {

	public DarknessObeliskBlock() {
		super("radiessency:darkness_obelisk", Material.IRON, MapColor.BLACK, SoundType.METAL, BlockRenderLayer.CUTOUT);
		setHardness(10F);
		setCreativeTab(RadiessencyCreativeTabs.MACHINES);
	}
}
