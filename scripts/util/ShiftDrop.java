package scripts.util;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.Inventory.DROPPING_PATTERN;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSItem;

import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;

/**
 * Shift dropping
 * 
 * @author volcom3d, orange451
 *
 */
public class ShiftDrop {
	
	/**
	 * Enables shift drop option
	 * @param enabled
	 */
	public static void setShiftDropEnabled(boolean enabled) {
		if ( isShiftDropEnabled() == enabled )
			return;
		
		// Option options
		GameTab.open(TABS.OPTIONS);
		General.sleep(400,800);
		
		// Click joystick
		RSInterface joystick = Interfaces.get(261, 1, 6);
		if ( joystick == null )
			return;
		joystick.click("");
		General.sleep(400,800);
		
		// Click shift button
		RSInterface shiftButton = Interfaces.get(261, 79);
		if ( shiftButton == null )
			return;
		shiftButton.click("");
	}
	
	/**
	 * Returns whether shipft drop is enabled.
	 * @return
	 */
	public static boolean isShiftDropEnabled() {
		return Game.getSetting(1055) == -2147343104;
	}
	
	/**
	 * Shift drop a list of Item Ids based on a specified dropping pattern.
	 * @param pattern
	 * @param ids
	 */
	public static void shiftDrop(DROPPING_PATTERN pattern, int... ids) {
		
		// Enable shift dropping
		setShiftDropEnabled(true);
		
		RSItem[] itemsToDrop;
		ArrayList<RSItem> itemsToDropOrdered = new ArrayList<RSItem>();
		if (ids.length == 0) {
			itemsToDrop = Inventory.getAll();
		} else {
			itemsToDrop = Inventory.find(ids);
		}

		// Ordering items.
		switch (pattern) {
		case LEFT_TO_RIGHT:
			for (RSItem item : itemsToDrop) {
				itemsToDropOrdered.add(item);
			}
			break;
		case TOP_TO_BOTTOM:
			for (int i = 0; i < 4; i++) {
				for (RSItem item : itemsToDrop) {
					if (item.getIndex() % 4 == i) {
						itemsToDropOrdered.add(item);
					}
				}
			}
			break;
		case ZIGZAG:
			for (int i = 0; i < 4; i++) {
				for (RSItem item : itemsToDrop) {
					if (item.getIndex() >= (0 + 8 * i) && item.getIndex() <= (3 + 8 * i)) {
						itemsToDropOrdered.add(item);
					}
				}
				for (int j = itemsToDrop.length - 1; j >= 0; j--) {
					if (itemsToDrop[j].getIndex() >= (4 + 8 * i) && itemsToDrop[j].getIndex() <= (7 + 8 * i)) {
						itemsToDropOrdered.add(itemsToDrop[j]);
					}
				}
			}
			break;
		default:
			break;
		}

		// Drop items.
		if (Inventory.open()) {
			Keyboard.sendPress(KeyEvent.CHAR_UNDEFINED, KeyEvent.VK_SHIFT);
			General.sleep(250, 500);
			for (RSItem it : itemsToDropOrdered) {
				while(!it.click(it.getDefinition().getName()))
					General.sleep(50,100);
				General.sleep(General.randomSD(50, 1000, 200, 1));
			}
			General.sleep(250, 500);
			Keyboard.sendRelease(KeyEvent.CHAR_UNDEFINED, KeyEvent.VK_SHIFT);
		}
	}
	
	/**
	 * Shift drop a list of item ids with a random Dropping pattern.
	 * @param ids
	 */
	public static void shiftDrop(ItemIds... ids) {
		shiftDrop(ItemNames.get(ids));
	}
	
	/**
	 * Shift drop a list of item ids with a random Dropping pattern.
	 * @param ids
	 */
	public static void shiftDrop(int... ids) {
		int rand = General.random(1, 3);
		switch (rand) {
		case 1:
			shiftDrop(Inventory.DROPPING_PATTERN.LEFT_TO_RIGHT, ids);
			break;
		case 2:
			shiftDrop(Inventory.DROPPING_PATTERN.TOP_TO_BOTTOM, ids);
			break;
		case 3:
			shiftDrop(Inventory.DROPPING_PATTERN.ZIGZAG, ids);
			break;
		}
	}

	/**
	 * Shift drop entire inventory.
	 */
	public static void shiftDropAll() {
		shiftDrop(new int[] {});
	}
}