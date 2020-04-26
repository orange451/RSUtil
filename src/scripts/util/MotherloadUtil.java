package scripts.util;

import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSInterface;

import scripts.util.names.Locations;

public class MotherloadUtil {
	/**
	 * Returns the amount of ores in your hopper bag at motherload mine.
	 */
	public static int getHopperBagAmount() {
		if ( !isInMotherloadMine() )
			return 0;
		
		RSInterface root = Interfaces.get(382, 4, 2);
		if ( root == null || root.getText() == null || root.getText().length() == 0 )
			return 0;
		
		try {
			return Integer.parseInt(root.getText());
		} catch(Exception e) {
			return 0;
		}
	}
	
	public static boolean isInMotherloadMine() {
		return Locations.isNear(Locations.MOTHERLOAD_MINE);
	}
}
