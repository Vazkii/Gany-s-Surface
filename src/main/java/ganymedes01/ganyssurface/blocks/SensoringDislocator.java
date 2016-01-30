package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.ModItems;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntitySensoringDislocator;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class SensoringDislocator extends Dislocator {

	public SensoringDislocator() {
		super();
		setBlockName(Utils.getUnlocalisedName(Strings.SENSORING_DISLOCATOR_NAME));
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbour) {
		if (world.isRemote)
			return;
		TileEntitySensoringDislocator tile = Utils.getTileEntity(world, x, y, z, TileEntitySensoringDislocator.class);
		if (tile != null)
			tile.redstoneStrength = world.getStrongestIndirectPower(x, y, z);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (player.isSneaking())
			return false;
		else {
			if (player.getCurrentEquippedItem() != null)
				if (player.getCurrentEquippedItem().getItem() == ModItems.woodenWrench)
					return false;

			TileEntitySensoringDislocator tile = Utils.getTileEntity(world, x, y, z, TileEntitySensoringDislocator.class);
			if (tile != null)
				player.openGui(GanysSurface.instance, GUIsID.BLOCK_DETECTOR.ordinal(), world, x, y, z);
			return true;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockSide = reg.registerIcon(Utils.getBlockTexture(Strings.SENSORING_DISLOCATOR_NAME) + "_side");
		blockFront = reg.registerIcon(Utils.getBlockTexture(Strings.SENSORING_DISLOCATOR_NAME) + "_front");
		blockBack = reg.registerIcon(Utils.getBlockTexture(Strings.SENSORING_DISLOCATOR_NAME) + "_back");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntitySensoringDislocator();
	}
}