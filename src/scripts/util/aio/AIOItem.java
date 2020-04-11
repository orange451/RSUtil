package scripts.util.aio;

import org.tribot.api2007.Camera;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.util.DPathNavigator;

import scripts.util.PlayerUtil;
import scripts.util.misc.AntiBan;
import scripts.util.names.ItemNamesData;
import scripts.util.names.Locations;

public class AIOItem {
	public static boolean walkToLocationForItem(Locations location, ItemNamesData item) {
		return walkToLocationForItem(location, item, "");
	}
	
	public static boolean walkToLocationForItem(Locations location, ItemNamesData item, String click) {
		if (Player.isMoving()) {
			return false;
		}

		AIOWalk.walkToIfFar(location, false);
		
		if ( location != null ) {
			if ( Player.getPosition().distanceTo(location.getCenter()) > 3 )
				new DPathNavigator().traverse(location.getCenter());
		}

		RSGroundItem[] bs = GroundItems.findNearest(item.getIds());
		if ((bs == null) || (bs.length == 0)) {
			return false;
		}

		walkTo(bs[0].getPosition());
		Camera.turnToTile(bs[0]);

		int timeout = 0;
		int startAmount = PlayerUtil.getAmountItemsInInventory(item);
		while ((!bs[0].click(click)) && (timeout < 8)) {
			timeout++;
			AntiBan.idle(83, 156);
			
			int currentAmount = PlayerUtil.getAmountItemsInInventory(item);
			if ( currentAmount > startAmount )
				break;
		}

		if (timeout >= 8) {
			return false;
		}

		AntiBan.sleep(1000, 250);
		while ((Player.isMoving()) || (Player.getAnimation() != -1)) {
			AntiBan.sleep(500, 200);
		}
		return true;
	}
}
