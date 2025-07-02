package thelm.radiessency.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BeaconBaseBlock extends BasicBlock {

	public BeaconBaseBlock(String registryName, MapColor mapColor) {
		super(registryName, Material.IRON, mapColor, SoundType.METAL);
	}

	@Override
	public boolean isBeaconBase(IBlockAccess world, BlockPos pos, BlockPos beacon) {
		return true;
	}
}
