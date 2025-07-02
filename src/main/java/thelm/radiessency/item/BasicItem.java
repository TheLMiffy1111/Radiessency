package thelm.radiessency.item;

import net.minecraft.item.Item;
import thelm.radiessency.client.model.IItemModelRegister;

public class BasicItem extends Item implements IItemModelRegister {

	public BasicItem(String registryName) {
		setTranslationKey(registryName.replace(':', '.'));
		setRegistryName(registryName);
	}
}
