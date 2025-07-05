package thelm.radiessency.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PartialBlock extends BasicBlock {

	public PartialBlock(String registryName, Material material, MapColor mapColor, SoundType soundType, BlockRenderLayer renderLayer) {
		super(registryName, material, mapColor, soundType, renderLayer);
	}

	public PartialBlock(String registryName, Material material, MapColor mapColor, SoundType soundType) {
		super(registryName, material, mapColor, soundType);
	}

	public PartialBlock(String registryName, Material material, SoundType soundType, BlockRenderLayer renderLayer) {
		super(registryName, material, soundType, renderLayer);
	}

	public PartialBlock(String registryName, Material material, SoundType soundType) {
		super(registryName, material, soundType);
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		if(world.getBlockState(pos.offset(side)).getBlock() == this) {
			return false;
		}
		return super.shouldSideBeRendered(state, world, pos, side);
	}

	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		return false;
	}
}
