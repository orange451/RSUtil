package scripts.util;

import org.tribot.api2007.types.RSItem;

import scripts.util.ge.GEItem;
import scripts.util.ge.GrandExchangeUtil;
import scripts.util.names.ItemIds;
import scripts.util.names.internal.ItemNamesBase;
import scripts.util.names.type.FoodType;

public class ItemUtil {

	/**
	 * Returns whether an RSItem is a consumable food item.
	 * @param item
	 * @return
	 */
	public static boolean isFood(RSItem item) {
		return ItemNamesBase.is(item, ItemNamesBase.get(FoodType.values()));
	}
	
	/**
	 * Returns if this RSItem contains the specified action when right-clicked.
	 * @param item
	 * @param action
	 * @return
	 */
	public static boolean hasAction(RSItem item, String action) {
		String[] actions = item.getDefinition().getActions();
		for (int i = 0; i < actions.length; i++) {
			String s = actions[i];
			if ( s.toLowerCase().contains(action.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the GE price of an item. Requires a socket connection.
	 * @param id
	 * @return
	 */
	public static int getPrice(int id) {
		GEItem item = GrandExchangeUtil.getItemData(id);
		if ( item == null )
			return -1;
		
		return item.getOverallAverage();
	}
	
	/**
	 * Returns whether an item is stackable
	 * @param id
	 * @return
	 */
	public static boolean isStackable(ItemIds item) {
		return isStackable(item.getIds()[0]);
	}
	
	/**
	 * Returns whether an item is stackable
	 * @param id
	 * @return
	 */
	public static boolean isStackable(int id) {
		return new RSItem(0, id, 1, RSItem.TYPE.OTHER).getDefinition().isStackable();
	}
	
	/**
	 * Returns generic RSItem from id.
	 * @param id
	 * @return
	 */
	public static RSItem fromId(int id) {
		return new RSItem(0, id, 1, RSItem.TYPE.OTHER);
	}
}
