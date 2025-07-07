package thelm.radiessency.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import thelm.radiessency.item.RadiessencyCreativeTabs;

public class SunRayAbsorberBlock extends PartialBlock {

	public SunRayAbsorberBlock() {
		super("radiessency:sun_ray_absorber", Material.IRON, MapColor.PURPLE, SoundType.METAL);
		setHardness(10F);
		setResistance(25F);
		setCreativeTab(RadiessencyCreativeTabs.MACHINES);
	}
}
