package scripts.util.aio;

import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;

import scripts.util.PlayerUtil;
import scripts.util.misc.AntiBan;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;

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
	public static boolean bankNearest() {
		return bankNearest(new AIOStatus());
	}
	
	/**
	 * Walks the player to the nearest bank. Opens the bank. Deposits all.
	 */
	public static boolean bankNearest(AIOStatus status) {
		return bankNearestExcept(status, new ItemNames[] {});
	}
	
	/**
	 * Walks the player to the nearest bank. Opens the bank. Deposits everything except the exclusion list.
	 */
	public static boolean bankNearestExcept(ItemIds... exclude) {
		return bankNearestExcept( new AIOStatus(), exclude );
	}
	
	/**
	 * Walks the player to the nearest bank. Opens the bank. Deposits everything except the exclusion list.
	 */
	public static boolean bankNearestExcept(AIOStatus status, ItemIds... exclude) {
		
		// Return since inventory is empty
		int excludedItemsInInventory = PlayerUtil.getAmountItemsInInventory(exclude);
		int itemsInInventoryToBank = Inventory.getAll().length - excludedItemsInInventory;
		if ( itemsInInventoryToBank <= 0 ) {
			status.setType(StatusType.SUCCESS);
			return true;
		}
		
		// Walk to nearest bank and open it
		if ( !walkToNearestBankAndOpen(status) )
			return false;
		
		// Deposit
		status.setStatus("Depositing Items");
		Banking.depositAllExcept(ItemNames.get(exclude));
		AntiBan.sleep(1000, 500);
		
		// Close bank
		status.setStatus("Closing Bank");
		while ( !Banking.close() ) {
			AntiBan.sleep(1000, 500);
		}
		
		// Everything went successful.
		status.setType(StatusType.SUCCESS);
		return true;
	}
}
