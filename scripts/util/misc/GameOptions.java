package scripts.util.misc;

import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.types.RSInterface;

public class GameOptions {
	
	public static void open() {
		// Option options
		GameTab.open(TABS.OPTIONS);
		General.sleep(400,800);
		
		// Click joystick
		RSInterface joystick = Interfaces.get(116, 75, 9);
		if ( joystick == null )
			return;
		joystick.click("");
		General.sleep(400,800);
	}
	
	public static void close() {
		GameOptionsInterfaces.CLOSE.click("");
		General.sleep(400,800);
	}
	
	/**
	 * Enables shift drop option
	 * @param enabled
	 */
	public static void setShiftDropEnabled(boolean enabled) {
		if ( isShiftDropEnabled() == enabled )
			return;
		
		// Option options
		open();
		
		RSInterface moreInfo = GameOptionsInterfaces.MORE_INFORMATION.get();
		if ( !moreInfo.getText().contains("more ") )
			moreInfo.click("");
		
		GameOptionsInterfaces.CONTROLS.click("");
		General.sleep(400,800);
		GameOptionsInterfaces.SHIFT_DROP_CHECKBOX.click("");
	}
	
	/**
	 * Returns whether shipft drop is enabled.
	 * @return
	 */
	public static boolean isShiftDropEnabled() {
		return Game.getSetting(1055) == -2147343104;
	}
}
