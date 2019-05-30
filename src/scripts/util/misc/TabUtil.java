package scripts.util.misc;

import java.awt.event.KeyEvent;

import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;

public class TabUtil {
	
	/**
	 * Close a tab via keyboard.
	 * @param tab
	 */
	public static void closeTab(TABS tab) {
		if ( tab == null || tab.equals(TABS.NULL) )
			return;
		
		if ( GameTab.getOpen() != tab )
			return;
		
		int keycode = getTabKeyCode(tab);
		sendKey(keycode);
	}
	
	/**
	 * Open a tab via keyboard.
	 * @param tab
	 */
	public static void openTab(TABS tab) {
		if ( tab == null || tab.equals(TABS.NULL) )
			return;
		
		if ( GameTab.getOpen() == tab )
			return;
		
		int keycode = getTabKeyCode(tab);
		sendKey(keycode);
	}
	
	private static void sendKey(int keycode) {
		Keyboard.sendPress((char)keycode, keycode);
		General.sleep(10);
		Keyboard.sendRelease((char)keycode, keycode);
	}
	
	private static int getTabKeyCode(TABS tab) {
		switch(tab) {
			case INVENTORY: {
				return KeyEvent.VK_ESCAPE;
			}
			case COMBAT: {
				return KeyEvent.VK_F1;
			}
			case STATS: {
				return KeyEvent.VK_F2;
			}
			case QUESTS: {
				return KeyEvent.VK_F3;
			}
			case EQUIPMENT: {
				return KeyEvent.VK_F4;
			}
			case PRAYERS: {
				return KeyEvent.VK_F5;
			}
			case MAGIC: {
				return KeyEvent.VK_F6;
			}
			case CLAN: {
				return KeyEvent.VK_F7;
			}
			case FRIENDS: {
				return KeyEvent.VK_F8;
			}
			case ACCOUNT: {
				return KeyEvent.VK_F9;
			}
			case OPTIONS: {
				return KeyEvent.VK_F10;
			}
			case EMOTES: {
				return KeyEvent.VK_F11;
			}
			case MUSIC: {
				return KeyEvent.VK_F12;
			}
			default: {
				return KeyEvent.VK_ESCAPE;
			}
		}
	}
}
