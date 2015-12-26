package ganymedes01.ganyssurface.tileentities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.inventory.ContainerOrganicMatterCompressor;
import ganymedes01.ganyssurface.lib.Strings;
import ganymedes01.ganyssurface.recipes.OrganicMatterRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class TileEntityOrganicMatterCompressor extends GanysInventory implements ISidedInventory {

	private static final int BLOCK_OF_COAL = 10, COAL = 9, WORK_TIME = 900, NEEDED_MATTER = 144;
	private static final int[] SLOTS = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

	private int currentTime, organicMatter;

	public TileEntityOrganicMatterCompressor() {
		super(11, Strings.ORGANIC_MATTER_COMPRESSOR_NAME);
		currentTime = WORK_TIME;
		organicMatter = 0;
	}

	@Override
	public void updateEntity() {
		if (worldObj.isRemote)
			return;
		if (yCoord > GanysSurface.maxLevelOMCWorks)
			return;
		if (inventory[BLOCK_OF_COAL] == null || inventory[BLOCK_OF_COAL].stackSize < 64) {
			if (organicMatter < NEEDED_MATTER)
				organicMatter = collectOrganicMatter();
			if (organicMatter >= NEEDED_MATTER)
				if (inventory[COAL] != null) {
					currentTime--;
					if (currentTime <= 0)
						generateBlockOfCoal();
				} else
					currentTime = WORK_TIME;
		}
	}

	private int collectOrganicMatter() {
		int matter = 0;
		for (int i = 0; i < 9; i++) {
			if (inventory[i] != null) {
				int yield = OrganicMatterRegistry.getOrganicYield(inventory[i]);
				if (yield > 0) {
					matter += yield;
					inventory[i].stackSize--;
					if (inventory[i].stackSize <= 0)
						inventory[i] = null;
				} else
					continue;
			}
			if (organicMatter + matter >= NEEDED_MATTER)
				return organicMatter + matter;
		}
		return organicMatter + matter;
	}

	private void generateBlockOfCoal() {
		if (inventory[BLOCK_OF_COAL] == null)
			inventory[BLOCK_OF_COAL] = new ItemStack(Blocks.coal_block);
		else
			inventory[BLOCK_OF_COAL].stackSize++;

		inventory[COAL].stackSize--;
		if (inventory[COAL].stackSize <= 0)
			inventory[COAL] = null;
		currentTime = WORK_TIME;
		organicMatter -= NEEDED_MATTER;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (slot == 9)
			return stack.getItem() == Items.coal && stack.getItemDamage() == 0;
		else if (slot < 9)
			return OrganicMatterRegistry.isOrganicMatter(stack);
		else
			return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return SLOTS;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot == BLOCK_OF_COAL;
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		currentTime = data.getInteger("currentTime");
		organicMatter = data.getInteger("organicMatter");
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		data.setInteger("currentTime", currentTime);
		data.setInteger("organicMatter", organicMatter);
	}

	public void getGUIData(int id, int value) {
		switch (id) {
			case 0:
				currentTime = value;
				break;
			case 1:
				organicMatter = value;
				break;
		}
	}

	public void sendGUIData(ContainerOrganicMatterCompressor compressor, ICrafting craft) {
		craft.sendProgressBarUpdate(compressor, 0, currentTime);
		craft.sendProgressBarUpdate(compressor, 1, organicMatter);
	}

	@SideOnly(Side.CLIENT)
	public float getOrganicMatter() {
		return (float) organicMatter / (float) NEEDED_MATTER;
	}

	@SideOnly(Side.CLIENT)
	public int getScaledWorkTime(int scale) {
		if (currentTime == 0)
			return 0;
		return scale - (int) (scale * ((float) currentTime / (float) WORK_TIME));
	}
}