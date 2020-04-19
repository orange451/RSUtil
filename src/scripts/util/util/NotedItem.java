package scripts.util;

import scripts.util.names.ItemIds;
import scripts.util.names.ItemNamesData;

public class NotedItem extends ItemIds {
	private int[] ids;
	
	public NotedItem(ItemNamesData item) {
		this.ids = item.getIds();
	}
	
	public boolean is(ItemNamesData item) {
		return item.getIds() == this.ids;
	}
	
	public int[] getIds() {
		int[] nid = new int[ids.length];
		for (int i = 0; i < nid.length; i++) {
			nid[i] = ids[i]+1;
		}
		
		return nid;
	}
}
