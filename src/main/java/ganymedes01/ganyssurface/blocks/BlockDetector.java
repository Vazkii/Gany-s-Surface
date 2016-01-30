package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.InventoryUtils;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.GUIsID;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.tileentities.TileEntityBlockDetector;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class BlockDetector extends BlockContainer implements IConfigurable {

	@SideOnly(Side.CLIENT)
	private IIcon blockOn, blockOff;

	public BlockDetector() {
		super(Material.cloth);
		setHardness(0.2F);
		setStepSound(soundTypeWood);
		setBlockName(Utils.getUnlocalisedName(Strings.BLOCK_DETECTOR_NAME));
		setCreativeTab(GanysSurface.enableDislocators ? GanysSurface.surfaceTab : null);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return meta == 0 ? blockOff : blockOn;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockOn = reg.registerIcon(Utils.getBlockTexture(Strings.BLOCK_DETECTOR_NAME) + "_on");
		blockOff = reg.registerIcon(Utils.getBlockTexture(Strings.BLOCK_DETECTOR_NAME) + "_off");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityBlockDetector();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if (player.isSneaking())
			return false;
		else {
			TileEntityBlockDetector tile = Utils.getTileEntity(world, x, y, z, TileEntityBlockDetector.class);
			if (tile != null)
				player.openGui(GanysSurface.instance, GUIsID.BLOCK_DETECTOR.ordinal(), world, x, y, z);
			return true;
		}
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess access, int x, int y, int z, int side) {
		return access.getBlockMetadata(x, y, z);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		InventoryUtils.dropInventoryContents(world.getTileEntity(x, y, z));
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableDislocators;
	}
}