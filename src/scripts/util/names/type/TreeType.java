package scripts.util.names.type;

import scripts.util.misc.NameFormatter;
import scripts.util.misc.ObjectWrapper;
import scripts.util.names.ObjectNames;

public enum TreeType implements ObjectWrapper {
	TREE(ObjectNames.TREE, 1),
	OAK(ObjectNames.OAK, 15),
	WILLOW(ObjectNames.WILLOW, 30),
	YEW(ObjectNames.YEW, 30);
	
	private ObjectNames object;
	private int minLevelRequired;
	
	TreeType(ObjectNames object, int minLevelRequired) {
		this.object = object;
		this.minLevelRequired = minLevelRequired;
	}
	
	public ObjectNames getObjectName() {
		return this.object;
	}
	
	public int getMinimumLevelRequired() {
		return minLevelRequired;
	}
	
	public String getName() {
		return NameFormatter.get(this.toString());
	}
}
