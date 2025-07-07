package thelm.radiessency.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thelm.radiessency.item.RadiessencyCreativeTabs;
import thelm.radiessency.tile.DoubleDelegateTile;
import thelm.radiessency.tile.RayTowerTile;

public class RayTowerBlock extends DoublePartialBlock implements ITileEntityProvider {

	public static final AxisAlignedBB AABB = new AxisAlignedBB(0.0625, 0, 0.0625, 0.9375, 1, 0.9375);

	public RayTowerBlock() {
		super("radiessency:ray_tower", Material.IRON, MapColor.PURPLE, SoundType.METAL, BlockRenderLayer.CUTOUT);
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
		if(meta == 0) {
			return new RayTowerTile();
		}
		return new DoubleDelegateTile();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return false;
	}
}
