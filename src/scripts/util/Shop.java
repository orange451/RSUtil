package scripts.util;

import java.util.ArrayList;
import java.util.Arrays;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSInterfaceComponent;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;

public class Shop {
	/**
	 * Returns whether the shop interface is open.
	 * @return
	 */
	public static boolean isShopOpen() {
		return Interfaces.get(300) != null;
	}

	/**
	 * Returns the name of the shop.
	 * @return
	 */
	public static String getShopName() {
		if (isShopOpen()) {
			return Interfaces.get(300, 1).getChild(1).getText();
		}

		return "";
	}

	/**
	 * Closes the shop interface.
	 * @return
	 */
	public static boolean close() {
		if (isShopOpen()) {
			return Interfaces.get(300, 1).getChild(11).click(new String[0]);
		}


		return false;
	}

	public static RSItem[] getAll() {
		RSInterfaceComponent[] items1 = Interfaces.get(300).getChild(2).getChildren();

		RSItem[] items = new RSItem[items1.length];
		for (int i = 0; i < items1.length; i++) {
			RSInterfaceComponent item = items1[i];
			items[i] = new RSItem(item.getComponentName(), item.getActions(), item.getComponentIndex(), item.getComponentItem(), item.getComponentStack(), RSItem.TYPE.OTHER);
			items[i].setArea(item.getAbsoluteBounds());
		}

		if (isShopOpen()) {
			return createRectangles(items);
		}

		return new RSItem[0];
	}

	public static RSItem[] get(String... name) {
		ArrayList<RSItem> items = new ArrayList();
		RSItem[] arrayOfRSItem;
		int j = (arrayOfRSItem = getAll()).length;

		for (int i = 0; i < j; i++) {
			RSItem it = arrayOfRSItem[i];

			RSItemDefinition definition = it.getDefinition();

			if (definition != null) {
				if (Arrays.asList(name).contains(definition.getName())) {
					items.add(it);
				}
			}
		}

		return (RSItem[])items.toArray(new RSItem[items.size()]);
	}

	public static RSItem[] get(int... ids) {
		ArrayList<RSItem> items = new ArrayList();

		RSItem[] arrayOfRSItem;
		int j = (arrayOfRSItem = getAll()).length; for (int i = 0; i < j; i++) {
			RSItem it = arrayOfRSItem[i];

			for (int a = 0; a < ids.length; a++) {
				int idCheck = ids[a];
				if (it.getID() == idCheck) {
					items.add(it);
					break;
				}
			}
		}

		return (RSItem[])items.toArray(new RSItem[items.size()]);
	}

	public static int getCount(String... name) {
		int c = 0;
		RSItem[] arrayOfRSItem;
		int j = (arrayOfRSItem = get(name)).length;
		
		for (int i = 0; i < j; i++) {
			RSItem item = arrayOfRSItem[i];
			c += item.getStack();
		}


		return c;
	}

	public static int getCount(int... id) {
		int c = 0;
		RSItem[] arrayOfRSItem;
		int j = (arrayOfRSItem = get(id)).length;
		
		for (int i = 0; i < j; i++) {
			RSItem item = arrayOfRSItem[i];
			c += item.getStack();
		}


		return c;
	}

	private static RSItem[] createRectangles(RSItem[] items) {
		RSItem[] arrayOfRSItem = items;int j = items.length;
		for (int i = 0; i < j; i++) {
			RSItem it = arrayOfRSItem[i];
			int x = (int)Math.ceil(it.getIndex() % 8) * 47 + 80;
			int k = (int)(Math.floor(it.getIndex()) / 8.0D % 5.0D) * 47 + 69;
		}

		return items;
	}

	public static boolean buy(int count, String... names) {
		RSItem[] arrayOfRSItem;

		int j = (arrayOfRSItem = get(names)).length;
		for (int i = 0; i < j; i++) { RSItem item = arrayOfRSItem[i];
			if (item.getStack() > 0) {
				if (count >= 10) {
					return item.click(new String[] { "Buy 10" });
				}
				if (count >= 5) {
					return item.click(new String[] { "Buy 5" });
				}
	
				return item.click(new String[] { "Buy 1" });
			}
		}
		
		return false;
	}

	public static boolean buy(int count, int... ids) {
		RSItem[] arrayOfRSItem;

		int j = (arrayOfRSItem = get(ids)).length;
		for (int i = 0; i < j; i++) { RSItem item = arrayOfRSItem[i];
			if (item.getStack() > 0) {
				if (count >= 10) {
					return item.click(new String[] { "Buy 10" });
				}
				if (count >= 5) {
					return item.click(new String[] { "Buy 5" });
				}
	
	
				return item.click(new String[] { "Buy 1" });
			}
		}

		return false;
	}

	public static boolean sell(int count, String... names) {
		RSItem[] items = Inventory.find(names);

		if (items.length > 0) {
			if (count >= 10) {
				return items[0].click(new String[] { "Buy 10" });
			}
			if (count >= 5) {
				return items[0].click(new String[] { "Buy 5" });
			}

			return items[0].click(new String[] { "Buy 1" });
		}

		return false;
	}

	public static boolean sell(int count, int... ids) {
		RSItem[] items = Inventory.find(ids);

		if (items.length > 0) {
			if (count >= 10) {
				return items[0].click(new String[] { "Buy 10" });
			}
			if (count >= 5) {
				return items[0].click(new String[] { "Buy 5" });
			}

			return items[0].click(new String[] { "Buy 1" });
		}

		return false;
	}
}
