package thelm.radiessency.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thelm.radiessency.item.RadiessencyCreativeTabs;
import thelm.radiessency.tile.MatrixExtractorTile;

public class MatrixExtractorBlock extends PartialBlock implements ITileEntityProvider {

	public static final AxisAlignedBB AABB = new AxisAlignedBB(0, 0, 0, 1, 0.875, 1);

	public MatrixExtractorBlock() {
		super("radiessency:matrix_extractor", Material.IRON, MapColor.STONE, SoundType.METAL);
		setHardness(10F);
		setResistance(25F);
		setCreativeTab(RadiessencyCreativeTabs.MACHINES);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return AABB;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new MatrixExtractorTile();
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof MatrixExtractorTile) {
			((MatrixExtractorTile)tile).updatePowered();
		}
	}
}
