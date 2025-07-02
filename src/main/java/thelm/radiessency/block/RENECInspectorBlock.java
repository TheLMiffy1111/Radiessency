package thelm.radiessency.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import thelm.radiessency.item.RadiessencyCreativeTabs;

public class RENECInspectorBlock extends BasicBlock {

	public RENECInspectorBlock() {
		super("radiessency:renec_inspector", Material.IRON, MapColor.PURPLE, SoundType.METAL);
		setHardness(10F);
		setCreativeTab(RadiessencyCreativeTabs.MACHINES);
	}
}
