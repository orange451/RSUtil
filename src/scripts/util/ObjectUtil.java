package scripts.util;

import java.util.ArrayList;
import org.tribot.api.General;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.util.names.ObjectNames;

public class ObjectUtil {
	/**
	 * Returns all RSObjects with 20 tiles of the player with a matching ObjectNames. See {@link #getAll(ObjectNames, int)}.
	 * @param obj
	 * @return
	 */
	public static ArrayList<RSObject> getAll(ObjectNames obj) {
		return getAll(obj, 20);
	}
	
	/**
	 * Returns all RSObjects with a specified distance of the player with a matching ObjectNames.
	 * @param obj
	 * @param distance
	 * @return
	 */
	public static ArrayList<RSObject> getAll(ObjectNames obj, int distance) {
		ArrayList<RSObject> objs = new ArrayList<RSObject>();
		RSObject[] objects = Objects.getAll(distance);
		for (int i = 0; i < objects.length; i++) {
			RSObject o = objects[i];
			int[] ids = obj.getId();
			for (int ii = 0; ii < ids.length; ii++) {
				if (o.getID() == ids[ii]) {
					objs.add(o);
				}
			}
		}

		return objs;
	}

	/**
	 * Returns whether a RSObject is a specific objectname. ObjectNames can have multiple ids, this method will check all of them.
	 * @param o
	 * @param obj
	 * @return
	 */
	public static boolean isA(RSObject o, ObjectNames obj) {
		int[] ids = obj.getId();
		for (int ii = 0; ii < ids.length; ii++) {
			if (o.getID() == ids[ii]) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the closest object with a matching object name to the given position.
	 * @param obj
	 * @param position
	 * @param MAX_DIST
	 * @return
	 */
	public static RSObject get(ObjectNames obj, Positionable position, int MAX_DIST) {
		ArrayList<RSObject> objects = getAll(obj);
		RSObject mine = null;
		int dist = MAX_DIST;
		for (int i = 0; i < objects.size(); i++) {
			RSObject o = (RSObject)objects.get(i);
			int tdist = o.getPosition().distanceTo(position);
			if (tdist < dist) {
				dist = tdist;
				mine = o;
			}
		}

		return mine;
	}

	/**
	 * Returns the closest object with a matching object name to the player. See {@link #get(ObjectNames, RSTile, int)}.
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
}
