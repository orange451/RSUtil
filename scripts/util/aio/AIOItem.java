package scripts.util.aio;

import static scripts.util.names.internal.ItemNamesData.get;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSItem;

import scripts.util.ItemUtil;
import scripts.util.NPCUtil;
import scripts.util.PlayerUtil;
import scripts.util.ge.GEItem;
import scripts.util.ge.GrandExchangeUtil;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;
import scripts.util.names.Locations;
import scripts.util.names.NPCNames;

public class AIOItem {
	
	/**
	 * Whether or not the AIO will attempt to buy the item at the GE.
	 */
	public static boolean CAN_USE_GE_TO_BUY_ITEMS = true;
	
	/**
	 * The GP multiplier the AIO will markup the item when attempting to buy it.
	 * A higher number will result in more GP spent, but a higher likelyhood of getting the item.
	 */
	public static double GE_BUY_MARKUP_MULTIPLIER = 1.25;
	
	/**
	 * The GP multiplier the AIO will markdown the item when attempting to sell it.
	 * A lower number will result in less GP earned, but a higher likelyhood of getting the item.
	 */
	public static double GE_BUY_MARKDOWN_MULTIPLIER = 0.8;
	
	/**
	 * Attempts to find a specific item in the players inventory.
	 * If the item is not found, the player will walk to the bank to withdraw.
	 * If the item is not in the bank, the player will go to GE to buy if {@link AIOItem#CAN_USE_GE_TO_BUY_ITEMS} is true.
	 * @param desiredItems
	 * @return
	 */
	public static RSItem getItem(ItemIds desiredItem) {
		return getItem(desiredItem, defaultNoteLogic(desiredItem, 1));
	}
	
	/**
	 * Attempts to find a specific item in the players inventory.
	 * If the item is not found, the player will walk to the bank to withdraw.
	 * If the item is not in the bank, the player will go to GE to buy if {@link AIOItem#CAN_USE_GE_TO_BUY_ITEMS} is true.
	 * @param desiredItems
	 * @return
	 */
	public static RSItem getItem(ItemIds desiredItem, boolean canNote) {
		return getItem(desiredItem, 1, canNote);
	}
	
	/**
	 * Attempts to find a list of items in the players inventory.
	 * If the items are not found, the player will walk to the bank to withdraw.
	 * If the items are not in the bank, the player will go to GE to buy if {@link AIOItem#CAN_USE_GE_TO_BUY_ITEMS} is true.
	 * @param desiredItems
	 * @return
	 */
	public static RSItem[] getItem(ItemIds... desiredItem) {
		List<RSItem> items = new ArrayList<>();
		for (ItemIds id : desiredItem) {
			RSItem t = getItem(id);
			if ( t != null )
				items.add(t);
		}
		
		return items.toArray(new RSItem[items.size()]);
	}
	
	/**
	 * Attempts to find a specific item in the players inventory.
	 * If the item is not found, the player will walk to the bank to withdraw.
	 * If the item is not in the bank, the player will go to GE to buy if {@link AIOItem#CAN_USE_GE_TO_BUY_ITEMS} is true.
	 * @param desiredItems
	 * @return
	 */
	public static RSItem getItem(ItemIds desiredItem, int quantity) {
		return getItem(desiredItem, quantity, defaultNoteLogic(desiredItem, quantity));
	}
	
	/**
	 * Attempts to find a specific item in the players inventory.
	 * If the item is not found, the player will walk to the bank to withdraw.
	 * If the item is not in the bank, the player will go to GE to buy if {@link AIOItem#CAN_USE_GE_TO_BUY_ITEMS} is true.
	 * @param desiredItems
	 * @return
	 */
	public static RSItem getItem(ItemIds desiredItem, int quantity, boolean canNoteItem) {
		return getItem(desiredItem, quantity, quantity, canNoteItem);
	}
	
	/**
	 * Attempts to find a specific item in the players inventory.
	 * If the item is not found, the player will walk to the bank to withdraw.
	 * If the item is not in the bank, the player will go to GE to buy if {@link AIOItem#CAN_USE_GE_TO_BUY_ITEMS} is true.
	 * @param desiredItems
	 * @return
	 */
	public static RSItem getItem(ItemIds desiredItem, int quantity, int buyQuantity) {
		return getItem(desiredItem, quantity, buyQuantity, defaultNoteLogic(desiredItem, quantity));
	}
	
	/**
	 * Attempts to find a specific item in the players inventory.
	 * If the item is not found, the player will walk to the bank to withdraw.
	 * If the item is not in the bank, the player will go to GE to buy if {@link AIOItem#CAN_USE_GE_TO_BUY_ITEMS} is true.
	 * @param desiredItems
	 * @return
	 */
	public static RSItem getItem(ItemIds desiredItem, int quantity, int buyQuantity, boolean canNoteItem) {
		int[] desiredItemIds = get(desiredItem);
		String itemName = ItemUtil.fromId(desiredItemIds[0]).getDefinition().getName();
		
		// Check if it is in inventory, and equip if not.
		General.println("Checking inventory for: ("+quantity+") " + itemName + " (" + Arrays.toString(desiredItemIds) + ")");
		int count = PlayerUtil.getAmountItemsInInventory(desiredItem);
		if ( count >= quantity ) {
			RSItem item = PlayerUtil.getFirstItemInInventory(desiredItem);
			if ( !canNoteItem ) {
				RSItem nonNoteItem = PlayerUtil.getFirstItemInInventory(false, desiredItem);
				
				// If we have item, but no non-noted version, and we can't accept notes
				if ( item != null && nonNoteItem == null ) {
					GrandExchange.close();
					AIOBank.walkToNearestBankAndOpen();
					Banking.deposit(Integer.MAX_VALUE, item.getID()); // Deposit non note version
					General.sleep(2000);
					return AIOItem.getItem(desiredItem, quantity, buyQuantity, canNoteItem);
				}
			} else {
				return item;
			}
		}
		
		// Go to bank to get item
		General.println("Checking bank for: " + itemName + " (" + Arrays.toString(desiredItemIds) + ")");
		if ( AIOBank.walkToNearestBankAndWithdrawFirstItem(canNoteItem, quantity, desiredItem) ) {
			General.sleep(2000);
			return getItem(desiredItem, quantity, buyQuantity, canNoteItem);
		}

		// Not in bank, lets go to GE and buy it!
		if ( CAN_USE_GE_TO_BUY_ITEMS && buyQuantity > 0 && !desiredItem.equals(ItemNames.COINS) ) {
			General.println("Attempting to buy item from GE");
			if ( buyAtGE(desiredItem, buyQuantity) ) {
				General.sleep(2000);
				RSItem inventoryItem = getItem(desiredItem, quantity, buyQuantity, true);
				boolean noted = inventoryItem.getStack() > 1;
				
				if ( !noted || canNoteItem ) {
					return inventoryItem;
				} else {
					return AIOItem.getItem(desiredItem, quantity, buyQuantity, canNoteItem);
				}
			}
		}
		
		// Failed! :(
		General.println("Could not get item...");
		return null;
	}
	
	private static boolean defaultNoteLogic(ItemIds item, int quantity) {
		int freeSpace = 28 - Inventory.getAll().length;
		return ItemUtil.isStackable(item) ? (false) : (quantity > freeSpace);
	}
	
	/**
	 * Real shitty logic to go to the GE and buy an item.
	 * @param desiredItem
	 * @param quantity
	 * @return
	 */
	private static boolean buyAtGE(ItemIds desiredItem, int quantity) {
		GEItem ge = GrandExchangeUtil.getItemData(desiredItem);
		if ( ge == null )
			return false;
		
		// Walk to GE
		if ( !Locations.isNear(Locations.GRAND_EXCHANGE) )
			if ( !AIOWalk.walkTo(Locations.GRAND_EXCHANGE) )
				return false;
		
		// Figure out how much item is worth
		int buyPrice = (int) Math.ceil((Math.max(ge.getSellAverage(), ge.getBuyAverage()) * GE_BUY_MARKUP_MULTIPLIER));
		if ( buyPrice == 0 ) {
			GrandExchangeUtil.forceUpdateGEPriceData();
			General.println("Could not get GE price data...");
			return false;
		}
		
		// Attempt to get money from bank
		int money = PlayerUtil.getAmountOfMoney();
		General.println("Will buy item for " + buyPrice + " gp");
		if ( money < buyPrice*quantity ) {
			if ( AIOItem.getItem(ItemNames.COINS, buyPrice*quantity) == null ) {
				General.println("INSUFFICIENT FUNDS");
				return false;
			}
			General.sleep(800,1400);
		}
		
		// Make sure we have money
		if ( PlayerUtil.getAmountItemsInInventory(ItemNames.COINS) == 0 ) {
			General.println("We don't have money...");
			return false;
		}
		
		// Close bank
		while ( Banking.isBankScreenOpen() ) {
			Banking.close();
			General.sleep(500);
		}
		
		// Open GE
		while(!GrandExchangeUtil.openGrandExchange() )
			General.sleep(1000);
		General.sleep(2000, 2400);
		while(Player.isMoving())
			General.sleep(1000);
		General.sleep(250);
		General.println("Attempting to buy " + quantity + " " + ge.getName() + " for " + buyPrice + " gp");
		
		// If the item isn't available to collect, make an offer!
		if ( GrandExchangeUtil.getCollectItems(ge.getId()).length == 0 ) {
			boolean bought = GrandExchangeUtil.offerBuy(ge.getName(), buyPrice, quantity);
			if ( !bought )
				return false;
		}
		
		// Wait for offer to conclude
		while(GrandExchangeUtil.getCollectItems().length == 0) {
			General.println("Waiting for offers to collect...");
			General.sleep(1000);
			
			if ( !GrandExchangeUtil.isGrandExchangeOpen() )
				GrandExchangeUtil.openGrandExchange();
		}

		// Collect the offer
		General.sleep(800,1400);
		GrandExchangeUtil.collectAll();
		General.sleep(1500,2200);
		GrandExchange.close();
		
		// Mark that we found the item
		return true;
	}

	public static boolean sellItem(ItemIds item, int quantity) {
		GEItem ge = GrandExchangeUtil.getItemData(item);
		if ( ge == null )
			return false;
		
		int sellPrice = (int) Math.ceil(Math.max(ge.getSellAverage(), ge.getBuyAverage()) * GE_BUY_MARKDOWN_MULTIPLIER);
		
		// Walk to GE
		if ( !Locations.isNear(Locations.GRAND_EXCHANGE) )
			if ( !AIOWalk.walkTo(Locations.GRAND_EXCHANGE) )
				return false;
		
		// Get item to sell
		RSItem sellItem = AIOItem.getItem(item, quantity, 0, quantity > 1);
		if ( sellItem == null )
			return false;
		
		// Close bank
		while ( Banking.isBankScreenOpen() ) {
			Banking.close();
			General.sleep(500);
		}
		
		// Open GE
		while(!NPCUtil.interactWithFirstNPC("Exchange G", NPCNames.GRAND_EXCHANGE_CLERK) )
			General.sleep(1000);
		General.sleep(2000, 2400);
		while(Player.isMoving())
			General.sleep(1000);
		General.sleep(250);
		
		General.println("Attempting to sell " + quantity + " " + ge.getName() + " for " + sellPrice + " gp ea");
		
		// Collect anything we can collect
		GrandExchangeUtil.collectAll();
		
		// Sell!
		if ( !GrandExchangeUtil.offerSell(sellItem, sellPrice, quantity) ) {
			General.println("COULD NOT SELL!");
			return false;
		}

		// Wait for offer to conclude
		while(GrandExchangeUtil.getCollectItems().length == 0) {
			General.println("Waiting for offers to collect...");
			General.sleep(1000);
		}

		// Collect the offer
		General.sleep(800,1400);
		GrandExchangeUtil.collectAll();
		General.sleep(800,1400);
		GrandExchange.close();
		
		return true;
	}
}
