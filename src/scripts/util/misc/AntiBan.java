package scripts.util.misc;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;
import scripts.util.PlayerUtil;

@SuppressWarnings("deprecation")
public final class AntiBan {
	private static final ABCUtil abc = new ABCUtil();
	
	static {
		General.useAntiBanCompliance(true);
		abc.generateTrackers();
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
		if (abc.shouldLeaveGame())
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
		float center = (float)(maxTime * General.randomDouble(0.25D, 0.275D));
		float rightBound = maxTime - center;
		float leftBound = center;
		float powerRight = (float)Math.pow(Math.random(), 6.0D) * 2.0F;
		float powerLeft = (float)Math.pow(Math.random(), 3.0D);
		float randomRight = powerRight * rightBound;
		float randomLeft = powerLeft * leftBound;
		int waitTime = (int)(center + randomRight - randomLeft);

		return waitTime;
	}

	/**
	 * Generates a human-like random afk time based near maxTime.
	 * @param maxTime
	 * @return
	 */
	public static int generateAFKTime(float maxTime) {
		float center = (float)(maxTime * General.randomDouble(0.025D, 0.06D));
		float rightBound = maxTime - center;
		float leftBound = center;
		float powerRight = (float)Math.pow(Math.random(), 6.0D) * 2.0F;
		float powerLeft = (float)Math.pow(Math.random(), 3.0D);
		float randomRight = powerRight * rightBound;
		float randomLeft = powerLeft * leftBound;
		int waitTime = (int)(center + randomRight - randomLeft);

		return waitTime;
	}

	/**
	 * Performs an AFK operation. Sleeps the current thread until the time is met.<br>
	 * While AFK, the camera will not rotate. If you die or are in danger, the AFK is cancelled.
	 * @param maxTime
	 */
	public static void afk(float maxTime) {
		int afkTime = generateResponseTime(maxTime);
		long timeToWait = System.currentTimeMillis() + afkTime;

		afk = true;
		while (System.currentTimeMillis() + 1000L < timeToWait) {
			General.sleep(100L);
			if (PlayerUtil.isDead() || PlayerUtil.isInDanger()) {
				afk = false;
				break;
			}
		}
		afk = false;
	}

	/**
	 * Idles for a max of maxTime milliseconds. See {@link #idle(long, Condition)}.
	 * @param maxTime
	 */
	public static void idle(long maxTime) {
		idle(maxTime, null);
	}

	/**
	 * Idles for maxTime milliseconds. Idling will sleep the current thread, perform antiban actions, and randomly rotate the camera.<br>
	 * If the player NEEDS to eat or is in danger, the idle is immediately cancelled.<br>
	 * If the condition is met (active), the idle is also immediately cancelled.
	 * @param timeToWait
	 * @param condition
	 */
	public static void idle(long timeToWait, Condition condition) {
		timeToWait = System.currentTimeMillis() + timeToWait;
		
		while (System.currentTimeMillis() < timeToWait) {

			if ((condition != null) && (condition.active())) {
				break;
			}

			General.sleep(100L);
			timedActions();

			if (randomChance(128)) {
				rotateCameraRandom();
			}

			if ((PlayerUtil.needsToEat()) || (PlayerUtil.isInDanger())) {
				break;
			}
		}
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
	 * Performs a sleep where each time is based around the average time. This provides more human-like results.
	 * @param average
	 * @param deviation
	 */
	public static void sleep(int average, int deviation) {
		int time = average+generateResponseTime(deviation)-generateResponseTime(deviation);
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
		return (int)(Math.random() * i) == i / 2;
	}
}
