package scripts.util.names;

import scripts.util.misc.NameFormatter;

public enum RockType {
	COPPER(ObjectNames.ORE_COPPER, 1),
	TIN(ObjectNames.ORE_TIN, 1),
	COAL(ObjectNames.ORE_COAL, 2),
	GOLD(ObjectNames.ORE_GOLD, 16),
	IRON(ObjectNames.ORE_IRON, 3);
	
	private ObjectNames objectType;
	private int minTravelDistance;
	
	private RockType(ObjectNames objectType, int minTravelDistance) {
		this.objectType = objectType;
		this.minTravelDistance = minTravelDistance;
	}
	
	public ObjectNames getObjectName() {
		return this.objectType;
	}
	
	public int getMinimumTravelDistance() {
		return this.minTravelDistance;
	}
	
	public String getName() {
		return NameFormatter.get(this.toString());
	}

	public static RockType get(ObjectNames rock) {
		for (RockType rockType : values()) {
			if ( rockType.getObjectName().equals(rock) ) {
				return rockType;
			}
		}
		return null;
	}
}