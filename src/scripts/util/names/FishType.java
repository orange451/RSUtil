package scripts.util.names;

import scripts.util.misc.NameFormatter;

public enum FishType {
	SHRIMPS(NPCNames.FISHING_SPOT, 10),
	ANCHOVIES(NPCNames.FISHING_SPOT, 10);
	
	private NPCNames NPCType;
	private int minTravelDistance;
	
	private FishType(NPCNames NPCType, int minTravelDistance) {
		this.NPCType = NPCType;
		this.minTravelDistance = minTravelDistance;
	}
	
	public NPCNames getNPCName() {
		return this.NPCType;
	}
	
	public int getMinimumTravelDistance() {
		return this.minTravelDistance;
	}
	
	public String getName() {
		return NameFormatter.get(this.toString());
	}
	
	public static FishType get(NPCNames fish) {
		for (FishType fishType : values()) {
			if (fishType.getNPCName().equals(fish)) {
				return fishType;
			}
		}
		return null;
	}
}
