package scripts.util;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSItem;

import scripts.util.misc.AntiBan;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;
import scripts.util.names.internal.ItemNamesData;
import scripts.util.names.type.FoodType;

public class BankingUtil {
	
	/**
	 * Returns the amount of an item you have in your bank.
	 * @param item
	 * @return
	 */
	public static int getAmountOfItems(ItemIds item) {
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
	public static boolean withdrawFood(int amount, FoodType... foodType) {
		if (!Banking.isBankScreenOpen())
			return false;
		
		ItemIds[] names = ItemNamesData.get(foodType);
		if ( names.length == 0 )
			names = ItemNamesData.get(FoodType.values());
		
		for (int i = 0; i < names.length; i++) {
			if ( amount <= 0 )
				return true;
			
			RSItem[] t = Banking.find(names[i].getIds());
			if ( t != null && t.length > 0 ) {
				int amt = PlayerUtil.getAmountItemsInInventory(names[i]);
				
				if (Banking.withdraw(amount, names[i].getIds()))
					return true;
				
				amount -= (PlayerUtil.getAmountItemsInInventory(names[i])-amt);
				if ( amount <= 0 )
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Withdraws the first item in your bank from a list of desired items
	 * @param desiredItems
	 */
	public static boolean withdrawFirstItem(ItemIds... desiredItems) {
		return withdrawFirstItem(false, 1, desiredItems);
	}
	
	/**
	 * Withdraws the first item in your bank from a list of desired items
	 * @param desiredItems
	 */
	public static boolean withdrawFirstItem(boolean noted, int quantity, ItemIds... desiredItems) {
		if (!Banking.isBankScreenOpen())
			return false;
		
		// If only taking out money, dont note
		if ( desiredItems != null && desiredItems.length >= 1 && desiredItems[0].equals(ItemNames.COINS) )
			noted = false;
		
		BankingUtil.setNote(noted);
		
		for (ItemIds item : desiredItems) {
			RSItem[] t = Banking.find(item.getIds());
			if ( t == null || t.length == 0 )
				continue;
			
			int requiredSpace = 1;
			NotedItem note = new NotedItem(item);
			if ( !note.isValid() && !t[0].getDefinition().isStackable() )
				requiredSpace = t[0].getStack();
			
			int freeSpace = 28 - Inventory.getAll().length;
			
			if ( freeSpace < requiredSpace ) {
				General.println("Not enough space");
				return false;
			} else {
				//if ( quantity > freeSpace ) {
					//BankingUtil.setNote(!item.equals(ItemNames.COINS));
				//} else {
					BankingUtil.setNote(noted);
				//}
			}
			
			if ( Banking.withdraw(quantity, t[0].getID()) ) {
				AntiBan.sleep(3000, 1000);
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
		boolean isNoted = Game.getSetting(115) == 1;
		if ( isNoted == note )
			return true;
		
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
