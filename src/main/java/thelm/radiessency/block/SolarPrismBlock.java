package thelm.radiessency.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import thelm.radiessency.item.RadiessencyCreativeTabs;

public class SolarPrismBlock extends PartialBlock {

	public static final AxisAlignedBB AABB = new AxisAlignedBB(0, 0.4375, 0, 1, 0.5625, 1);

	public SolarPrismBlock() {
		super("radiessency:solar_prism", Material.IRON, MapColor.YELLOW, SoundType.GLASS, BlockRenderLayer.TRANSLUCENT);
		setHardness(10F);
		setResistance(25F);
		setCreativeTab(RadiessencyCreativeTabs.MACHINES);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return AABB;
	}
}
