package thelm.radiessency.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import thelm.radiessency.item.RadiessencyCreativeTabs;
import thelm.radiessency.tile.CreativeRadiessenceSourceTile;

public class CreativeRadiessenceSourceBlock extends BasicBlock implements ITileEntityProvider {

	public CreativeRadiessenceSourceBlock() {
		super("radiessency:creative_radiessence_source", Material.IRON, MapColor.STONE, SoundType.METAL);
		setHardness(10F);
		setResistance(25F);
		setCreativeTab(RadiessencyCreativeTabs.MACHINES);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new CreativeRadiessenceSourceTile();
	}
}
