package scripts.util.aio;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;

import scripts.util.PlayerUtil;
import scripts.util.misc.AntiBan;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;
import scripts.util.task.BotTask;
import scripts.util.task.BotTaskWalkToBank;

public class AIOBank {
	/**
	 * Walks to nearest bank.
	 * @return
	 */
	public static boolean walkToNearestBank() {
		return walkToNearestBank(new AIOStatus());
	}
	
	/**
	 * Walks to nearest bank.
	 * @return
	 */
	public static boolean walkToNearestBank(AIOStatus status) {
		status.setStatus("Walking to bank...");
		
		// Generate walk to bank task
		BotTaskWalkToBank walkToBank = new BotTaskWalkToBank() {
			@Override
			public BotTask getNextTask() {
				return null;
			}

			@Override
			public void init() {
				//
			}
		};
		
		// Complete the task
		int tries = 0;
		while( !walkToBank.isTaskComplete() ) { // Forces task to run
			General.sleep(1000);
			tries++;
			if ( tries > 20 ) {
				status.setType(StatusType.FAILED);
				return false;
			}
		}
		
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
		return bankNearest(status, new ItemNames[] {});
	}
	
	/**
	 * Walks the player to the nearest bank. Opens the bank. Deposits everything except the exclusion list.
	 */
	public static boolean bankNearest(ItemIds... exclude) {
		return bankNearest( new AIOStatus(), exclude );
	}
	
	/**
	 * Walks the player to the nearest bank. Opens the bank. Deposits everything except the exclusion list.
	 */
	public static boolean bankNearest(AIOStatus status, ItemIds... exclude) {
		
		// Return since inventory is empty
		int excludedItemsInInventory = PlayerUtil.getAmountItemsInInventory(exclude);
		int itemsInInventoryToBank = Inventory.getAll().length - excludedItemsInInventory;
		if ( itemsInInventoryToBank <= 0 ) {
			status.setType(StatusType.SUCCESS);
			return true;
		}
		
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
