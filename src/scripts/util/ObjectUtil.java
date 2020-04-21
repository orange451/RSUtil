package scripts.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.tribot.api.General;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Players;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSPlayer;
import org.tribot.api2007.types.RSTile;

import scripts.util.aio.AIOWalk;
import scripts.util.misc.AntiBan;
import scripts.util.names.ObjectNames;

public class ObjectUtil {
	/**
	 * Returns all RSObjects with 20 tiles of the player with a matching ObjectNames. See {@link #getAll(ObjectNames, int)}.
	 * @param obj
	 * @return
	 */
	public static List<RSObject> getAll(ObjectNames obj) {
		return getAll(obj, 25);
	}
	
	/**
	 * Returns all RSObjects with a specified distance of the player with a matching ObjectNames.
	 * @param obj
	 * @param distance
	 * @return
	 */
	public static List<RSObject> getAll(ObjectNames obj, int distance) {
		RSObject[] objects = Objects.findNearest(distance, obj.getIds());
		return Arrays.asList(objects);
	}

	/**
	 * Returns whether a RSObject is a specific objectname. ObjectNames can have multiple ids, this method will check all of them.
	 * @param o
	 * @param obj
	 * @return
	 */
	public static boolean isA(RSObject o, ObjectNames obj) {
		if ( o == null )
			return false;
		
		int[] ids = obj.getIds();
		for (int ii = 0; ii < ids.length; ii++) {
			if (o.getID() == ids[ii]) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the closest object with a matching object name to the given position.
	 * All objects returned MUST be reachable from {@link PathFinding#canReach()}.
	 * @param obj
	 * @param position
	 * @param MAX_DIST
	 * @return
	 */
	public static RSObject get(ObjectNames obj, Positionable position, int MAX_DIST) {
		return get(obj, position, MAX_DIST, false);
	}
	
	/**
	 * Returns the closest object with a matching object name to the given position.
	 * All objects returned MUST be reachable from {@link PathFinding#canReach()}.
	 * @param obj
	 * @param position
	 * @param MAX_DIST
	 * @return
	 */
	public static RSObject get(ObjectNames obj, Positionable position, int MAX_DIST, boolean adjacentPlayersAddDistance) {
		List<RSObject> objects = getAll(obj);
		RSObject mine = null;
		int dist = MAX_DIST;
		
		for (int i = 0; i < objects.size(); i++) {
			RSObject o = (RSObject)objects.get(i);
			int tdist = o.getPosition().distanceTo(position);
			boolean hasAdjacent = adjacentPlayersAddDistance && hasAdjacentPlayer(o);
			
			if ( hasAdjacent ) 
				tdist += 10;
			
			if ( !PathFinding.canReach(Player.getPosition(), o.getPosition(), true) )
				tdist = Integer.MAX_VALUE;
			
			if (tdist < dist) {
				dist = tdist;
				mine = o;
			}
		}

		return mine;
	}

	private static boolean hasAdjacentPlayer(RSObject object) {
		RSTile center = object.getPosition();
		RSTile[] adjacentTiles = new RSTile[] {
				new RSTile(center.getX() + 1, center.getY(), center.getPlane()),
				new RSTile(center.getX() - 1, center.getY(), center.getPlane()),
				new RSTile(center.getX(), center.getY() + 1, center.getPlane()),
				new RSTile(center.getX(), center.getY() - 1, center.getPlane())
		};
		
		RSPlayer[] players = Players.getAll();
		for (RSTile tile : adjacentTiles) {
			for (RSPlayer player : players) {
				if ( player.equals(Player.getRSPlayer()))
					continue;
				
				if ( player.getPosition().equals(tile) )
					return true;
			}
		}
		
		return false;
	}

	/**
	 * Returns the closest object with a matching object name to the player. See {@link #get(ObjectNames, RSTile, int)}.
	 * Additionally, all objects returned MUST be reachable from {@link PathFinding#canReach()}.
	 * @return
	 */
	public static RSObject get(ObjectNames obj) {
		return get(obj, 25);
	}

	/**
	 * Returns the closest object with a matching object name to the player. See {@link #get(ObjectNames, RSTile, int)}.
	 * Additionally, all objects returned MUST be reachable from {@link PathFinding#canReach()}.
	 * @param obj
	 * @param MAX_DIST
	 * @return
	 */
	public static RSObject get(ObjectNames obj, int MAX_DIST) {
		return get(obj, Player.getPosition(), MAX_DIST);
	}

	/**
	 * Clicks an object with a given option.
	 * @param objectName
	 * @param option
	 */
	public static void clickObject(ObjectNames objectName, String option) {
		while (Player.isMoving()) {
			General.sleep(1000L);
		}

		RSObject obj = get(objectName, 16);
		if (obj == null) {
			return;
		}
		while (!obj.click(new String[] { option })) {
			General.sleep(86, 173);
		}
		General.sleep(1000L);
	}
	
	/**
	 * Attempt to interact with a specific object
	 * @param object
	 * @param click
	 * @return
	 */
	public static boolean interactWithObject(ObjectNames object) {
		return interactWithObject(object, "");
	}
	
	/**
	 * Attempt to interact with a specific object
	 * @param object
	 * @param click
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean interactWithObject(ObjectNames object, String click) {
		RSObject obj = scripts.util.ObjectUtil.get(object, 10);
		if (obj == null) {
			return false;
		}

		if (!PathFinding.canReach(obj, false)) {
			AIOWalk.walkToLegacy(obj.getPosition());
		}
		Camera.turnToTile(obj);


		int tries = 0;
		while (!obj.click(new String[] { click }) && tries < 8) {
			AntiBan.idle(83, 156);
			tries++;
		}
		
		if ( tries >= 8 )
			return false;
		
		General.sleep(1000L);
		while ((Player.isMoving()) || (Player.getAnimation() != -1)) {
			General.sleep(500L);
		}
		return true;
	}
}
