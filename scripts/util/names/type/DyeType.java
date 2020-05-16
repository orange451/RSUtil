package scripts.util.names.type;

import scripts.util.misc.ItemWrapper;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;

public enum DyeType implements ItemWrapper {
	BLUE(ItemNames.BLUE_DYE, ItemNames.WOAD_LEAF),
	YELLOW(ItemNames.YELLOW_DYE, ItemNames.ONION),
	RED(ItemNames.RED_DYE, ItemNames.REDBERRIES);
	
	private ItemIds item;
	private ItemIds requiredItem;
	
	DyeType(ItemIds item, ItemIds requiredItem) {
		this.item = item;
		this.requiredItem = requiredItem;
	}
	
	public ItemIds getItem() {
		return this.item;
	}
	
	public ItemIds getRequiredItem() {
		return this.requiredItem;
	}
}
