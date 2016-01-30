package ganymedes01.ganyssurface.client.gui.inventory;

import ganymedes01.ganyssurface.inventory.ContainerPortableDualWorkTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

@SideOnly(Side.CLIENT)
public class GuiPortableDualWorkTable extends GuiDualWorkTable {

	public GuiPortableDualWorkTable(EntityPlayer player, int slot) {
		super(new ContainerPortableDualWorkTable(player, slot));
	}
}