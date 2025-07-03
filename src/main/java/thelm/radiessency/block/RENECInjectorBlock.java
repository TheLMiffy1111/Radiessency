package thelm.radiessency.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import thelm.radiessency.item.RadiessencyCreativeTabs;

public class RENECInjectorBlock extends BasicBlock {

	public RENECInjectorBlock() {
		super("radiessency:renec_injector", Material.IRON, MapColor.PURPLE, SoundType.METAL);
		setHardness(10F);
		setResistance(25F);
		setCreativeTab(RadiessencyCreativeTabs.MACHINES);
	}
}
