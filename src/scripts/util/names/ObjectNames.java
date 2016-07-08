package scripts.util.names;

import java.util.ArrayList;

import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

public enum ObjectNames {
	BANK_BOOTH(7409),
	ORE_NONE(7468),
	ORE_COPPER(7453,7484),
	ORE_TIN(7486, 7485),
	ORE_IRON(7488, 7455),
	STAIRCASE(12536, 12537, 12538, 16672, 16673, 16671, 11796, 17385, 11799, 24074, 24072, 24077, 24078, 24070, 24071, 881, 882, 6435),
	LADDER(2417, 2418, 16683),
	NULL(23849);

	private int[] id;
	private ObjectNames(int... id) {
		this.id = id;
	}

	/**
	 * Returns the list of Runescape ids associated with this Interactive Object type
	 * @return
	 */
	public int[] getId() {
		return id;
	}

	/**
	 * Returns the name of this enum with Title Casing.
	 * <br>
	 * Additionally the _ characters are replaced with a blank space.
	 * @return
	 */
	public String getName() {
	    String[] arr = toString().toLowerCase().replace("_", " ").split(" ");
	    StringBuffer sb = new StringBuffer();

	    for (int i = 0; i < arr.length; i++) {
	        sb.append(Character.toUpperCase(arr[i].charAt(0)))
	            .append(arr[i].substring(1)).append(" ");
	    }
	    return sb.toString().trim();
	}

	/**
	 * Returns all of the nearby RSObjects with a given Interactive Object type
	 * @param obj
	 * @return
	 */
	public static ArrayList<RSObject> getAll(ObjectNames obj) {
		ArrayList<RSObject> objs = new ArrayList<RSObject>();
		RSObject[] objects = Objects.getAll( 20 );
		for (int i = 0; i < objects.length; i++) {
			RSObject o = objects[i];
			int[] ids = obj.getId();
			for (int ii = 0; ii < ids.length; ii++) {
				if ( o.getID() == ids[ii] ) {
					objs.add(o);
				}
			}
		}

		return objs;
	}

	/**
	 * Returns whether or not a RSObject is the specified Interactive Object type
	 * @param o
	 * @param obj
	 * @return
	 */
	public static boolean isA(RSObject o, ObjectNames obj) {
		int[] ids = obj.getId();
		for (int ii = 0; ii < ids.length; ii++) {
			if ( o.getID() == ids[ii] ) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns an RSObject of the specified Interactive Object type.
	 * <br>
	 * The object will be the closest object to (near) and be within (MAX_DIST) tiles.
	 * @param obj
	 * @param near
	 * @param MAX_DIST
	 * @return
	 */
	public static RSObject get(ObjectNames obj, RSTile near, int MAX_DIST) {
		ArrayList<RSObject> objects = ObjectNames.getAll( obj );
		RSObject mine = null;
		int dist = MAX_DIST;
		for (int i = 0; i < objects.size(); i++) {
			RSObject o = objects.get(i);
			int tdist = o.getPosition().distanceTo( near );
			if ( tdist < dist ) {
				dist = tdist;
				mine = o;
			}
		}

		return mine;
	}

	/**
	 * Returns an RSObject of the specified Interactive Object type.
	 * <br>
	 * The object will be the closest object to the Player and be within (MAX_DIST) tiles.
	 * @param obj
	 * @param near
	 * @param MAX_DIST
	 * @return
	 */
	public static RSObject get(ObjectNames obj, int MAX_DIST) {
		return get( obj, Player.getPosition(), MAX_DIST );
	}
}