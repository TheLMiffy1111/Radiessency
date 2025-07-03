package thelm.radiessency.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import thelm.radiessency.item.RadiessencyCreativeTabs;

public class RENECExpanderBlock extends BasicBlock {

	public RENECExpanderBlock() {
		super("radiessency:renec_expander", Material.IRON, MapColor.PURPLE, SoundType.METAL);
		setHardness(10F);
		setResistance(25F);
		setCreativeTab(RadiessencyCreativeTabs.MACHINES);
	}
}
