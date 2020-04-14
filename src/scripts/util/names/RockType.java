package scripts.util.names;

import scripts.util.misc.NameFormatter;

public enum RockType {
	COPPER(ObjectNames.ORE_COPPER),
	TIN(ObjectNames.ORE_TIN),
	COAL(ObjectNames.ORE_COAL),
	GOLD(ObjectNames.ORE_GOLD),
	IRON(ObjectNames.ORE_IRON);
	
	private ObjectNames objectType;
	
	private RockType(ObjectNames objectType) {
		this.objectType = objectType;
	}
	
	public ObjectNames getObjectName() {
		return this.objectType;
	}
	
	public String getName() {
		return NameFormatter.get(this.toString());
	}
}