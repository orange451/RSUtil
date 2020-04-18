package scripts.util.names.type;

import scripts.util.misc.NPCWrapper;
import scripts.util.misc.NameFormatter;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;
import scripts.util.names.NPCNames;

public enum FishAreaType implements NPCWrapper {
	SHRIMPS(NPCNames.FISHING_SPOT, ItemNames.SMALL_FISHING_NET, "Net", 16, 1),
	ANCHOVIES(NPCNames.FISHING_SPOT, ItemNames.SMALL_FISHING_NET, "Net", 16, 15),
	SARDINES(NPCNames.FISHING_SPOT, ItemNames.FISHING_ROD, "Bait", 16, 5),
	HERRING(NPCNames.FISHING_SPOT, ItemNames.FISHING_ROD, "Bait", 16, 10);
	
	private NPCNames NPCType;
	private int minTravelDistance;
	private int minLevelRequired;
	private ItemIds itemRequired;
	private String actionName;
	
	private FishAreaType(NPCNames NPCType, ItemIds itemRequired, String actionName, int minTravelDistance, int minLevelRequired) {
		this.NPCType = NPCType;
		this.itemRequired= itemRequired;
		this.minTravelDistance = minTravelDistance;
		this.minLevelRequired = minLevelRequired;
		this.actionName = actionName;
	}

	@Override
	public NPCNames getNPCName() {
		return this.NPCType;
	}

	public ItemIds getItemRequired() {
		return this.itemRequired;
	}
	
	public int getMinimumLevelRequired() {
		return this.minLevelRequired;
	}
	
	public int getMinimumTravelDistance() {
		return this.minTravelDistance;
	}
	
	public String getName() {
		return NameFormatter.get(this.toString());
	}
	
	public static FishAreaType get(NPCNames fish) {
		for (FishAreaType fishType : values()) {
			if (fishType.getNPCName().equals(fish)) {
				return fishType;
			}
		}
		return null;
	}

	public String getActionName() {
		return this.actionName;
	}
}
