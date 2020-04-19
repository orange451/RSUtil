package scripts.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import org.tribot.api2007.types.RSItem;

import scripts.dax_api.shared.jsonSimple.JSONObject;
import scripts.dax_api.shared.jsonSimple.parser.JSONParser;
import scripts.util.ge.GEItem;
import scripts.util.ge.GrandExchangeUtil;
import scripts.util.names.ItemNamesData;

public class ItemUtil {

	/**
	 * Returns whether an RSItem is a consumable food item.
	 * @param item
	 * @return
	 */
	public static boolean isFood(RSItem item) {
		return ItemNamesData.is(item, getFood());
	}

	/**
	 * Returns a list of ItemNamesData that are considered food.
	 * @return
	 */
	public static ItemNamesData[] getFood() {
		// These are food IDS
		int[] foodIds = { 361, 365, 373, 379, 385, 391, 397, 355, 351, 347, 339, 337, 333, 329, 325, 319, 315, 2309, 2142 };

		// Get all ItemNamesData objects with the food ids
		ArrayList<ItemNamesData> itemsList = new ArrayList<ItemNamesData>();
		ItemNamesData[] items = ItemNamesData.values();
		for (int i = 0; i < items.length; i++) {
			ItemNamesData itm = items[i];
			if (itm.hasId(foodIds)) {
				itemsList.add(itm);
			}
		}
		
		// Return
		return itemsList.toArray(new ItemNamesData[itemsList.size()]);
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
		
		return item.getSellAverage();
	}
}