package scripts.util.player;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;

public class InventoryHelper {
	/**
	 * Returns whether or not the players inventory is empty
	 * @return
	 */
	public static boolean isInventoryEmpty() {
		RSItem[] items = Inventory.getAll();
		return (items != null ? items.length : 0) == 0;
	}
}
