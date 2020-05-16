package scripts.util.names.type;

import scripts.util.misc.NameFormatter;
import scripts.util.misc.ObjectWrapper;
import scripts.util.names.ObjectNames;

public enum RockType implements ObjectWrapper {
	COPPER(ObjectNames.ORE_COPPER, 2, 1),
	TIN(ObjectNames.ORE_TIN, 2, 1),
	COAL(ObjectNames.ORE_COAL, 2, 30),
	GOLD(ObjectNames.ORE_GOLD, 16, 40),
	IRON(ObjectNames.ORE_IRON, 5, 15),
	MITHRIL(ObjectNames.ORE_MITHRIL, 16, 55),
	ADAMANT(ObjectNames.ORE_ADAMANT, 16, 70), 
	RUNE(ObjectNames.ORE_ADAMANT, 160, 85), 
	CLAY(ObjectNames.ORE_CLAY, 1, 4), 
	PAY_DIRT(ObjectNames.ORE_PAY_DIRT, Integer.MAX_VALUE, 4);
	
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
		return NameFormatter.formatName(this.toString());
	}

	public static RockType get(ObjectNames rock) {
		for (RockType rockType : values()) {
			if ( rockType.getObjectName().equals(rock) ) {
				return rockType;
			}
		}
		return null;
	}

	public OreType getOre() {
		return OreType.valueOf(this.toString());
	}
}