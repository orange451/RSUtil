package scripts.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSPlayer;
import org.tribot.api2007.types.RSTile;

public class Navigation {

	/**
	 * Walks the current Runescape player to a randomized point within the location.
	 * <br><br>
	 * Uses a custom condition. If true the walking task will complete.
	 */
	public static void walkTo( Locations location, Condition condition ) {
		RSTile t   = Player.getPosition();
		RSPlayer p = Player.getRSPlayer();
		boolean playerUnderGround = Locations.isUnderGround(t);
		int playerFloor = t.getPlane();

		// If we're here ignore
		if ( location.contains( t ) )
			return;

		boolean locUnderGround = Locations.isUnderGround( location );
		int locFloor = location.getFloor();

		// Do a simple walk-to if both locations are on ground-level.
		boolean canSimpleWalk = false;
		if ( !locUnderGround && locFloor == 0 ) {
			if ( !playerUnderGround && playerFloor == 0 ) {
				canSimpleWalk = true;
				WebWalking.walkTo( location.getRandomizedCenter( 5 ), condition, 100L);
			}
		}

		// We gotta do some magic stuff otherwise
		//EzWalk.plugin.println(canSimpleWalk);
		if ( !canSimpleWalk ) {
			// Get to ground level
			General.println("Getting to ground level");
			while ( Player.getPosition().getPlane() != 0 || Locations.isUnderGround(Player.getPosition()) ) {
				CLIMB_STAIRS( Locations.isUnderGround(Player.getPosition()) );
			}

			// If dest is on ground level, walk to it!
			if ( locFloor == 0 && !locUnderGround ) {
				General.println("Walking to dest: " + location.toString());
				WebWalking.walkTo( location.getRandomizedCenter( 5 ), condition, 100L);
			} else {
				// We need to get to the closest location on ground level
				Locations closestLoc = GET_GROUND_LOC( location, 0 );
				General.println("Finding closest location...");
				if ( closestLoc != null ) {
					General.println("Closest location: " + closestLoc.toString());
					while ( !closestLoc.contains(Player.getPosition()) ) {
						WebWalking.walkTo( closestLoc.getRandomizedCenter( 5 ) );
					}
				} else {
					General.println("No closest location");
				}

				// Take stairs
				General.println( "Climbing stairs: " + Player.getPosition().getPlane() + " / " + location.getFloor());
				while ( Player.getPosition().getPlane() != location.getFloor() || locUnderGround != Locations.isUnderGround(Player.getPosition()) ) {
					boolean up = (location.getFloor() > Player.getPosition().getPlane()) || (Locations.isUnderGround(Player.getPosition()) && !locUnderGround );
					CLIMB_STAIRS( up );
				}

				// Walk to dest
				General.println("Walking to dest: " + location.toString());
				if ( !location.contains( Player.getPosition() ) ) {
					WebWalking.walkTo( location.getRandomizedCenter( 5 ), condition, 100L);
				}
			}
		}
	}

	private static Locations GET_GROUND_LOC(Locations location, int desiredFloor) {

		if ( Locations.isUnderGround( location ) ) {
			return Locations.getGroundFloorLocation( location );
		}

		Locations[] locs = Locations.values();
		RSTile c = location.getCenter();

		Locations closest = null;
		int dist = 9999;
		for (int i = 0; i < locs.length; i++) {
			Locations locationChecking = locs[i];
			//if ( l.getFloor() == desiredFloor && (l.contains( c ) || l.getBounds().intersects(location.getBounds()) || l.getBounds().contains(location.getBounds()) )) {
			if ( locationChecking.getFloor() == desiredFloor ) {
				int d = locationChecking.getCenter().distanceTo( c );
				//EzWalk.plugin.println("Dist: " + d + "  /  " + l.toString() );
				if ( d < dist ) {
					dist = d;
					closest = locationChecking;
				}
			}
			//}
		}

		return closest;
	}

	private static void CLIMB_STAIRS( boolean up ) {
		String option = "Climb-down";
		if ( up )
			option = "Climb-up";

		ArrayList<RSObject> stairs = GET_STAIRS();
		RSObject myStair = null;
		for (int i = 0; i < stairs.size(); i++) {
			final RSObject stair = stairs.get(i);
			if ( stair.getPosition().getPlane() != Player.getPosition().getPlane() )
				continue;

			boolean clicked = false;
			/*for ( int a = 0; a < 2 + (int)(Math.random() * 2); a++ ) {
				if ( stair.click(option) ) {
					myStair = stair;
					clicked = true;
					break;
				}
				SLEEP( 75 + (int)(Math.random() * 500) );
			}

			if ( clicked ) {
				SLEEP( 3000 + (int)(Math.random() * 3000) );
				break;
			}*/
			myStair = stair;
			break;
		}

		// Click the stair
		if ( myStair != null ) {
			RSTile sloc = myStair.getPosition();
			RSTile ploc = Player.getPosition();
			final RSTile tloc = new RSTile( (sloc.getX() + ploc.getX()) / 2, (sloc.getY() + ploc.getY()) / 2, sloc.getPlane() );
			//EzWalk.plugin.println("Found stair: " + sloc.distanceTo(ploc) + " distance");
			if ( myStair.getPosition().distanceTo( Player.getPosition()) > 2 ) {
				WebWalking.walkTo(tloc, new Condition() {

					@Override
					public boolean active() {
						return tloc.distanceTo( Player.getPosition()) <= 2;
					}

				}, 100L);
			}
			myStair.click(option, "Open");
		}

		SLEEP( 1000 + (int)(Math.random() * 1000) );
	}

	private static void SLEEP( long milli ) {
		try {
			Thread.sleep(milli);
		} catch (InterruptedException e) {
			//
		}
	}

	private static ArrayList<RSObject> GET_STAIRS() {
		ArrayList<RSObject> ret = new ArrayList<RSObject>();
		RSObject[] objects = Objects.getAll(100);

		for (int i = 0; i < objects.length; i++) {
			RSObject o = objects[i];
			if ( InteractiveObjects.isA( o, InteractiveObjects.STAIRCASE ) || InteractiveObjects.isA( o, InteractiveObjects.LADDER ) ) {
				ret.add(o);
			}
		}

		Collections.sort(ret, new Comparator<RSObject>() {
			@Override public int compare(RSObject p1, RSObject p2) {
				return p1.getPosition().distanceTo(Player.getPosition()) - p2.getPosition().distanceTo(Player.getPosition());
			}

		});

		return ret;
	}

	/**
	 * Walks the current Runescape player to a randomized point within the location.
	 * <br><br>
	 * If (stopWhenInside) the player will stop trying to walk when he is inside the location.
	 */
	public static void walkTo( final Locations location, final boolean stopWhenInside ) {
		walkTo( location, new Condition() {

			@Override
			public boolean active() {
				return location.contains(Player.getRSPlayer()) && stopWhenInside;
			}

		});
	}
}
