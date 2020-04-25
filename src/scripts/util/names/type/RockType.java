package scripts.util.names.type;

import scripts.util.misc.NameFormatter;
import scripts.util.misc.ObjectWrapper;
import scripts.util.names.ObjectNames;

public enum RockType implements ObjectWrapper {
	COPPER(ObjectNames.ORE_COPPER, 1, 1),
	TIN(ObjectNames.ORE_TIN, 1, 1),
	COAL(ObjectNames.ORE_COAL, 2, 30),
	GOLD(ObjectNames.ORE_GOLD, 16, 40),
	IRON(ObjectNames.ORE_IRON, 5, 15),
	MITHRIL(ObjectNames.ORE_MITHRIL, 16, 40),
	ADAMANT(ObjectNames.ORE_ADAMANT, 16, 40), 
	CLAY(ObjectNames.ORE_CLAY, 1, 4);
	
	private ObjectNames objectType;
	private int minTravelDistance;
	private int minLevelRequired;
	
	private RockType(ObjectNames objectType, int minTravelDistance, int minLevelRequired) {
		this.objectType = objectType;
		this.minTravelDistance = minTravelDistance;
		this.minLevelRequired = minLevelRequired;
	}
	
	public ObjectNames getObjectName() {
		return this.objectType;
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

	public static RockType get(ObjectNames rock) {
		for (RockType rockType : values()) {
			if ( rockType.getObjectName().equals(rock) ) {
				return rockType;
			}
		}
		return null;
	}
}