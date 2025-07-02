package thelm.radiessency.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import thelm.radiessency.item.RadiessencyCreativeTabs;

public class RENECControllerBlock extends BasicBlock {

	public RENECControllerBlock() {
		super("radiessency:renec_controller", Material.IRON, MapColor.PURPLE, SoundType.METAL);
		setHardness(10F);
		setCreativeTab(RadiessencyCreativeTabs.MACHINES);
	}
}
