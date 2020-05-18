package scripts.util.misc;

import java.util.List;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;

import scripts.util.NPCDialogue;
import scripts.util.PlayerUtil;

@SuppressWarnings("deprecation")
public final class AntiBan {
	private static ABCUtil abc;
	
	private static boolean initialized;
	
	private static long bigResponseTime;
	
	private static long fatigue_start = -1;
	
	private static long fatigue_peak = -1;

	public static final long OFFSET_FATIGUE = 256;
	
	public static final long OFFSET_FATIGUE_POWER = 300;
	
	public static final long OFFSET_RESPONSE_TIME = 500;
	
	public static final long OFFSET_AFK_TIME = 1024;
	
	public static final long OFFSET_CHANGE_LOCATION = 1;
	
	static {
		try {
			fatigueReset();
			
			abc = new ABCUtil();
			abc.generateTrackers();
			initialized = true;
			General.useAntiBanCompliance(true);
		} catch(Exception e) {
			System.out.println("Could not generate ABC");
		}
	}

	private static boolean afk;

	public static ABCUtil getABC() {
		return abc;
	}

	public static void timedActions() {
		if (abc.shouldCheckTabs()) {
			abc.checkTabs();
		}
		if (abc.shouldCheckXP()) {
			abc.checkXP();
		}
		if (abc.shouldExamineEntity()) {
			abc.examineEntity();
		}
		if (abc.shouldMoveMouse()) {
			abc.moveMouse();
		}
		if (abc.shouldPickupMouse()) {
			abc.pickupMouse();
		}
		if (abc.shouldRightClick()) {
			abc.rightClick();
		}
		if (abc.shouldRotateCamera()) {
			abc.rotateCamera();
		}
		if (abc.shouldLeaveGame() && randomChance((NPCDialogue.isInConversation() ? 128 : 64) + (int)(getAccountOffset() * 64)))
			abc.leaveGame();
	}

	public static float generateEatAtHP() {
		return getABC().generateEatAtHP();
	}

	/**
	 * Generates a human-like random wait time based near maxTime.
	 * @param maxTime
	 * @return
	 */
	public static int generateResponseTime(float maxTime) {
		float center = (float)(maxTime * (General.randomDouble(0.3D, 0.35D) + (fatigueGet()*0.33)));
		float rightBound = maxTime - center;
		float leftBound = center;
		float powerRight = (float)Math.pow(Math.random(), 6.0D) * 2.0F;
		float powerLeft = (float)Math.pow(Math.random(), 3.0D);
		float randomRight = powerRight * rightBound;
		float randomLeft = powerLeft * leftBound;
		double offset = Math.pow(getAccountOffset(OFFSET_RESPONSE_TIME), 2) * 400;
		int waitTime = (int)(center + randomRight - randomLeft + offset);
		
		if ( waitTime > 15000 )
			bigResponseTime+=4;
		else
			bigResponseTime = Math.max(bigResponseTime-1, 0);
		
		if ( bigResponseTime > 1)
			while(waitTime > 2000)
				waitTime = (int) (waitTime * 0.7);

		return waitTime;
	}

	/**
	 * Generates a human-like random afk time based near maxTime.
	 * @param maxTime
	 * @return
	 */
	public static int generateAFKTime(float maxTime) {
		float center = (float)(maxTime * (General.randomDouble(0.005D, 0.013D) + (fatigueGet() * 0.5d)));
		float rightBound = maxTime - center;
		float leftBound = center;
		float powerRight = (float)Math.pow(Math.random(), 6.0D) * 2.0F;
		float powerLeft = (float)Math.pow(Math.random(), 3.0D);
		float randomRight = powerRight * rightBound;
		float randomLeft = powerLeft * leftBound;
		double offset = Math.pow(getAccountOffset(OFFSET_AFK_TIME), 2) * 4000;
		int waitTime = (int)(center + randomRight - randomLeft + offset);
		
		if ( waitTime > 20000 )
			bigResponseTime+=4;
		else
			bigResponseTime = Math.max(bigResponseTime-1, 0);
		
		if ( bigResponseTime > 1)
			while(waitTime > 10000)
				waitTime = (int) (waitTime * 0.7);

		return waitTime;
	}
	
	/**
	 * Returns an offset (0-1) based on an accounts username
	 * @return
	 */
	public static double getAccountOffset() {
		return getAccountOffset(0);
	}
	
	/**
	 * Returns an offset (0-1) based on an accounts username
	 * @return
	 */
	public static double getAccountOffset(long extraOffset) {
		if ( !initialized || Player.getRSPlayer() == null )
			return 0;
		return getAccountOffset(Player.getRSPlayer().getName(), extraOffset);
	}
	
	/**
	 * Returns an offset (0-1) based on an accounts username
	 * @return
	 */
	public static double getAccountOffset(String name) {
		return getAccountOffset(name, 0);
	}
	
	/**
	 * Returns an offset (0-1) based on an accounts username. Mixes extra offset into the returned offset value.
	 * @return
	 */
	public static double getAccountOffset(String accountName, long extraOffset) {
		if ( accountName == null )
			return 0;
		
		long max = (long) 1.0e6;
		long offset = (long)(extraOffset*1.33e6);
		long overallHash = ((accountName.hashCode()*567)+offset) % max;
		return (overallHash/(double)max) * 0.5 + 0.5;
	}

	/**
	 * Performs an AFK operation. Sleeps the current thread until the time is met.<br>
	 * While AFK, the camera will not rotate. If you die or are in danger, the AFK is cancelled.
	 * @param maxTime
	 */
	public static void afk(float maxTime) {
		double abcReac = Math.min(2, Math.max(0.3, abc.generateReactionTime()/10000d));
		int afkTime = (int) (generateAFKTime(maxTime)*abcReac);
		afkTime = Math.min(60000, afkTime);
		
		long timeToWait = System.currentTimeMillis() + afkTime;

		if ( System.currentTimeMillis() + 1000L < timeToWait ) {
			General.println("AFK for " + afkTime + " ms");
			afk = true;
			while (System.currentTimeMillis() < timeToWait) {
				General.sleep(100L);
				if (PlayerUtil.isDead() || PlayerUtil.isInDanger()) {
					afk = false;
					break;
				}
			}
			afk = false;
		}
		
		// Regenerate trackers
		abc.generateTrackers();
	}

	/**
	 * Idles for idleTime milliseconds. See {@link #idle(long, Condition)}.
	 * @param maxTime
	 */
	public static void idle(long idleTime) {
		idle(idleTime, null);
	}

	/**
	 * Idles for maxTime milliseconds. Idling will sleep the current thread, perform antiban actions, and randomly rotate the camera.<br>
	 * If the player NEEDS to eat or is in danger, the idle is immediately cancelled.<br>
	 * If the condition is met (active), the idle is also immediately cancelled.
	 * @param timeToWait
	 * @param condition
	 */
	public static void idle(long timeToWait, Condition condition) {
		long endTime = System.currentTimeMillis() + timeToWait;
		
		General.println("Generating idle time: " + timeToWait + " ms");
		
		while (System.currentTimeMillis() < endTime) {

			// Break if we're in danger
			if (PlayerUtil.needsToEat() || PlayerUtil.isInDanger() || PlayerUtil.isDead())
				break;
			
			// do the condition
			if ((condition != null) && (condition.active())) {
				break;
			}

			General.sleep(100L);
			
			// Break if we're in danger
			if (PlayerUtil.needsToEat() || PlayerUtil.isInDanger() || PlayerUtil.isDead())
				break;
			
			// Antiban
			timedActions();

			// Camera rotation because we're not a bot i swear
			if (randomChance(250))
				rotateCameraRandom();

			// Break if we're in danger
			if (PlayerUtil.needsToEat() || PlayerUtil.isInDanger() || PlayerUtil.isDead())
				break;
		}
		
		// Regenerate trackers
		abc.generateTrackers();
	}

	/**
	 * Idle randomly between a min and a max time. See {@link #idle(long, Condition)}.
	 * @param min
	 * @param max
	 */
	public static void idle(int min, int max) {
		int time = (int)(min + Math.random() * (max - min));
		idle(time);
	}
	
	/**
	 * Performs a idle where each time is based around the average time. This provides more human-like results. See {@link #idle(long)}.
	 * @param average
	 * @param deviation
	 */
	public static void sleep(int average, int deviation) {
		int time = (int) (average*0.75+generateResponseTime(deviation));
		if ( time < 5 )
			time = 5;
		idle(time);
	}

	/**
	 * Randomly rotate the camera.
	 */
	public static void rotateCameraRandom() {
		RSTile p = Player.getPosition();
		int rx = (int)((Math.random() - Math.random()) * 8.0D);
		int ry = (int)((Math.random() - Math.random()) * 8.0D);
		RSTile tile = new RSTile(p.getX() + rx, p.getY() + ry, p.getPlane());
		Camera.turnToTile(tile);
	}

	/**
	 * Returns whether the antiban system is currently afk.
	 * @return
	 */
	public static boolean isAFK() {
		return afk;
	}

	/**
	 * Performs a random (1 in n) chance. For example:<br><br>
	 * 
	 * if ( randomChance(10) )<br>
	 * 	print("Random 1 in 10 chance!");
	 * @param i
	 * @return
	 */
	public static boolean randomChance(int i) {
		return random(i) == i / 2;
	}
	
	/**
	 * Returns a random number between (0) and (i-1);
	 * @param max
	 * @return
	 */
	public static int random(int i) {
		return (int)(Math.random()*i);
	}
	
	/**
	 * Resets the internal fatigue timers
	 */
	public static void fatigueReset() {
		try {
			long minutes = AntiBan.random((int) (60 + (getAccountOffset(OFFSET_FATIGUE)*30))) + 60;
			
			fatigue_start = System.currentTimeMillis();
			fatigue_peak = System.currentTimeMillis() + (1000 * 60 * minutes);
		} catch(Exception e) {
			//
		}
	}
	
	/**
	 * Returns the ratio of fatigue between 0 and 1.
	 * @return 
	 */
	public static double fatigueGet() {
		if ( fatigue_start == -1 )
			fatigueReset();
		
		// Get linear fatigue 0-1
		long currentTime = System.currentTimeMillis()-BreakHelper.getTotalBreakTime();
		double fatigue = (currentTime-fatigue_start) / (double)(fatigue_peak-fatigue_start);
		if ( fatigue < 0 )
			fatigue = 0;
		if ( fatigue > 1 )
			fatigue = 1;
		
		// Raise to power, but different for all accounts!
		fatigue = Math.pow(fatigue, 1 + getAccountOffset(OFFSET_FATIGUE_POWER) * 0.5);
		
		// Return
		return fatigue;
	}

	/**
	 * Randomly pick an element from a list of elements.
	 */
	public static <T> T choose(@SuppressWarnings("unchecked") T... objects) {
		return objects[AntiBan.random(objects.length)];
	}

	/**
	 * Randomly pick an element from a list of elements.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T choose( List<T> objects ) {
		return (T) choose(objects.toArray());
	}
}
