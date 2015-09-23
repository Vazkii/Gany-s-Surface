package ganymedes01.ganyssurface.items;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.IConfigurable;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.Strings;
import net.minecraft.item.ItemFood;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class Beetroot extends ItemFood implements IConfigurable {

	public Beetroot() {
		super(1, 0.6F, false);
		setTextureName("beetroot");
		setUnlocalizedName(Utils.getUnlocalisedName(Strings.BEETROOT_NAME));
		setCreativeTab(GanysSurface.enableBeetroots ? GanysSurface.surfaceTab : null);
	}

	@Override
	public boolean isEnabled() {
		return GanysSurface.enableBeetroots;
	}
}