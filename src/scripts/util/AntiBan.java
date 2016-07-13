package scripts.util;

import org.tribot.api.General;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSTile;

public final class AntiBan {

    private static final ABCUtil abc;
    private static boolean afk;

    /**
     * Static initialization block.
     * By default, the use of general anti-ban compliance is set to be true.
     */
    static {
        abc = new ABCUtil();
        General.useAntiBanCompliance(true);
        abc.generateTrackers();
    }

    /**
     * Checks all of the actions that are performed with the time tracker; if any are ready, they will be performed.
     */
    public static void timedActions() {
    	if (abc.shouldCheckTabs())
    		abc.checkTabs();

    	if (abc.shouldCheckXP())
    		abc.checkXP();

    	if (abc.shouldExamineEntity())
    		abc.examineEntity();

    	if (abc.shouldMoveMouse())
    		abc.moveMouse();

    	if (abc.shouldPickupMouse())
    		abc.pickupMouse();

    	if (abc.shouldRightClick())
    		abc.rightClick();

    	if (abc.shouldRotateCamera())
    		abc.rotateCamera();

    	if (abc.shouldLeaveGame())
    		abc.leaveGame();
    }

    /**
     * Returns a number in milliseconds that represents how long the user is away from the window. Max is 1 minute.
     * @return
     */
	public static int generateAFKTime( float maxTime ) {
		return (int) (Math.pow( Math.random() * Math.random(), 16 ) * maxTime);
	}

	/**
	 * Simulates going AFK for up to 30 seconds.
	 */
	public static void afk( float maxTime ) {
		int afkTime = generateAFKTime( maxTime );
		long timeToWait = System.currentTimeMillis() + afkTime;

		afk = true;
		while ( System.currentTimeMillis() + 4000 < timeToWait ) {
			sleep( 100L );
			timedActions();

			// Cancel AFK if we die
			if ( Skills.getCurrentLevel( SKILLS.HITPOINTS ) <= 0 ) {
				break;
			}
		}
		afk = false;
	}

	/**
	 * Waits the script for (maxTime) milliseconds. During this time both timedActions() and rotateCameraRandom() will be performed.
	 * @param maxTime
	 */
	public static void idle( long maxTime ) {
		long timeToWait = System.currentTimeMillis() + maxTime;
		while ( System.currentTimeMillis() < timeToWait ) {
			sleep( 50L );
			timedActions();

			if ( randomChance( 10 ) )
				rotateCameraRandom();
		}
	}

	/**
	 * Rotates the camera to a random tile around the player
	 */
	public static void rotateCameraRandom() {
		RSTile p = Player.getPosition();
		int rx = (int) ((Math.random() - Math.random()) * 8);
		int ry = (int) ((Math.random() - Math.random()) * 8);
		RSTile tile = new RSTile( p.getX() + rx, p.getY() + ry, p.getPlane() );
		Camera.turnToTile(tile);
	}

	/**
	 * Returns whether the antiban is sleeping our script.
	 * @return
	 */
	public static boolean isAFK() {
		return afk;
	}


	/**
	 * Custom sleep function.
	 * @param l
	 */
	public static void sleep(long l) {
		try {
			Thread.sleep(l);
		} catch (InterruptedException e) {
			//
		}
	}

	public static boolean randomChance(int i) {
		return (int)(Math.random() * i) == i/2;
	}
}
