package scripts.util;

import org.tribot.api.General;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Players;
import org.tribot.api2007.Trading;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSPlayer;

import scripts.util.misc.AntiBan;
import scripts.util.names.ItemIds;

public class TradingUtil {
	/**
	 * Clicks the trade with option on a player. Returns true if the trade window successfully opens up.
	 * @param player
	 * @return
	 */
	public static boolean tradePlayer(RSPlayer player) {
		return tradePlayer(player, 15000);
	}
	
	/**
	 * Clicks the trade with option on a player. Returns true if the trade window successfully opens up.
	 * @param player
	 * @return
	 */
	public static boolean tradePlayer(RSPlayer player, long timeout) {
		player.click("trade with " + player.getName());
		
		// Wait for accept
		long start = System.currentTimeMillis();
		AntiBan.sleep(3000, 1000);
		while(Trading.getOpponentName() == null || Trading.getOpponentName().equals("") ) {
			AntiBan.sleep(1000, 250);
			
			// Waiting too long for trade
			if ( System.currentTimeMillis()-start > timeout ) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Gets the player who sent the most recent trade request.<br>
	 * This is computed by scraping the text chat interface.
	 * @return
	 */
	public static RSPlayer getTradeRequest() {
		RSInterfaceChild trade = Interfaces.get(162, 59);
		String[] actions = trade.getActions();
		if ( actions == null || actions.length == 0 || actions[0] == null )
			return null;
		
		if ( actions[0].equals("Accept trade") ) {
			// This could be replaced with REGEX.
			String name = trade.getComponentName();
			String[] t = name.split(">");
			String[] t1 = t[1].split("<");
			String playerName = t1[0];
			
			// Find the player with the matching name.
			RSPlayer[] players = Players.findNearest(playerName);
			if ( players == null || players.length == 0 )
				return null;
			
			return players[0];
		}
		
		return null;
	}
	
	
	/**
	 * Performs a simple trade. Does not offer any items. Only accepts.
	 * @param with
	 * @return
	 */
	public static boolean doSimpleTradeAccept(RSPlayer with) {
		return doSimpleTrade(with);
	}
	
	/**
	 * Performs a simple trade. Offers all of the items in the list. Then clicks accept on all menus.
	 * @param tradePlayer
	 * @param items
	 * @return
	 */
	public static boolean doSimpleTrade(RSPlayer tradePlayer, ItemIds... items) {
		// Sleep a little longer if we're not trading items... wait for the player to offer...
		if ( items.length == 0 )
			AntiBan.sleep(4000, 500);
		
		// Offer the items
		for (int i = 0; i < items.length; i++) {
			RSItem item = PlayerUtil.getFirstItemInInventory(items[i]);
			if ( item == null )
				break;
			
			item.click("-all");
			AntiBan.sleep(500, 200);
		}

		// Click accept
		while(Trading.getOpponentName() != null && Trading.getOpponentName().length() > 0 ) {
			AntiBan.sleep(4000, 500);
			Trading.accept();
			General.sleep(1000);
		}
		
		return true;
	}
}
