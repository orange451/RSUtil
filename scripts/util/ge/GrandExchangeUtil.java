package scripts.util.ge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSInterfaceComponent;
import org.tribot.api2007.types.RSItem;

import com.google.gson.JsonObject;

import scripts.dax_api.shared.jsonSimple.JSONObject;
import scripts.jrest.HttpMethod;
import scripts.jrest.RequestEntity;
import scripts.jrest.ResponseEntity;
import scripts.util.NPCUtil;
import scripts.util.names.ItemIds;
import scripts.util.names.NPCNames;

public class GrandExchangeUtil {
	
	/** Internal GE data */
	private static JsonObject priceData;
	
	/** Last timestamp GE data was downloaded */
	private static long lastUpdateTime;
	
	/** Rate at which we refresh internally stored GE data */
	public static long REFRESH_GE_TIME = 1000 * 60 * 60;
	
	/**
	 * Force fetches all the price data for GE Items.
	 */
	@SuppressWarnings("unchecked")
	public static void forceUpdateGEPriceData() {
		try {
			String address = "https://prices.runescape.wiki/api/v1/osrs/latest";
			RequestEntity<JsonObject> request = new RequestEntity<>(HttpMethod.GET);
			ResponseEntity<JsonObject> response = request.exchange(address, JsonObject.class);
			
			if ( response.getBody() != null )
				priceData = (JsonObject) response.getBody().get("data");
			
			lastUpdateTime = System.currentTimeMillis();
		} catch(Exception e ) {
			General.println("Error fetching GE data.");
			General.println(e);
			e.printStackTrace();
		}
	}
	
	private static void updateGEPriceData() {
		if ( priceData == null || System.currentTimeMillis()-lastUpdateTime > REFRESH_GE_TIME )
			forceUpdateGEPriceData();
	}
	
	/**
	 * Get GEItem from OSRS item ID
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static GEItem getItemData(int id) {
		updateGEPriceData();
		
		JsonObject data = (JsonObject) priceData.get(Integer.toString(id));
		if ( data == null )
			return null;
		
		return new GEItem(id, data);
	}
	
	/**
	 * Get GEItem from RSItem
	 * @param id
	 * @return
	 */
	public static GEItem getItemData(RSItem item) {
		return getItemData(item.getID());
	}

	
	/**
	 * Get GEItem from OSRS ItemNames enum
	 * @param id
	 * @return
	 */
	public static GEItem getItemData(ItemIds item) {
		for (int i : item.getIds()) {
			GEItem g = getItemData(i);
			if ( g != null )
				return g;
		}
		
		return null;
	}
	
	/**
	 * Returns whether the GE is open.
	 * @return
	 */
	public static boolean isGrandExchangeOpen() {
		return GEInterfaces.OFFER_WINDOW.isVisible();
	}
	
	public static RSItem[] getCollectItems() {
		return getCollectItems(-1);
	}
	
	public static RSItem[] getCollectItems(int... itemIds) {
		if (!isGrandExchangeOpen())
			return new RSItem[0];
		
		List<RSItem> items = new ArrayList<RSItem>();
		
		// Iterate over each offer box
		for (int i = 0; i < 8; i++) {
			int offerChild = GEInterfaces.OFFER_BOX_OFFSET + i;
			RSInterfaceChild box = Interfaces.get(GEInterfaces.OFFER_WINDOW.getParent(), offerChild);
			
			// Offer box must be visible
			if ( box == null || box.isHidden() || !box.getChild(0).isHidden() )
				continue;
			
			// Get progress bar
			RSInterfaceComponent bar1 = box.getChild(GEInterfaces.OFFER_BOX_BAR1_OFFSET);
			RSInterfaceComponent bar2 = box.getChild(GEInterfaces.OFFER_BOX_BAR2_OFFSET);
			if ( bar1.isHidden() || bar2.isHidden() || bar1.getWidth() != bar2.getWidth() )
				continue;
			
			// get item data
			RSInterfaceComponent itemComponent = box.getChild(GEInterfaces.OFFER_BOX_ITEM_OFFSET);
			if ( itemComponent == null || itemComponent.getComponentItem() == -1 )
				continue;
			
			// If item data matches criteria, add it
			int itemId = itemComponent.getComponentItem();
			for (int j = 0; j < itemIds.length; j++)
				if ( itemId == itemIds[j] || itemIds[j] == -1 )
					items.add(new RSItem(i, itemComponent.getComponentItem(), itemComponent.getComponentStack(), RSItem.TYPE.GRAND_EXCHANGE_COLLECT));
		}
		
		return items.toArray(new RSItem[items.size()]);
	}
	
	public static boolean collectAll() {
		if (!isGrandExchangeOpen())
			return false;
		
		if ( getCollectItems().length == 0 )
			return true;
		
		GEInterfaces.COLLECT_ALL.click();
		General.sleep(1000, 2000);
		
		return true;
	}
	
	private static boolean clickOffer(RSInterfaceChild offerBox, int offset) {
		// GE must be open.
		if (!isGrandExchangeOpen()) {
			General.println("GE NOT OPEN");
			return false;
		}
		
		// Find free offer box
		if ( offerBox == null ) {
			General.println("NO OFFERS");
			return false;
		}
		
		// Click
		if ( !offerBox.getChild(offset).click("") )
			return false;
		
		General.sleep(1000, 2000);
		return true;
	}
	
	public static boolean offerSell(RSItem item, int price, int quantity) {
		// Click offer
		if ( !clickOffer(getFirstFreeOfferBox(), 1) ) {
			General.println("No free offers!");
			return false;
		}
		
		if ( !item.click("offer") ) {
			General.println("Could not click offer on item");
			return false;
		}
		General.sleep(1000,2000);
		
		// Check if restricted
		if ( GEInterfaces.RESTRICTED_INTERFACE.isVisible() && GEInterfaces.RESTRICTED_INTERFACE.get().getText().contains("restricted") ) {
			GEInterfaces.BACK_BUTTON.click("");
			General.sleep(1000, 2000);
			General.println("Client is restricted from trading!");
			return false;
		}
		
		// Set quantity
		if ( item.getStack() > quantity ) {
			GEInterfaces.QUANTITY_MANUAL.click("");
			General.sleep(1000, 2000);
			userInputText(Integer.toString(quantity));
		} else {
			GEInterfaces.QUANTITY_ALL.click("");
			General.sleep(1000, 2000);
		}
		
		// Set price
		GEInterfaces.PRICE_MANUAL.click("");
		General.sleep(1000, 2000);
		userInputText(Integer.toString(price));
		
		// Confirm
		GEInterfaces.CONFIRM_OFFER.click("");
		General.sleep(1000, 2000);
		
		// We done clicked it!
		return true;
	}
	
	public static boolean offerBuy(String itemName, int price, int quantity) {
		// Click offer
		if ( !clickOffer(getFirstFreeOfferBox(), 0) )
			return false;
		
		// Write item we want to buy
		userInputText(itemName);
		
		// Set quantity
		if ( quantity > 1 ) {
			GEInterfaces.QUANTITY_MANUAL.click("");
			General.sleep(1000, 2000);
			userInputText(Integer.toString(quantity));
		}
		
		// Set price
		GEInterfaces.PRICE_MANUAL.click("");
		General.sleep(1000, 2000);
		userInputText(Integer.toString(price));
		
		// Confirm
		GEInterfaces.CONFIRM_OFFER.click("");
		General.sleep(1000, 2000);
		
		// We done clicked it!
		return true;
	}
	
	private static void userInputText(String text) {
		// Type name of item
		General.println("Attempting to type string: " + text);
		Keyboard.typeString(text);
		General.sleep(1000, 2000);
		
		// Find search result
		GEInterfaces[] results = GEInterfaces.SEARCH_RESULTS;
		for (int i = 0; i < results.length; i++) {
			RSInterface interf = results[i].get();
			if ( interf == null )
				continue;
			
			if ( interf.getText().equalsIgnoreCase(text) && interf.click("") ) {
				General.sleep(500, 1000);
				return;
			}
		}
		
		Keyboard.pressEnter();
		General.sleep(500, 1000);
	}

	private static RSInterfaceChild getFirstFreeOfferBox() {
		for (int i = 0; i < 8; i++) {
			int offerChild = GEInterfaces.OFFER_BOX_OFFSET + i;
			RSInterfaceChild box = Interfaces.get(GEInterfaces.OFFER_WINDOW.getParent(), offerChild);
			if ( box == null || box.isHidden() )
				continue;
			
			if ( box.getChild(0).isHidden() )
				continue;
			
			return box;
		}
		
		return null;
	}

	public static boolean openGrandExchange() {
		return NPCUtil.interactWithFirstNPC("Exchange G", NPCNames.GRAND_EXCHANGE_CLERK);
	}
}
