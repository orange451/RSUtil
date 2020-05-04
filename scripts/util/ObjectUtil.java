package scripts.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tribot.api.General;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Players;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSObjectDefinition;
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
		
		List<RSObject> list = new ArrayList<>();
		for (RSObject object : objects) {
			list.add(object);
		}
		
		objects = Objects.findNearest(distance, obj.getName());
		for (RSObject object : objects) {
			if ( list.contains(object) )
				continue;
			list.add(object);
		}
		
		return list;
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
		RSObject[] objects = getAll(obj, position, MAX_DIST, adjacentPlayersAddDistance);
		if ( objects.length == 0 )
			return null;
		
		return objects[0];
	}
	
	/**
	 * Returns the closest objects with a matching object name to the given position.
	 * All objects returned MUST be reachable from {@link PathFinding#canReach()}.
	 * @param obj
	 * @param position
	 * @param MAX_DIST
	 * @return
	 */
	public static RSObject[] getAll(ObjectNames obj, Positionable position, int MAX_DIST, boolean adjacentPlayersAddDistance) {
		List<RSObject> objects = getAll(obj);
		
		List<RSObject> temp = new ArrayList<>();
		Map<RSObject, Double> distance = new HashMap<>();
		
		// Get all relevent objects
		for (int i = 0; i < objects.size(); i++) {
			RSObject o = (RSObject)objects.get(i);

			if ( !PathFinding.canReach(Player.getPosition(), o.getPosition(), true) )
				continue;

			int tdist = o.getPosition().distanceTo(position);
			boolean hasAdjacent = adjacentPlayersAddDistance && hasAdjacentPlayers(o, false);
			
			if ( hasAdjacent ) 
				tdist += 10;

			// Farther away the object is, the more uncertain we are of exact distance
			for (int j = 0; j < (int)(tdist / 3); j++)
				tdist += General.random(-1, 1);
			
			distance.put(o, (double) tdist);
			temp.add(o);
		}
		
		// sort
		temp.sort((Object arg0, Object arg1) -> {
			return (int) (distance.get(arg0)-distance.get(arg1));
		});

		// return
		return temp.toArray(new RSObject[temp.size()]);
	}

	public static boolean hasAdjacentPlayers(RSObject object, boolean includeUs) {
		return getAdjacentPlayers(object, includeUs).length > 0;
	}

	public static RSPlayer[] getAdjacentPlayers(RSObject object, boolean includeUs) {
		if ( object == null )
			return new RSPlayer[] {};
		
		RSTile center = object.getPosition();
		RSTile[] adjacentTiles = new RSTile[] {
				new RSTile(center.getX() + 1, center.getY(), center.getPlane()),
				new RSTile(center.getX() - 1, center.getY(), center.getPlane()),
				new RSTile(center.getX(), center.getY() + 1, center.getPlane()),
				new RSTile(center.getX(), center.getY() - 1, center.getPlane())
		};
		
		List<RSPlayer> t = new ArrayList<>();
		RSPlayer[] players = Players.getAll();
		for (RSTile tile : adjacentTiles) {
			for (RSPlayer player : players) {
				if ( player.equals(Player.getRSPlayer()) && !includeUs)
					continue;
				
				if ( player.getPosition().equals(tile) )
					t.add(player);
			}
		}
		
		return t.toArray(new RSPlayer[t.size()]);
	}
	
	public static boolean isPlayerAdjacent(RSObject object, RSPlayer player) {
		boolean contains = false;
		RSPlayer[] adjacent = ObjectUtil.getAdjacentPlayers(object, true);
		for (RSPlayer t : adjacent) {
			if ( t.equals(player) ) {
				contains = true;
				break;
			}
		}
		
		return contains;
	}

	/**
	 * Returns the closest object with a matching object name to the player. See {@link #get(ObjectNames, RSTile, int)}.
	 * Additionally, all objects returned MUST be reachable from {@link PathFinding#canReach()}.
	 * @return
	 */
	public static RSObject get(ObjectNames obj) {
		return get(obj, 32);
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
	 * Returns whether an npc has a specific right-click action
	 * @param npc
	 * @param option
	 * @return
	 */
	public static boolean hasAction(RSObject object, String option) {
		RSObjectDefinition definition = object.getDefinition();
		if ( definition == null )
			return false;
		
		String[] actions = definition.getActions();
		for (int i = 0; i < actions.length; i++) {
			String a = actions[i];
			if ( a.equalsIgnoreCase(option) )
				return true;
		}
		
		return false;
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
	public static boolean interactWithObject(ObjectNames object, String click) {
		RSObject obj = scripts.util.ObjectUtil.get(object, 10);
		if (obj == null)
			return false;

		// Walk to object
		if (!PathFinding.canReach(obj, false))
			AIOWalk.walkTo(obj.getPosition());
		
		// Rotate to object if needed
		if ( !obj.isOnScreen() || AntiBan.randomChance(5))
			Camera.turnToTile(obj);

		// Try to click
		int tries = 0;
		while (!obj.click(new String[] { click }) && tries < 8) {
			AntiBan.idle(83, 156);
			tries++;
		}
		
		// Oof we couldnt click
		if ( tries >= 8 )
			return false;
		
		// Wait until player is finished doing whatever it needed to do after clicking
		General.sleep(1000L);
		long timeout = System.currentTimeMillis() + 10000;
		while ((Player.isMoving()) || (Player.getAnimation() != -1)) {
			General.sleep(500L);
			
			if ( System.currentTimeMillis() > timeout )
				break;
		}
		
		// Sucessfully clicked 
		return true;
	}
}
