package scripts.util;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api.util.ABCUtil;
import org.tribot.api2007.*;

public final class AntiBan {

    private static final ABCUtil abc;
    private static boolean print_debug;

    /**
     * Static initialization block.
     * By default, the use of general anti-ban compliance is set to be true.
     */
    static {
        abc = new ABCUtil();
        print_debug = false;
        General.useAntiBanCompliance(true);
    }

    /**
     * Checks all of the actions that are performed with the time tracker; if any are ready, they will be performed.
     */
    public static void timedActions() {
        if (System.currentTimeMillis() >= abc.TIME_TRACKER.EXAMINE_OBJECT.next()) {
            abc.TIME_TRACKER.EXAMINE_OBJECT.reset();
            abc.performExamineObject();
          }

          if (System.currentTimeMillis() >= abc.TIME_TRACKER.LEAVE_GAME.next()) {
            abc.TIME_TRACKER.LEAVE_GAME.reset();
            abc.performLeaveGame();
          }

          if (System.currentTimeMillis() >= abc.TIME_TRACKER.PICKUP_MOUSE.next()) {
            abc.TIME_TRACKER.PICKUP_MOUSE.reset();
            abc.performPickupMouse();
          }

          if (System.currentTimeMillis() >= abc.TIME_TRACKER.RANDOM_MOUSE_MOVEMENT.next()) {
            abc.TIME_TRACKER.RANDOM_MOUSE_MOVEMENT.reset();
            abc.performRandomMouseMovement();
          }

          if (System.currentTimeMillis() >= abc.TIME_TRACKER.RANDOM_RIGHT_CLICK.next()) {
            abc.TIME_TRACKER.RANDOM_RIGHT_CLICK.reset();
            abc.performRandomRightClick();
          }
    }
}
