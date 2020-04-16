package scripts.util.names.type;

import scripts.util.misc.NameFormatter;
import scripts.util.misc.ObjectWrapper;
import scripts.util.names.ObjectNames;

public enum TreeType implements ObjectWrapper {
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
