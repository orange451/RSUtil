package scripts.util.names.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSItem;

import scripts.util.ItemUtil;
import scripts.util.misc.ItemWrapper;
import scripts.util.misc.NameFormatter;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;

public class ItemData extends ItemIds {
	protected int[] id;

	private static ArrayList<ItemData> items;

	protected ItemData(int... id) {
		this.id = id;
		
		if ( ItemData.items == null ) {
			ItemData.items = new ArrayList<ItemData>();
		}
		ItemData.items.add(this);
	}

	@Override
	public int[] getIds() {
		return this.id;
	}
	
	
	/**
	 * Returns the properly formatted name for this Item.
	 * @return
	 */
	public String getName() {
		return NameFormatter.formatItemName(super.getName());
	}

	/**
	 * Returns the price of this item. See {@link ItemUtil#getPrice(int)}.
	 * @return
	 */
	public int getPrice() {
		return ItemUtil.getPrice(this.id[0]);
	}

	/**
	 * Returns if an RSItem matches a set of ItemNames.
	 * @param check
	 * @param types
	 * @return
	 */
	public static boolean is(RSItem check, ItemIds... types) {
		return is(check.getID(), types);
	}

	/**
	 * Returns if an RSItem matches a set of ItemNames.
	 * @param check
	 * @param types
	 * @return
	 */
	public static boolean is(int itemId, ItemIds... types) {
		for (int i = 0; i < types.length; i++) {
			ItemIds itemData = types[i];
			int[] ids = itemData.getIds();
			for (int j = 0; j < ids.length; j++) {
				if (ids[j] == itemId) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns if an RSGrountItem matches a set of ItemNames.
	 * @param check
	 * @param types
	 * @return
	 */
	public static boolean is(RSGroundItem check, ItemIds... types) {
		return is(check.getID(), types);
	}

	/**
	 * Returns if this ItemNames enum contains a specific item id.
	 * @param array
	 * @return
	 */
	public boolean hasId(int... ids) {
		int[] t = getIds();
		for (int i = 0; i < t.length; i++) {
			int mid = t[i];
			
			for (int a = 0; a < ids.length; a++) {
				int fid = ids[a];
				
				// It has the id!
				if (  mid == fid ) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Get an ItemName data from an item ID.
	 * @param id
	 * @return
	 */
	public static ItemData get(int id) {
		for (int i = 0; i < items.size(); i++) {
			ItemData data = items.get(i);
			if ( data.hasId(id) )
				return data;
		}
		return null;
	}

	/**
	 * Combines the IDs of a list of Itemnames
	 * @param itemNames
	 * @return
	 */
	public static int[] get(ItemIds... itemNames) {
		int len = 0;
		for (int i = 0 ;i < itemNames.length; i++) {
			len += itemNames[i].getIds().length;
		}
		
		int a = 0;
		int[] ret = new int[len];
		for (int i = 0; i < itemNames.length; i++) {
			int[] t = itemNames[i].getIds();
			for (int j = 0; j < t.length; j++) {
				ret[a++] = t[j];
			}
		}
		
		return ret;
	}
	
	public static ItemIds[] get(ItemWrapper... wrapper) {
		if ( wrapper == null )
			return new ItemIds[0];
		
		ItemIds[] ret = new ItemIds[wrapper.length];
		for (int i = 0; i < wrapper.length; i++) {
			if ( wrapper[i] == null )
				ret[i] = ItemNames.NONE;
			else
				ret[i] = wrapper[i].getItem();
		}
		
		return ret;
	}
	
	public static ItemIds[] get(Object... objects) {
		List<ItemIds> list = new ArrayList<>();
		for (Object object : objects) {
			if ( object == null )
				continue;
			
			if ( object.getClass().isArray() ) {
				Collections.addAll(list, get((Object[])object));
			} else if ( object instanceof List ) {
				Collections.addAll(list, get((List<?>)object));
			} else if ( object instanceof ItemWrapper ) {
				list.add(((ItemWrapper)object).getItem());
			} else if ( object instanceof RSItem ) {
				list.add(ItemNames.get(((RSItem)object).getID()));
			} else if ( object instanceof ItemIds ) {
				list.add((ItemIds) object);
			}
		}
		return (ItemIds[]) list.toArray(new ItemIds[list.size()]);
	}

	public static ItemData[] values() {
		return items.toArray(new ItemData[items.size()]);
	}
}
