package scripts.util;

import org.tribot.api.General;
import org.tribot.api.util.abc.ABCUtil;

public final class AntiBan {

    private static final ABCUtil abc;

    /**
     * Static initialization block.
     * By default, the use of general anti-ban compliance is set to be true.
     */
    static {
        abc = new ABCUtil();
        General.useAntiBanCompliance(true);
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
	public static int generateAFKTime() {
		return (int) (Math.pow( Math.random() * Math.random(), 8 ) * 60000);
	}

	/**
	 * Simulates going AFK
	 */
	public static void afk() {
		int afkTime = generateAFKTime();
		long timeToWait = System.currentTimeMillis() + afkTime;

		while ( System.currentTimeMillis() + 2000 < timeToWait ) {
			try {
				Thread.sleep(100L);
			} catch (InterruptedException e) {
				//
			}
			timedActions();
		}
	}
}
