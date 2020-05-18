package scripts.util.names.type;

import scripts.util.misc.ItemWrapper;
import scripts.util.misc.NameFormatter;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;

public enum DyeType implements ItemWrapper {
	BLUE(ItemNames.BLUE_DYE, ItemNames.WOAD_LEAF, 2),
	YELLOW(ItemNames.YELLOW_DYE, ItemNames.ONION, 2),
	RED(ItemNames.RED_DYE, ItemNames.REDBERRIES, 3);
	
	private ItemIds item;
	private ItemIds requiredItem;
	private int amtRequiredItem;
	
	DyeType(ItemIds item, ItemIds requiredItem, int amtRequiredItem) {
		this.item = item;
		this.requiredItem = requiredItem;
		this.amtRequiredItem = amtRequiredItem;
	}
	
	public ItemIds getItem() {
		return this.item;
	}
	
	public ItemIds getRequiredItem() {
		return this.requiredItem;
	}
	
	public int getRequiredItemAmount() {
		return this.amtRequiredItem;
	}

	public String getName() {
		return NameFormatter.formatName(getItem());
	}
}
