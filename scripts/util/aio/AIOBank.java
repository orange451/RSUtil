package scripts.util.aio;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;

import scripts.util.BankingUtil;
import scripts.util.PlayerUtil;
import scripts.util.misc.AntiBan;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;
import scripts.util.names.Locations;

public class AIOBank {
	/**
	 * Walks to nearest bank. Uses {@link AIOWalk#walkToNearestBank()}
	 * @return
	 */
	public static boolean walkToNearestBank() {
		return AIOWalk.walkToNearestBank();
	}
	
	/**
	 * Walks to nearest bank. Uses {@link AIOWalk#walkToNearestBank()}
	 * @return
	 */
	public static boolean walkToNearestBank(AIOStatus status) {
		return AIOWalk.walkToNearestBank(status);
	}
	
	/**
	 * Walks to nearest bank and opens. Uses {@link AIOWalk#walkToNearestBank()}
	 * @return
	 */
	public static boolean walkToNearestBankAndOpen() {
		return walkToNearestBankAndOpen(new AIOStatus());
	}
	
	/**
	 * Walks to nearest bank and opens. Uses {@link AIOWalk#walkToNearestBank()}
	 * @return
	 */
	public static boolean walkToNearestBankAndOpen(AIOStatus status) {
		// Walk to nearest bank
		if ( !walkToNearestBank(status) ) {
			status.setType(StatusType.FAILED);
			return false;
		}
		
		// Open Bank
		status.setStatus("Opening bank");
		while ( !Banking.isBankScreenOpen() ) {
			Banking.openBank();
			AntiBan.sleep(1000, 500);
		}
		AntiBan.sleep(2000, 1000);
		
		return true;
	}
	
	/**
	 * Walks the player to the nearest bank. Opens the bank. Deposits all.
	 */
	public static boolean walkToNearestBankAndDepositAll() {
		return walkToNearestBankAndDepositAll(new AIOStatus());
	}
	
	/**
	 * Walks the player to the nearest bank. Opens the bank. Deposits all.
	 */
	public static boolean walkToNearestBankAndDepositAll(AIOStatus status) {
		return walkToNearestBankAndDepositAllExcept(status, new ItemNames[] {});
	}
	
	/**
	 * Walks the player to the nearest bank. Opens the bank. Deposits everything except the exclusion list.
	 */
	public static boolean walkToNearestBankAndDepositAllExcept(ItemIds... exclude) {
		return walkToNearestBankAndDepositAllExcept( new AIOStatus(), exclude );
	}
	
	/**
	 * Walks the player to the nearest bank. Opens the bank. Deposits everything except the exclusion list.
	 */
	public static boolean walkToNearestBankAndDepositAllExcept(AIOStatus status, ItemIds... exclude) {
		if ( PlayerUtil.isInDanger() )
			walkToNearestBank();
		
		// Return since inventory is empty
		int excludedItemsInInventory = PlayerUtil.getAmountItemsInInventory(false, exclude);
		int itemsInInventoryToBank = Inventory.getAll().length - excludedItemsInInventory;
		if ( itemsInInventoryToBank <= 0 ) {
			status.setType(StatusType.SUCCESS);
			return true;
		}
		
		// Walk to nearest bank and open it
		if ( !walkToNearestBankAndOpen(status) )
			return false;
		
		// Deposit
		return walkToBankAndDepositAllExcept(status, null, exclude);
	}
	
	public static boolean walkToBankAndDepositAllExcept(Locations location, ItemIds...exclude) {
		return walkToBankAndDepositAllExcept(new AIOStatus(), location, exclude);
	}
	
	public static boolean walkToBankAndDepositAllExcept(AIOStatus status, Locations location, ItemIds...exclude) {
		if ( location != null )
			if ( !AIOWalk.walkTo(location) )
				return false;
		
		// Open Bank
		status.setStatus("Opening bank");
		while ( !Banking.isBankScreenOpen() ) {
			Banking.openBank();
			AntiBan.sleep(1000, 500);
		}
		AntiBan.sleep(2000, 1000);
		
		// Deposit
		status.setStatus("Depositing Items");
		Banking.depositAllExcept(ItemNames.get(exclude));
		AntiBan.sleep(1000, 500);
		
		// Close bank
		/*status.setStatus("Closing Bank");
		while ( !Banking.close() ) {
			AntiBan.sleep(1000, 500);
		}*/
		
		// Everything went successful.
		status.setType(StatusType.SUCCESS);
		return true;
	}

	public static boolean walkToNearestBankAndWithdrawFirstItem(int quantity, ItemIds... desiredItem) {
		// Go to bank
		if ( !walkToNearestBankAndOpen() ) {
			General.println("Couldnt open bank");
			return false;
		}
		
		// Withdraw
		if ( !BankingUtil.withdrawFirstItem(quantity, desiredItem) ) {
			General.println("Couldn't withdraw " + quantity + " / " + desiredItem.toString());
			return false;
		}
		
		return true;
	}
	
	public static boolean walkToNearestBankAndWithdrawMoney(int quantity) {
		return walkToNearestBankAndWithdrawFirstItem(quantity, ItemNames.COINS);
	}
}
