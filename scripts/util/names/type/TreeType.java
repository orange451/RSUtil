package scripts.util.names.type;

import scripts.util.misc.NameFormatter;
import scripts.util.misc.ObjectWrapper;
import scripts.util.names.ObjectNames;

public enum TreeType implements ObjectWrapper {
	TREE(ObjectNames.TREE, 1, 10),
	OAK(ObjectNames.OAK, 15, 14),
	WILLOW(ObjectNames.WILLOW, 30, 22),
	YEW(ObjectNames.YEW, 60, 64);
	
	private ObjectNames object;
	private int minLevelRequired;
	private int maxTravelDistance;
	
	TreeType(ObjectNames object, int minLevelRequired, int maxTravelDistance) {
		this.object = object;
		this.minLevelRequired = minLevelRequired;
		this.maxTravelDistance = maxTravelDistance;
	}
	
	public ObjectNames getObjectName() {
		return this.object;
	}
	
	public int getMinimumLevelRequired() {
		return minLevelRequired;
	}
	
	public int getMaximumTravelDistance() {
		return maxTravelDistance;
	}
	
	public String getName() {
		return NameFormatter.get(this.toString());
	}

	public static TreeType match(ObjectNames object) {
		for (TreeType tree:values()) {
			if ( tree.getObjectName().equals(object) )
				return tree;
		}
		
		return null;
	}
}
