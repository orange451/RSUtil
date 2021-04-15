package scripts.util;

import org.tribot.api2007.types.RSItem;

import scripts.util.names.ItemIds;
import scripts.util.names.internal.ItemNamesBase;

public class NotedItem extends ItemIds {
	private int[] ids;
	
	public NotedItem(ItemIds item) {
		this.ids = item.getIds();
	}
	
	public NotedItem(RSItem item) {
		this.ids = new int[] {item.getID()};
	}
	
	public boolean is(ItemNamesBase item) {
		for (int id2 : item.getIds())
			for (int id1 : ids )
				if ( id2 == id1 )
					return true;
		return false;
	}
	
	public int[] getIds() {
		int[] nid = new int[ids.length];
		for (int i = 0; i < nid.length; i++) {
			nid[i] = ids[i]+1;
		}
		
		return nid;
	}
	
	public boolean isValid() {
		return ItemNamesBase.get(getIds()[0]) == null;
	}
}
