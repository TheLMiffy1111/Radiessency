package thelm.radiessency.tile;

import java.util.Collections;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import thelm.radiessency.api.radiessence.BalancedAmount;
import thelm.radiessency.api.radiessence.IRadiessenceStorage;
import thelm.radiessency.api.radiessence.IRadiessenceTransfer;
import thelm.radiessency.api.radiessence.IRadiessenceTransferHandler;
import thelm.radiessency.api.radiessence.RadiessenceStorageBeamOriginWrapper;
import thelm.radiessency.api.radiessence.TileRadiessenceStorage;
import thelm.radiessency.api.recipe.IIrradiationChamberRecipe;
import thelm.radiessency.api.transfer.DirectionalGlobalPos;
import thelm.radiessency.api.transfer.GlobalVec;
import thelm.radiessency.capability.RadiessencyCapabilities;
import thelm.radiessency.client.gui.IrradiationChamberGui;
import thelm.radiessency.container.IrradiationChamberContainer;
import thelm.radiessency.inventory.IrradiationChamberItemHandler;
import thelm.radiessency.inventory.IrradiationChamberUpgradeItemHandler;
import thelm.radiessency.recipe.IrradiationChamberRecipes;
import thelm.radiessency.util.ApiImpl;
import thelm.radiessency.util.MiscHelper;

public class IrradiationChamberTile extends BaseTile implements ITickable {

	public static int radiessenceCapacity = 5000;
	public static int transferRange = 8;
	public static int transferInterval = 20;
	public static int radiessenceBaseUsage = 1;

	public final IrradiationChamberItemHandler itemHandler = new IrradiationChamberItemHandler(this);
	public final IrradiationChamberUpgradeItemHandler upgradeItemHandler = new IrradiationChamberUpgradeItemHandler(this);
	public final TileRadiessenceStorage<IrradiationChamberTile> radiessenceStorage = new TileRadiessenceStorage<>(this, radiessenceCapacity);
	public final IRadiessenceStorage radiessenceStorageWrapper = new RadiessenceStorageBeamOriginWrapper<>(radiessenceStorage, true, false);

	public boolean powered = false;
	public boolean firstTick = true;

	public IRadiessenceTransferHandler radiessenceTransferHandler;

	public IIrradiationChamberRecipe recipe;
	public boolean recipeChanged;
	public float startBalance;
	public int cost = 0;
	public int progress = 0;

	@Override
	protected String getDefaultName() {
		return I18n.translateToLocal("tile.radiessency.irradiation_chamber.name");
	}

	@Override
	public IrradiationChamberItemHandler getItemHandler() {
		return itemHandler;
	}

	@Override
	public void update() {
		if(firstTick) {
			firstTick = false;
			updatePowered();
		}
		if(!world.isRemote) {
			if(shouldUpdate(transferInterval)) {
				transfer();
			}
			if(recipeChanged) {
				recipeChanged = false;
				updateRecipe();
			}
			if(recipe != null) {
				tickProcess();
			}
		}
	}

	public void updatePowered() {
		if(world.getRedstonePowerFromNeighbors(pos) > 0 != powered) {
			powered = !powered;
		}
	}

	public void updateRecipe() {
		IIrradiationChamberRecipe recipe = IrradiationChamberRecipes.findRecipe(itemHandler.getStacks().subList(0, 2), radiessenceStorage.getBalance());
		if(recipe != this.recipe) {
			this.recipe = recipe;
			if(recipe == null) {
				startBalance = -1;
				cost = 0;
				progress = 0;
			}
			else {
				startBalance = radiessenceStorage.getBalance();
				cost = recipe.getRadiessence();
				progress = 0;
			}
			markDirty();
		}
	}

	protected void tickProcess() {
		if(recipe != null) {
			int toExtract = MathHelper.clamp(cost-progress, 0, getUsage());
			int extracted = radiessenceStorage.extract(toExtract, false).amount();
			progress += extracted;
			if(progress >= cost) {
				ItemStack output = recipe.getOutput(itemHandler.getStacks().subList(0, 2), startBalance);
				ItemStack existing = itemHandler.getStackInSlot(2);
				if(existing.isEmpty() || ItemHandlerHelper.canItemStacksStack(output, existing) && output.getCount() + existing.getCount() <= existing.getMaxStackSize()) {
					int[] matches = MiscHelper.INSTANCE.findMatches(recipe.getIngredients(), itemHandler.getStacks().subList(0, 2));
					if(matches != null) {
						for(int slot : matches) {
							itemHandler.getStackInSlot(slot).shrink(1);
						}
						if(existing.isEmpty()) {
							itemHandler.setStackInSlot(2, output);
						}
						else {
							existing.grow(output.getCount());
						}
						startBalance = radiessenceStorage.getBalance();
						progress = 0;
					}
					updateRecipe();
					markDirty();
				}
			}
		}
	}

	public int getUsage() {
		// TODO upgrades
		return radiessenceBaseUsage;
	}

	public void transfer() {
		if(radiessenceStorage.getAmount() < radiessenceStorage.getCapacity()) {
			if(radiessenceTransferHandler == null || !radiessenceTransferHandler.isValid()) {
				int dimension = world.provider.getDimension();
				radiessenceTransferHandler = ApiImpl.INSTANCE.getRadiessenceTransferHandler(world, itemHandler, 3, DirectionalGlobalPos.inRange(dimension, pos, transferRange), Collections.singleton(pos), new GlobalVec(dimension, pos));
			}
			if(radiessenceTransferHandler.isValid()) {
				IRadiessenceTransfer transfer = radiessenceTransferHandler.prepare(radiessenceStorage.getCapacity() - radiessenceStorage.getAmount());
				BalancedAmount extracted = transfer.getExtracted();
				if(extracted.amount() > 0) {
					transfer.execute();
					radiessenceStorage.insert(extracted, false);
				}
			}
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}
		if(capability == RadiessencyCapabilities.RADIESSENCE) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return (T)itemHandler.getWrapperForDirection(facing);
		}
		if(capability == RadiessencyCapabilities.RADIESSENCE) {
			return (T)radiessenceStorageWrapper;
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		itemHandler.deserializeNBT(nbt.getCompoundTag("Inventory"));
		radiessenceStorage.deserializeNBT(nbt.getCompoundTag("Radiessence"));
		if(nbt.hasKey("Recipe")) {
			recipe = IrradiationChamberRecipes.getRecipe(nbt.getString("Recipe"));
			startBalance = nbt.getFloat("StartBalance");
			cost = nbt.getInteger("Cost");
			progress = nbt.getInteger("Progress");
		}
		else {
			recipe = null;
			cost = 0;
			progress = 0;
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setTag("Inventory", itemHandler.serializeNBT());
		nbt.setTag("Radiessence", radiessenceStorage.serializeNBT());
		if(recipe != null) {
			nbt.setString("Recipe", recipe.getKey());
			nbt.setFloat("StartBalance", startBalance);
			nbt.setInteger("Cost", cost);
			nbt.setInteger("Progress", progress);
		}
		return nbt;
	}

	@Override
	public void readSyncNBT(NBTTagCompound nbt) {
		super.readSyncNBT(nbt);
		upgradeItemHandler.deserializeNBT(nbt.getCompoundTag("Upgrade"));
	}

	@Override
	public NBTTagCompound writeSyncNBT(NBTTagCompound nbt) {
		super.writeSyncNBT(nbt);
		nbt.setTag("Upgrade", upgradeItemHandler.serializeNBT());
		return nbt;
	}

	@Override
	public IrradiationChamberContainer getServerGuiElement(EntityPlayer player, int id) {
		return new IrradiationChamberContainer(this, player.inventory);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IrradiationChamberGui getClientGuiElement(EntityPlayer player, int id) {
		return new IrradiationChamberGui(getServerGuiElement(player, id));
	}
}
