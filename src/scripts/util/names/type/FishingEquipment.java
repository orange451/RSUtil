package scripts.util.names.type;

import java.util.ArrayList;
import java.util.List;

import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;

public enum FishingEquipment {
	SMALL_FISHING_NET(ItemNames.SMALL_FISHING_NET),
	FISHING_ROD(ItemNames.FISHING_ROD, ItemNames.FISHING_BAIT);
	
	private ItemIds primaryItem;
	private ItemIds[] subItems;
	
	private FishingEquipment(ItemIds primaryItem, ItemIds... subItems) {
		this.primaryItem = primaryItem;
		this.subItems = subItems;
	}
	
	public ItemIds getPrimaryItem() {
		return this.primaryItem;
	}
	
	public ItemIds[] getSubItems() {
		return this.subItems;
	}

	public ItemIds[] getAllItems() {
		List<ItemIds> items = new ArrayList<>();
		items.add(getPrimaryItem());
		for (int i = 0; i < subItems.length; i++) {
			items.add(subItems[i]);
		}
		
		return items.toArray(new ItemIds[items.size()]);
	}
}
