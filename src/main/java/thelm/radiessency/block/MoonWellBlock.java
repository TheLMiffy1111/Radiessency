package thelm.radiessency.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import thelm.radiessency.item.RadiessencyCreativeTabs;

public class MoonWellBlock extends PartialBlock {

	public MoonWellBlock() {
		super("radiessency:moon_well", Material.IRON, MapColor.PURPLE, SoundType.METAL, BlockRenderLayer.CUTOUT);
		setHardness(10F);
		setCreativeTab(RadiessencyCreativeTabs.MACHINES);
	}
}
