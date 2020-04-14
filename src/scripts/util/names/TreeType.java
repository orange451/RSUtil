package scripts.util.names;

import scripts.util.misc.NameFormatter;

public enum TreeType {
	TREE(ObjectNames.TREE),
	OAK(ObjectNames.OAK),
	WILLOW(ObjectNames.WILLOW);
	
	private ObjectNames object;
	
	TreeType(ObjectNames object) {
		this.object = object;
	}
	
	public ObjectNames getObjectName() {
		return this.object;
	}
	
	public String getName() {
		return NameFormatter.get(this.toString());
	}
}
