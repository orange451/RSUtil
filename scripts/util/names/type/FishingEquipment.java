package scripts.util.names.type;

import java.util.ArrayList;
import java.util.List;

import scripts.util.misc.NameFormatter;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;
import scripts.util.names.NPCNames;

public enum FishingEquipment {
	BIG_FISHING_NET(NPCNames.FISHING_SPOT, new String[] {"Big Net", "Net"}, 16, "Big Fishing Net", ItemNames.BIG_FISHING_NET),
	SMALL_FISHING_NET(NPCNames.FISHING_SPOT, new String[] {"Small Net", "Net"}, 1, "Small Net", ItemNames.SMALL_FISHING_NET),
	FISHING_ROD(new NPCNames[] {NPCNames.FISHING_SPOT, NPCNames.ROD_FISHING_SPOT}, "Bait", 5, ItemNames.FISHING_ROD, ItemNames.FISHING_BAIT),
	FLY_FISHING_ROD(NPCNames.ROD_FISHING_SPOT, "Lure", 20, ItemNames.FLY_FISHING_ROD, ItemNames.FEATHER),
	HARPOON(NPCNames.FISHING_SPOT, "Harpoon", 35, ItemNames.HARPOON),
	LOBSTER_POT(NPCNames.FISHING_SPOT, "Cage", 45, ItemNames.LOBSTER_POT);
	
	private NPCNames[] NPCTypes;
	private String[] actions;
	private ItemIds primaryItem;
	private ItemIds[] subItems;
	private String displayName;
	private int minimumLevel;
	
	private FishingEquipment(NPCNames[] npcs, String[] actions, int minimumLevel, String displayName, ItemIds primaryItem, ItemIds... subItems) {
		this.primaryItem = primaryItem;
		this.subItems = subItems;
		this.NPCTypes = npcs;
		this.actions = actions;
		this.displayName = displayName;
		this.minimumLevel = minimumLevel;
	}
	
	private FishingEquipment(NPCNames[] npcs, String action, int minimumLevel, ItemIds primaryItem, ItemIds... subItems) {
		this(npcs, new String[] {action}, minimumLevel, null, primaryItem, subItems);
	}
	
	private FishingEquipment(NPCNames[] npcs, String[] action, int minimumLevel, ItemIds primaryItem, ItemIds... subItems) {
		this(npcs, action, minimumLevel, null, primaryItem, subItems);
	}
	
	private FishingEquipment(NPCNames npc, String action, int minimumLevel, String displayName, ItemIds primaryItem, ItemIds... subItems) {
		this(new NPCNames[] {npc}, new String[] {action}, minimumLevel, displayName, primaryItem, subItems);
	}
	
	private FishingEquipment(NPCNames npc, String[] action, int minimumLevel, String displayName, ItemIds primaryItem, ItemIds... subItems) {
		this(new NPCNames[] {npc}, action, minimumLevel, displayName, primaryItem, subItems);
	}
	
	private FishingEquipment(NPCNames npc, String action, int minimumLevel, ItemIds primaryItem, ItemIds... subItems) {
		this(npc, action, minimumLevel, null, primaryItem, subItems);
	}
	
	private FishingEquipment(NPCNames npc, String[] actions, int minimumLevel, ItemIds primaryItem, ItemIds... subItems) {
		this(npc, actions, minimumLevel, null, primaryItem, subItems);
	}
	
	public String getName() {
		return displayName==null?NameFormatter.get(this.toString()):this.displayName;
	}

	public NPCNames[] getNPCNames() {
		return NPCTypes;
	}
	
	public int getMinimumLevelRequired() {
		return this.minimumLevel;
	}
	
	public String[] getActions() {
		return this.actions;
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
