package scripts.util.aio;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Game;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.util.DPathNavigator;

import scripts.aio.f2pquester.F2PQuester;
import scripts.dax_api.api_lib.DaxWalker;
import scripts.dax_api.walker.utils.AccurateMouse;
import scripts.dax_api.walker_engine.WalkingCondition;
import scripts.util.NPCUtil;
import scripts.util.ObjectUtil;
import scripts.util.PlayerUtil;
import scripts.util.misc.AntiBan;
import scripts.util.names.ItemIds;
import scripts.util.names.Locations;
import scripts.util.names.NPCNames;
import scripts.util.names.ObjectNames;
import scripts.util.task.BotTask;
import scripts.util.task.BotTaskWalk;
import scripts.util.task.BotTaskWalkToBank;

@SuppressWarnings("deprecation")
public class AIOWalk {

	/**
	 * Walks the player to the specified location.
	 * @param location
	 * @return
	 */
	public static boolean walkTo(Locations location) {
		return walkTo(location, false);
	}

	/**
	 * Walks the player to the specified location. Runs if specified.
	 * @param location
	 * @return
	 */
	public static boolean walkTo(Locations location, boolean shouldRunTo) {
		return walkTo(location, shouldRunTo, new AIOStatus());
	}


	/**
	 * Walks the player to the specified location. Runs if specified.
	 * @param location
	 * @return
	 */
	public static boolean walkTo(Locations location, boolean shouldRunTo, AIOStatus status) {
		if ( location == null )
			return true;
		
		while(Banking.isBankScreenOpen()) {
			Banking.close();
			General.sleep(2000,3000);
		}
		
		BotTaskWalk task = new BotTaskWalk(location, shouldRunTo) {

			@Override
			public BotTask getNextTask() {
				return null;
			}

			@Override
			public void init() {
				General.println("Walking to location: " + location);
			}
		};
				
		// Complete the task
		int tries = 0;
		while( !task.isTaskComplete() ) { // Forces task to run
			General.sleep(1000);
			tries++;
			if ( tries > 20 ) {
				status.setType(StatusType.FAILED);
				return false;
			}
		}
		
		return true;
	}

	/**
	 * Walks to nearest bank.
	 * @return
	 */
	public static boolean walkToNearestBank() {
		return walkToNearestBank(new AIOStatus());
	}
	
	/**
	 * Walks to nearest bank.
	 * @return
	 */
	public static boolean walkToNearestBank(AIOStatus status) {
		if ( Banking.isBankScreenOpen() )
			return true;
		
		status.setStatus("Walking to bank...");
		
		// Generate walk to bank task
		BotTaskWalkToBank walkToBank = new BotTaskWalkToBank() {
			@Override
			public BotTask getNextTask() {
				return null;
			}

			@Override
			public void init() {
				//
			}
		};
		
		// Complete the task
		int tries = 0;
		while( !walkToBank.isTaskComplete() ) { // Forces task to run
			General.sleep(1000);
			tries++;
			if ( tries > 20 ) {
				status.setType(StatusType.FAILED);
				return false;
			}
		}
		
		return true;
	}

	
	/**
	 * Walk to location for a specific NPC. Will click the npc.
	 */
	public static boolean walkToLocationForNPC(Locations location, NPCNames name) {
		return walkToLocationForNPC(location, name, "talk");
	}
	
	/**
	 * Walk to location for a specific NPC. Will click the npc.
	 */
	public static boolean walkToLocationForNPC(Locations location, NPCNames name, String interact) {
		if (Player.isMoving()) {
			return false;
		}

		// Walk to general location
		walkTo(location);

		// Find NPC
		RSNPC npc = NPCUtil.getFirstNPC(name);
		if (npc == null) {
			return false;
		}

		// Walk to the NPC
		if ( !PathFinding.canReach(npc.getPosition(), false) || npc.getPosition().distanceTo(Player.getPosition()) > 2 )
			AIOWalk.walkToLegacy(npc.getPosition());
		Camera.turnToTile(npc);
		
		// If we still cant do it, use dax!
		if ( !PathFinding.canReach(npc.getPosition(), false) )
			DaxWalker.walkTo(npc);
		
		// If we STILL cant reach.. fuck
		if ( !PathFinding.canReach(npc.getPosition(), false) )
			return false;
		
		// Wait a sec
		AntiBan.sleep(1000, 200);

		// Talk to NPC
		if (!npc.click(new String[] { interact }))
			return false;
		
		return true;
	}
	
	/**
	 * Walk to location for item. Will click the item.
	 * @param location
	 * @param item
	 * @return
	 */
	public static boolean walkToLocationForItem(Locations location, ItemIds item) {
		return walkToLocationForItem(location, item, "");
	}
	
	/**
	 * Walk to location for item. Will click the item.
	 * @param location
	 * @param item
	 * @return
	 */
	public static boolean walkToLocationForItem(Locations location, ItemIds item, String click) {
		if (Player.isMoving()) {
			return false;
		}

		AIOWalk.walkTo(location, false);
		
		if ( location != null ) {
			if ( Player.getPosition().distanceTo(location.getCenter()) > 3 )
				new DPathNavigator().traverse(location.getCenter());
		}

		RSGroundItem[] bs = GroundItems.findNearest(item.getIds());
		if ((bs == null) || (bs.length == 0)) {
			return false;
		}

		AIOWalk.walkToLegacy(bs[0].getPosition());
		Camera.turnToTile(bs[0]);

		int timeout = 0;
		int startAmount = PlayerUtil.getAmountItemsInInventory(item);
		while ((!bs[0].click(click)) && (timeout < 8)) {
			timeout++;
			AntiBan.idle(83, 156);
			
			int currentAmount = PlayerUtil.getAmountItemsInInventory(item);
			if ( currentAmount > startAmount )
				break;
		}

		if (timeout >= 8) {
			return false;
		}

		AntiBan.sleep(1000, 250);
		while ((Player.isMoving()) || (Player.getAnimation() != -1)) {
			AntiBan.sleep(500, 200);
		}
		return true;
	}
	
	/**
	 * Walk to location for object. Will click the object.
	 * @param location
	 * @param object
	 * @return
	 */
	public static boolean walkToLocationForObject(Locations location, ObjectNames object) {
		return walkToLocationForObject(location, object, "");
	}

	
	/**
	 * Walk to location for object. Will click the object.
	 * @param location
	 * @param object
	 * @return
	 */
	public static boolean walkToLocationForObject(Locations location, ObjectNames object, String string) {
		if (Player.isMoving()) {
			return false;
		}

		if ( location != null ) {
			walkTo(location);
		}

		return ObjectUtil.interactWithObject(object, string);
	}
	
	/**
	 * Legacy walk-to method. Use {@link AIOWalk#walkTo(Locations)}
	 * @param location
	 */
	@Deprecated
	public static void walkToLegacy(Locations location) {
		walkToLegacyInternal(location);
	}

	
	/**
	 * Legacy walk-to method. Use {@link AIOWalk#walkTo(Locations)}
	 * @param location
	 */
	@Deprecated
	public static boolean walkToLegacy(RSTile location) {
		return walkToLegacyInternal(location);
	}
	
	protected static void walkToLegacyInternal(Locations location) {
		General.println("Attempting to walk to: " + location);
		
		// AIO Walk to location
		if ( !Locations.isNear(location) )
			AIOWalk.walkTo(location);
		
		// If still not near, break out
		if ( !Locations.isNear(location.getCenter()) )
				return;
		
		// DPath Nav the way there
		new DPathNavigator().traverse(location.getRandomizedPosition());
		AntiBan.sleep(700, 250);
	}

	private static RSTile lastTile;
	private static int ticksNotMoved = 0;
	protected static boolean walkToLegacyInternal(final RSTile tile) {
		if ( tile == null )
			return false;
		
		
		ticksNotMoved = 0;
		lastTile = Player.getRSPlayer().getPosition();
		DPathNavigator nav = new DPathNavigator();
		nav.setStoppingConditionCheckDelay(100L);
		nav.setStoppingCondition(new Condition() {

			@Override
			public boolean active() {
				if ( PlayerUtil.isInDanger() )
					return true;
				AntiBan.idle(AntiBan.generateAFKTime(4000.0F));
				
				RSTile currentTile = Player.getRSPlayer().getPosition();
				if ( currentTile.equals(lastTile) )
					ticksNotMoved++;
				if ( ticksNotMoved > 8 )
					return true;
				
				lastTile = currentTile;
				return PathFinding.canReach(tile.getPosition(), false) && Player.getPosition().distanceTo(tile)<8;
			}
		});

		if ( !nav.traverse(tile.getPosition()) ) {
			Walking.blindWalkTo(tile);
		} else {
			AccurateMouse.clickMinimap(tile.getPosition());
		}
		
		// Return if we actually made it
		return PathFinding.canReach(tile.getPosition(), false);
	}
	
	protected static WalkingCondition getWalkingCondition(final RSTile tile) {
		WalkingCondition c = new WalkingCondition() {
			public WalkingCondition.State action() {
				if (F2PQuester.STOPPED) {
					return WalkingCondition.State.EXIT_OUT_WALKER_SUCCESS;
				}

				if (AntiBan.randomChance(23))
					AntiBan.rotateCameraRandom();
				
				AntiBan.timedActions();
				AntiBan.afk(AntiBan.generateAFKTime(Game.isRunOn() ? 1200 : 7000));
				
				if ( PlayerUtil.isUnderAttack(true) || PlayerUtil.isInDanger() )
					PlayerUtil.setRun(true);

				return tile.distanceTo(Player.getRSPlayer()) <= 4 ? WalkingCondition.State.EXIT_OUT_WALKER_SUCCESS : WalkingCondition.State.CONTINUE_WALKER;
			}
		};
		return c;
	}
	
	@SuppressWarnings("deprecation")
	protected static Condition getWalkingConditionAlt(final RSTile tile) {
		Condition c = new Condition() {
			@Override
			public boolean active() {
				if (F2PQuester.STOPPED) {
					return true;
				}
				
				AntiBan.afk(AntiBan.generateAFKTime(Game.isRunOn() ? 500 : 3000));
				
				if ( PlayerUtil.isUnderAttack(true) || PlayerUtil.isInDanger() )
					PlayerUtil.setRun(true);

				return tile.distanceTo(Player.getRSPlayer()) <= 4 ? true : false;
			}
		};
		return c;
	}
}
