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
import scripts.util.names.ItemNamesData;

public class ItemUtil {
	private static HashMap<String, Integer> prices = new HashMap<String, Integer>();
	private static JSONObject priceData;

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
		String key = new Integer(id).toString();

		if (prices.containsKey(key)) {
			return prices.get(key).intValue();
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

	/**
	 * Returns the GE price of an item. Requires a socket connection.
	 * @param id
	 * @return
	 */
	public static int getPriceAlt(int id) {
		try {
			if ( priceData == null ) {
				String address = "https://rsbuddy.com/exchange/summary.json";
				URL url = new URL(address);
				URLConnection connection = url.openConnection();
				InputStream inputStream = connection.getInputStream();
				InputStreamReader reader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(reader);
				String jsonSource = bufferedReader.readLine();
				
				JSONParser parser = new JSONParser();
				priceData = (JSONObject) parser.parse(jsonSource);
			}
			
			JSONObject itemData = (JSONObject) priceData.get(""+id);
			long sellPrice = (long) itemData.get("sell_average");
			return (int) sellPrice;
		} catch(Exception e ) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static void main(String[] args) {
		System.out.println(getPriceAlt(532));
	}
}
