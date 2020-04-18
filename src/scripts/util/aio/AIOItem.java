package scripts.util.aio;

import static scripts.util.names.ItemNamesData.get;

import java.util.Arrays;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;

import scripts.util.BankingUtil;
import scripts.util.PlayerUtil;
import scripts.util.names.ItemIds;

public class AIOItem {
	
	/**
	 * Attempts to find a specific item in the players inventory.
	 * If the item is not found, the player will walk to the bank to withdraw.
	 * @param desiredItems
	 * @return
	 */
	public static RSItem getItem(ItemIds desiredItems) {
		int[] desiredItemIds = get(desiredItems);
		
		// Check if it is in inventory, and equip if not.
		General.println("Checking inventory for: " + desiredItems);
		int count = Inventory.getCount(desiredItemIds);
		if ( count > 0 ) {
			RSItem item = PlayerUtil.getFirstItemInInventory(desiredItems);
			Inventory.open();
			return item;
		}
		
		General.println("Checking bank for: " + desiredItems + " (" + Arrays.toString(desiredItemIds) + ")");
		
		// Go to bank
		if ( !AIOBank.walkToNearestBankAndOpen() )
			return null;
		
		// Withdraw
		if ( BankingUtil.withdrawFirstItem(desiredItems) )
			return getItem(desiredItems);
		
		General.println("Could not find items at bank! " + "(" + Arrays.toString(desiredItemIds) + ")");
		
		// Not in bank, lets go to GE and buy it!
		// TODO implement GE stuff
		return null;
	}
}
