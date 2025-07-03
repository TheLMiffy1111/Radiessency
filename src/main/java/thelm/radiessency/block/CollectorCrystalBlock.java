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

public abstract class CollectorCrystalBlock extends DoublePartialBlock {

	public static final AxisAlignedBB AABB = new AxisAlignedBB(0.25, 0, 0.25, 0.75, 1, 0.75);

	public CollectorCrystalBlock(String registryName) {
		super(registryName, Material.IRON, MapColor.AIR, SoundType.GLASS, BlockRenderLayer.TRANSLUCENT);
		setHardness(10F);
		setResistance(25F);
		setCreativeTab(RadiessencyCreativeTabs.MACHINES);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return AABB;
	}

	public static class Pulsatile extends CollectorCrystalBlock {

		public Pulsatile() {
			super("radiessency:pulsatile_collector_crystal");
		}
	}

	public static class Alterant extends CollectorCrystalBlock {

		public Alterant() {
			super("radiessency:alterant_collector_crystal");
		}
	}

	public static class Void extends CollectorCrystalBlock {

		public Void() {
			super("radiessency:void_collector_crystal");
		}
	}

	public static class Demonic extends CollectorCrystalBlock {

		public Demonic() {
			super("radiessency:demonic_collector_crystal");
		}
	}
}
