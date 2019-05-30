package scripts.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import org.tribot.api2007.types.RSItem;
import scripts.util.names.ItemNames;

public class ItemUtil {
	private static HashMap<String, Integer> prices = new HashMap<String, Integer>();

	/**
	 * Returns whether an RSItem is a consumable food item.
	 * @param item
	 * @return
	 */
	public static boolean isFood(RSItem item) {
		return ItemNames.is(item, getFood());
	}

	/**
	 * Returns a list of ItemNames that are considered food.
	 * @return
	 */
	public static ItemNames[] getFood() {
		int[] foodIds = {
				361, 365, 373, 379, 385, 391, 397, 355, 351, 347, 339, 337, 333, 329, 325, 319, 315, 2309, 2142 };

		ItemNames[] items = ItemNames.values();
		ArrayList<ItemNames> itemsList = new ArrayList<ItemNames>();
		for (int i = 0; i < items.length; i++) {
			ItemNames itm = items[i];
			if (itm.hasId(foodIds)) {
				itemsList.add(itm);
			}
		}
		items = new ItemNames[itemsList.size()];
		for (int i = 0; i < itemsList.size(); i++) {
			items[i] = ((ItemNames)itemsList.get(i));
		}
		return items;
	}

	/**
	 * Returns the GE price of an item. Requires a socket connection.
	 * @param id
	 * @return
	 */
	public static int getPrice(int id) {
		String key = ""+id;

		if (prices.containsKey(key)) {
			return ((Integer)prices.get(key)).intValue();
		}
		String lookup = "http://api.rsbuddy.com/grandExchange?a=guidePrice&i=" + id;
		try {
			URL url = new URL(lookup);
			URLConnection con = url.openConnection();
			con.setUseCaches(true);
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String[] data = br.readLine().replace("{", "").replace("}", "").split(",");
			int value = Integer.parseInt(data[0].split(":")[1]);

			prices.put(key, new Integer(value));
			return value;
		} catch (Exception e) {}
		return 0;
	}
}
