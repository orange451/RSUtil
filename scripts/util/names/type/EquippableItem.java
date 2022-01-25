package scripts.util.names.type;

import scripts.util.misc.ItemWrapper;
import scripts.util.names.ItemIds;

public interface EquippableItem extends ItemWrapper {
	public EquipmentMaterial getMaterial();
	
	public ItemIds getItem();
	
	public EquipmentClass getType();
}
