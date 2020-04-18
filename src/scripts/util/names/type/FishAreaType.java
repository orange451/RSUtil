package scripts.util.names.type;

import scripts.util.misc.NameFormatter;

public enum FishAreaType {
	SHRIMPS(FishingEquipment.SMALL_FISHING_NET, 16, 1),
	ANCHOVIES(FishingEquipment.SMALL_FISHING_NET, 16, 15),
	SARDINES(FishingEquipment.FISHING_ROD, 16, 5),
	HERRING(FishingEquipment.FISHING_ROD, 16, 10);
	
	private int minTravelDistance;
	private int minLevelRequired;
	private FishingEquipment itemRequired;
	
	private FishAreaType(FishingEquipment itemRequired, int minTravelDistance, int minLevelRequired) {
		this.itemRequired= itemRequired;
		this.minTravelDistance = minTravelDistance;
		this.minLevelRequired = minLevelRequired;
	}

	public FishingEquipment getEquipmentRequired() {
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
}
