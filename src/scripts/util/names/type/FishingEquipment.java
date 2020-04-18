package scripts.util.names.type;

import java.util.ArrayList;
import java.util.List;

import scripts.util.misc.NPCWrapper;
import scripts.util.misc.NameFormatter;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;
import scripts.util.names.NPCNames;

public enum FishingEquipment implements NPCWrapper {
	SMALL_FISHING_NET(NPCNames.FISHING_SPOT, "Net", "Small Net", ItemNames.SMALL_FISHING_NET),
	FISHING_ROD(NPCNames.FISHING_SPOT, "Bait", ItemNames.FISHING_ROD, ItemNames.FISHING_BAIT);
	
	private NPCNames NPCType;
	private String actionName;
	private ItemIds primaryItem;
	private ItemIds[] subItems;
	private String displayName;
	
	private FishingEquipment(NPCNames npc, String action, String displayName, ItemIds primaryItem, ItemIds... subItems) {
		this.primaryItem = primaryItem;
		this.subItems = subItems;
		this.NPCType = npc;
		this.actionName = action;
		this.displayName = displayName;
	}
	
	private FishingEquipment(NPCNames npc, String action, ItemIds primaryItem, ItemIds... subItems) {
		this(npc, action, null, primaryItem, subItems);
	}
	
	public String getName() {
		return displayName==null?NameFormatter.get(this.toString()):this.displayName;
	}

	@Override
	public NPCNames getNPCName() {
		return NPCType;
	}
	
	public String getActionName() {
		return this.actionName;
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
