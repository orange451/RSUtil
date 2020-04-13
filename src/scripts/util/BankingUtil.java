package scripts.util;

import org.tribot.api2007.Banking;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSItem;

import scripts.util.misc.AntiBan;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNamesData;

public class BankingUtil {
	
	/**
	 * Returns the amount of an item you have in your bank.
	 * @param item
	 * @return
	 */
	public static int getAmountOfItems(ItemNamesData item) {
		RSItem[] is = Banking.find(item.getIds());
		if (is == null) {
			return 0;
		}
		int amt = 0;
		for (int i = 0; i < is.length; i++) {
			amt += is[i].getStack();
		}

		return amt;
	}

	/**
	 * Withdraws the first food item(s) in your bank until you have "amount" food items.
	 * @param amount
	 * @return
	 */
	public static boolean withdrawFood(int amount) {
		if (!Banking.isBankScreenOpen()) {
			return false;
		}
		ItemNamesData[] names = ItemUtil.getFood();
		for (int i = 0; i < names.length; i++) {
			RSItem[] t = Banking.find(names[i].getIds());
			if ( t != null && t.length > 0 ) {
				if (Banking.withdraw(amount, names[i].getIds())) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Withdraws the first item in your bank from a list of desired items
	 * @param desiredItems
	 */
	public static boolean withdrawFirstItem(ItemIds[] desiredItems) {
		if (!Banking.isBankScreenOpen())
			return false;
		
		for (ItemIds item : desiredItems) {
			RSItem[] t = Banking.find(item.getIds());
			if ( t == null || t.length == 0 )
				continue;
			
			if ( Banking.withdraw(1, item.getIds()) ) {
					AntiBan.sleep(800, 250);
					return true;
			}
		}
		
		return false;
	}

	/**
	 * Sets the note flag in the bank UI
	 * @param b
	 */
	public static boolean setNote(boolean note) {
		RSInterfaceChild u = Interfaces.get(12, note?24:22);
		if ( u != null ) {
			return u.click("");
		}
		return false;
	}

	/**
	 * Force deposit all items and close the bank ONLY IF the inventory is NON EMPTY. Will open bank too if not already in bank.
	 */
	public static void depositAllAndClose() {
		if ( PlayerUtil.isInventoryEmpty() )
			return;
		
		while ( !Banking.isBankScreenOpen() ) {
			Banking.openBank();
			AntiBan.sleep(500, 250);
		}
		
		while ( Banking.isBankScreenOpen() ) {
			Banking.depositAll();
			AntiBan.sleep(500, 250);
			Banking.close();
			AntiBan.sleep(500, 250);
		}
	}
}
