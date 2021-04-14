package scripts.util.aio;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSInterfaceChild;

import scripts.util.BankingUtil;
import scripts.util.PlayerUtil;
import scripts.util.misc.AntiBan;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;
import scripts.util.names.Locations;

public class AIOBank {
	private static long OFFSET_BANK = 101;
	
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
		// Bank already open
		if ( Banking.isBankScreenOpen() ) 
				return true;
		
		// Walk to nearest bank
		if ( !Banking.isInBank() ) {
			if ( !walkToNearestBank(status) ) {
				status.setType(StatusType.FAILED);
				return false;
			}
		} 
		
		// Open Bank
		openBank(status);
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
	 * Walks the player to the nearest bank. Opens the bank. Deposits specified items.
	 */
	public static boolean walkToNearestBankAndDeposit(ItemIds... items) {
		return walkToNearestBankAndDeposit(new AIOStatus(), items);
	}
		
	/**
	 * Walks the player to the nearest bank. Opens the bank. Deposits specified items.
	 */
	public static boolean walkToNearestBankAndDeposit(AIOStatus status, ItemIds... items) {
		if ( PlayerUtil.isInDanger() )
			walkToNearestBank();
		
		// Return since inventory is empty
		int itemsInInventory = PlayerUtil.getAmountItemsInInventory(false, true, items);
		if ( itemsInInventory == 0 ) {
			status.setType(StatusType.SUCCESS);
			return true;
		}
		
		// Walk to nearest bank and open it
		if ( !walkToNearestBankAndOpen(status) )
			return false;
		
		// Deposit
		return walkToBankAndDeposit(status, null, items);
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
		int excludedItemsInInventory = PlayerUtil.getAmountItemsInInventory(false, true, exclude);
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
	
	/**
	 * Walks the player to the specified bank. Opens the bank. Deposits all.
	 */
	public static boolean walkToBankAndDepositAll(Locations location) {
		return walkToBankAndDepositAll(new AIOStatus(), location);
	}
	
	/**
	 * Walks the player to the specified bank. Opens the bank. Deposits all.
	 */
	public static boolean walkToBankAndDepositAll(AIOStatus status, Locations location) {
		return walkToBankAndDepositAllExcept(status, location, new ItemNames[] {});
	}

	/**
	 * Walks the player to the specified bank. Opens the bank. Deposits all except the items in the exclusion list.
	 */
	public static boolean walkToBankAndDepositAllExcept(Locations location, ItemIds...exclude) {
		return walkToBankAndDepositAllExcept(new AIOStatus(), location, exclude);
	}

	/**
	 * Walks the player to the specified bank. Opens the bank. Deposits all except the items in the exclusion list.
	 */
	public static boolean walkToBankAndDepositAllExcept(AIOStatus status, Locations location, ItemIds...exclude) {
		if ( location != null )
			if ( !AIOWalk.walkTo(location) )
				return false;
		
		// Open Bank
		openBank(status);
		
		// Deposit
		General.println("Depositing");
		status.setStatus("Depositing Items");
		int amountExcept = PlayerUtil.getAmountItemsInInventory(exclude);
		if ( amountExcept > 0 ) {
			Banking.depositAllExcept(ItemNames.get(exclude));
		} else {
			Banking.depositAll();
		}
		AntiBan.sleep(500 + (int)(AntiBan.getAccountOffset(OFFSET_BANK)*1200), 1500);
		
		// Close bank
		/*status.setStatus("Closing Bank");
		while ( !Banking.close() ) {
			AntiBan.sleep(1000, 500);
		}*/
		
		// Everything went successful.
		status.setType(StatusType.SUCCESS);
		return true;
	}

	/**
	 * Walks the player to the specified bank. Opens the bank. Deposits all the items in the list.
	 */
	public static boolean walkToBankAndDeposit(Locations location, ItemIds...items) {
		return walkToBankAndDeposit(new AIOStatus(), location, items);
	}
	
	private static void openBank(AIOStatus status) {
		status.setStatus("Opening bank");
		General.println("Banking");
		if ( !Banking.isBankScreenOpen() ) {
			long TIMEOUT = System.currentTimeMillis() + 4000;
			while ( !Banking.isBankScreenOpen() && System.currentTimeMillis() < TIMEOUT) {
				Banking.openBank();
				AntiBan.sleep(500 + (int)(AntiBan.getAccountOffset(OFFSET_BANK)*1200), 500);
			}
			AntiBan.sleep(500 + (int)(AntiBan.getAccountOffset(OFFSET_BANK)*1200), 1200);
		}
		
		RSInterfaceChild maybeCloseButtonPerhapsNotSure = Interfaces.get(664, 28);
		if ( maybeCloseButtonPerhapsNotSure != null ) {
			maybeCloseButtonPerhapsNotSure.click("");
			AntiBan.sleep(500 + (int)(AntiBan.getAccountOffset(OFFSET_BANK)*1200), 1200);
		}
	}

	/**
	 * Walks the player to the specified bank. Opens the bank. Deposits all the items in the list.
	 */
	public static boolean walkToBankAndDeposit(AIOStatus status, Locations location, ItemIds...items) {
		if ( location != null )
			if ( !AIOWalk.walkTo(location) )
				return false;
		
		// Open Bank
		openBank(status);
		
		// Deposit
		status.setStatus("Depositing Items");
		Banking.deposit(Integer.MAX_VALUE, ItemNames.get(items));
		AntiBan.sleep(500 + (int)(AntiBan.getAccountOffset(OFFSET_BANK)*1200), 1500);
		
		// Everything went successful.
		status.setType(StatusType.SUCCESS);
		return true;
	}
	
	public static boolean walkToNearestBankAndWithdrawMoney(int quantity) {
		return walkToNearestBankAndWithdrawFirstItem(quantity, ItemNames.COINS);
	}

	public static boolean walkToNearestBankAndWithdrawFirstItem(int quantity, ItemIds... desiredItem) {
		return walkToNearestBankAndWithdrawFirstItem(false, quantity, desiredItem);
	}
	
	public static boolean walkToNearestBankAndWithdrawFirstItem(boolean noted, int quantity, ItemIds... desiredItem) {
		// Go to bank
		if ( !Banking.isBankScreenOpen() ) {
			if ( !walkToNearestBankAndOpen() ) {
				General.println("Couldnt open bank");
				return false;
			}
		}
		
		// Withdraw
		if ( !BankingUtil.withdrawFirstItem(noted, quantity, desiredItem) ) {
			General.println("Couldn't withdraw " + quantity + " / " + desiredItem.toString());
			return false;
		}
		
		return true;
	}
}
