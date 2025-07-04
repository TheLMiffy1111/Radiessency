package thelm.radiessency.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import thelm.radiessency.item.RadiessencyCreativeTabs;

public class MagmaticSmelteryBlock extends PartialBlock {

	public MagmaticSmelteryBlock() {
		super("radiessency:magmatic_smeltery", Material.IRON, MapColor.PURPLE, SoundType.METAL);
		setHardness(10F);
		setResistance(25F);
		setCreativeTab(RadiessencyCreativeTabs.MACHINES);
	}
}
