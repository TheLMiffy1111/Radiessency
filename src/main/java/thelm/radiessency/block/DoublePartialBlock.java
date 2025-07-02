package thelm.radiessency.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thelm.radiessency.api.block.Half;

public class DoublePartialBlock extends PartialBlock {

	public static final PropertyEnum<Half> HALF = PropertyEnum.create("half", Half.class);

	public DoublePartialBlock(String registryName, Material material, MapColor mapColor, SoundType soundType, BlockRenderLayer renderLayer) {
		super(registryName, material, mapColor, soundType, renderLayer);
		setDefaultState(blockState.getBaseState().withProperty(HALF, Half.BOTTOM));
	}

	public DoublePartialBlock(String registryName, Material material, MapColor mapColor, SoundType soundType) {
		super(registryName, material, mapColor, soundType);
		setDefaultState(blockState.getBaseState().withProperty(HALF, Half.BOTTOM));
	}

	public DoublePartialBlock(String registryName, Material material, SoundType soundType, BlockRenderLayer renderLayer) {
		super(registryName, material, soundType, renderLayer);
		setDefaultState(blockState.getBaseState().withProperty(HALF, Half.BOTTOM));
	}

	public DoublePartialBlock(String registryName, Material material, SoundType soundType) {
		super(registryName, material, soundType);
		setDefaultState(blockState.getBaseState().withProperty(HALF, Half.BOTTOM));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, HALF);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(HALF, Half.byIndex(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(HALF).getIndex();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(state.getValue(HALF) == Half.BOTTOM) {
			return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
		}
		else {
			return super.onBlockActivated(world, pos.down(), world.getBlockState(pos.down()), player, hand, facing, hitX, hitY, hitZ);
		}
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		return super.canPlaceBlockAt(world, pos) && (super.canPlaceBlockAt(world, pos.up()) || super.canPlaceBlockAt(world, pos.down()));
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		if(super.canPlaceBlockAt(world, pos.up())) {
			return getDefaultState().withProperty(HALF, Half.BOTTOM);
		}
		else if(super.canPlaceBlockAt(world, pos.down())) {
			return getDefaultState().withProperty(HALF, Half.TOP);
		}
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if(state.getValue(HALF) == Half.BOTTOM) {
			world.setBlockState(pos.up(), state.withProperty(HALF, Half.TOP), 3);
			super.onBlockPlacedBy(world, pos, state, placer, stack);
		}
		else {
			world.setBlockState(pos.down(), state.withProperty(HALF, Half.BOTTOM), 3);
			super.onBlockPlacedBy(world, pos.down(), world.getBlockState(pos.down()), placer, stack);
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		if(state.getValue(HALF) == Half.TOP) {
			BlockPos posCheck = pos.down();
			IBlockState stateCheck = world.getBlockState(posCheck);
			if(stateCheck.getBlock() != this || stateCheck.getValue(HALF) != Half.BOTTOM) {
				world.setBlockToAir(pos);
			}
		}
		else {
			BlockPos posCheck = pos.up();
			IBlockState stateCheck = world.getBlockState(posCheck);
			if(stateCheck.getBlock() != this || stateCheck.getValue(HALF) != Half.TOP) {
				world.setBlockToAir(pos);
				if(!world.isRemote) {
					dropBlockAsItem(world, pos, state, 0);
				}
			}
		}
	}

	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		if(player.capabilities.isCreativeMode && state.getValue(HALF) == Half.TOP) {
			BlockPos posCheck = pos.down();
			IBlockState stateCheck = world.getBlockState(posCheck);
			if(stateCheck.getBlock() == this && stateCheck.getValue(HALF) == Half.BOTTOM) {
				world.setBlockToAir(posCheck);
			}
		}
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return state.getValue(HALF) == Half.TOP ? Items.AIR : super.getItemDropped(state, rand, fortune);
	}
}
