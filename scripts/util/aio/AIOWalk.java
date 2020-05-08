package scripts.util.aio;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Game;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Projection;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.util.DPathNavigator;

import scripts.dax_api.api_lib.DaxWalker;
import scripts.dax_api.walker.utils.AccurateMouse;
import scripts.dax_api.walker_engine.WalkerEngine;
import scripts.dax_api.walker_engine.WalkingCondition;
import scripts.util.NPCDialogue;
import scripts.util.NPCUtil;
import scripts.util.ObjectUtil;
import scripts.util.PlayerUtil;
import scripts.util.misc.AntiBan;
import scripts.util.names.Banks;
import scripts.util.names.ItemIds;
import scripts.util.names.Locations;
import scripts.util.names.NPCNames;
import scripts.util.names.ObjectNames;
import scripts.util.task.BotTask;
import scripts.util.task.BotTaskWalk;
import scripts.util.task.TaskScript;

@SuppressWarnings("deprecation")
public class AIOWalk {

	private static DPathNavigator nav;
	
	public static RSTile[] debug_path;
	
	public static RSTile debug_path_destination;
	
	public static boolean IS_DAX_WALKING;
	
	static {
		nav = new DPathNavigator();
		nav.setAcceptAdjacent(false);
		nav.setStoppingConditionCheckDelay(100L);
		nav.setMaxDistance(32);
		nav.setStoppingCondition(new Condition() {

			@Override
			public boolean active() {
				if ( PlayerUtil.isInDanger() )
					return true;
				if ( IS_DAX_WALKING )
					return true;
				AntiBan.idle(AntiBan.generateAFKTime(4000.0F));
				
				RSTile currentTile = Player.getRSPlayer().getPosition();
				if ( currentTile.equals(lastTile) )
					ticksNotMoved++;
				if ( ticksNotMoved > 8 )
					return true;
				
				lastTile = currentTile;
				return Player.getPosition().distanceTo(debug_path_destination)<8 && PathFinding.canReach(debug_path_destination.getPosition(), false);
			}
		});
	}
	
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
			return false;
		
		if ( location.contains(Player.getPosition()) )
			return true;
		
		return walkTo(location.getRandomizedCenter(8), shouldRunTo, status);
	}
	
	/**
	 * Walks the player to the specified tile.
	 */
	public static boolean walkTo(RSTile tile) {
		return walkTo(tile, false);
	}
	
	/**
	 * Walks the player to the specified tile. Runs if specified.
	 */
	public static boolean walkTo(RSTile tile, boolean runTo ) {
		return walkTo(tile, runTo, new AIOStatus());
	}

	/**
	 * Walks the player to the specified tile. Runs if specified.
	 */
	public static boolean walkTo(RSTile tile, boolean runTo, AIOStatus status) {
		if (tile == null)
			return true;
		
		while(Banking.isBankScreenOpen()) {
			Banking.close();
			General.sleep(2000,3000);
		}
		
		// Try to DPATH FIRST?
		boolean daxWalk = true;
		if ( tile.distanceTo(Player.getPosition()) < 24 /*&& tile.getPlane() == Player.getPosition().getPlane()*/ ) {
			General.println("Attempting DPath navigation...");
			if ( walkToLegacyInternal(tile) && (Player.getPosition().distanceTo(tile) <= 2 && tile.getPlane() == Player.getPosition().getPlane()) ) {
				daxWalk = false;
			}
		}
		
		if ( daxWalk ) {
			IS_DAX_WALKING = true;
			Locations location = Locations.get(tile);
			General.println("Walking to location: " + ((location == null)?tile:location.getName()));
			
			BotTaskWalk task = new BotTaskWalk(tile, runTo) {
				
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
			while( !task.isTaskComplete() ) { // Forces task to run
				General.println("Failed");
				status.setType(StatusType.FAILED);
				IS_DAX_WALKING = false;
				return false;
			}
			General.sleep(250);
			
			General.println("Finished dax walk");
			IS_DAX_WALKING = false;
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
		
		return walkTo(Banks.getNearestBank().getLocation());
		
		// Generate walk to bank task
		/*BotTaskWalkToBank walkToBank = new BotTaskWalkToBank() {
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
			General.sleep(500);
			tries++;
			if ( tries > 20 ) {
				status.setType(StatusType.FAILED);
				return false;
			}
		}
		
		return true;*/
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
		if(location != null)
			walkTo(location);

		// Find NPC
		RSNPC npc = name == null ? null : NPCUtil.getFirstNPC(name);
		if (npc == null) {
			return false;
		}
		
		// In dialog, stop!
		if ( NPCDialogue.isInConversation() )
			return false;

		// Walk to the NPC
		if ( !PathFinding.canReach(npc.getPosition(), false) || npc.getPosition().distanceTo(Player.getPosition()) > 2 )
			AIOWalk.walkTo(npc.getPosition());

		// In dialog, stop!
		if ( NPCDialogue.isInConversation() )
			return false;
		
		// Look at NPC if we cant
		if ( !npc.isOnScreen() )
			Camera.turnToTile(npc);
		
		// In dialog, stop!
		if ( NPCDialogue.isInConversation() )
			return false;
		
		// If we still cant do it, use dax!
		if ( !PathFinding.canReach(npc.getPosition(), false) )
			DaxWalker.walkTo(npc);
		
		// In dialog, stop!
		if ( NPCDialogue.isInConversation() )
			return false;
		
		// If we STILL cant reach.. fuck
		if ( !PathFinding.canReach(npc.getPosition(), false) )
			return false;
		
		// In dialog, stop!
		if ( NPCDialogue.isInConversation() )
			return false;
		
		// Wait a sec
		AntiBan.sleep(1000, 200);

		// In dialog, stop!
		if ( NPCDialogue.isInConversation() )
			return false;
		
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
		if (Player.isMoving())
			return false;
		
		// Find item on ground
		RSGroundItem[] bs = GroundItems.findNearest(item.getIds());

		// Dax walk to location
		if ( location != null && bs.length == 0 ) {
			AIOWalk.walkTo(location, false);
			
			// Fallback DPATH
			if ( location != null && !location.contains(Player.getPosition()) )
				AIOWalk.walkTo(location.getCenter());
	
			// Find item on ground
			bs = GroundItems.findNearest(item.getIds());
			if ((bs == null) || (bs.length == 0)) {
				General.println("Could not find item: " + item);
				return false;
			}
		}

		// Turn to item
		AIOWalk.walkTo(bs[0].getPosition());
		if ( Player.getPosition().distanceTo(bs[0]) > 4 && PathFinding.canReach(Player.getPosition(), bs[0], true) ) {
			AccurateMouse.clickMinimap(bs[0]);
			General.sleep(500,2000);
		}
		if ( !bs[0].isOnScreen() )
			Camera.turnToTile(bs[0]);

		// Click it
		int timeout = 0;
		int startAmount = PlayerUtil.getAmountItemsInInventory(item);
		while ((!bs[0].click(click)) && (timeout < 8)) {
			
			timeout++;
			AntiBan.idle(134, 213);
			
			bs = GroundItems.findNearest(item.getIds());
			if ( bs.length == 0 )
				break;
			
			int currentAmount = PlayerUtil.getAmountItemsInInventory(item);
			if ( currentAmount > startAmount )
				break;
		}

		// Oof timeout
		if (timeout >= 8) {
			General.sleep(1000);
			return false;
		}

		// Sleep
		AntiBan.sleep(1000, 250);
		while ((Player.isMoving()) || (Player.getAnimation() != -1)) {
			AntiBan.sleep(500, 200);
		}
		AntiBan.sleep(500, 200);
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
	public static boolean walkToLocationForObject(Locations location, ObjectNames object, String action) {
		if (Player.isMoving())
			return false;

		if ( location != null )
			walkTo(location);

		return ObjectUtil.interactWithObject(object, action);
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
		nav.traverse(location.getRandomizedPosition());
		AntiBan.sleep(700, 250);
	}

	private static RSTile lastTile;
	private static int ticksNotMoved = 0;
	protected static boolean walkToLegacyInternal(final RSTile tile) {
		if ( tile == null )
			return false;
		
		debug_path_destination = tile;
		
		ticksNotMoved = 0;
		lastTile = Player.getRSPlayer().getPosition();
		WalkingCondition condition = new WalkingCondition() {
			@Override
			public State action() {
				if ( PlayerUtil.isInDanger() ) {
					General.println("In danger. Exiting pathing");
					return State.EXIT_OUT_WALKER_FAIL;
				}
				
				if ( PathFinding.canReach(tile.getPosition(), true) && Player.getPosition().distanceTo(tile)<=3 && tile.getPlane() == Player.getPosition().getPlane()) {
					General.println("Success");
					return State.EXIT_OUT_WALKER_SUCCESS;
				}
				
				AntiBan.idle(AntiBan.generateAFKTime(4000.0F));
				
				RSTile currentTile = Player.getRSPlayer().getPosition();
				General.println(currentTile + " / " + lastTile);
				if ( currentTile.equals(lastTile) )
					ticksNotMoved++;
				else
					ticksNotMoved = 0;
				
				if ( ticksNotMoved > 6 ) {
					General.println("Stuck.. Rotating camera");
					AntiBan.rotateCameraRandom();
				}
				
				if ( ticksNotMoved > 10 ) {
					General.println("Not moving...");
					return State.EXIT_OUT_WALKER_FAIL;
				}
				
				return State.CONTINUE_WALKER;
			}
		};
		
		// Hybrid DPath/Dax walker
		RSTile[] path = nav.findPath(tile.getPosition());
		debug_path = path;
		if ( path != null && path.length > 0 ) {
			General.println("Walking local path (" + path.length + ") tile(s)");
			
			// Walk result
			WalkerEngine.getInstance().walkPath(new ArrayList<>(Arrays.asList(path)), condition);
			
			// Look at tile
			if (!tile.isOnScreen())
				Camera.turnToTile(tile);
			
			// Click minimap if we're close
			if ( PathFinding.canReach(tile, true) && Player.getPosition().distanceTo(tile)>4 )
				AccurateMouse.clickMinimap(tile.getPosition());
			
			// Wait until we stop moving
			General.sleep(2000);
			while(Player.isMoving() && Player.getPosition().distanceTo(tile)>1)
				General.sleep(500);
			
			int dist = nav.findPath(tile.getPosition()).length;
			if ( dist <= 4 ) {
				General.println("Success");
				return true;
			}
			
			General.println("Couldn't local-walk");
		}
		
		// Wait until we stop moving
		General.sleep(250);
		while(Player.isMoving() && Player.getPosition().distanceTo(tile)>3)
			General.sleep(500);
		
		if ( Player.getPosition().distanceTo(tile) <= 1 && tile.getPlane() == Player.getPosition().getPlane() ) {
			General.println("We made it!");
			return true;
		}
		
		// Return if we actually made it
		General.println("Couldn't do it :(");
		return false;
	}
	
	public static void debugDraw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if (debug_path != null && debug_path.length > 0) {
			g2d.setColor(Color.WHITE);
			for (RSTile tile : debug_path) {
				if (tile.isOnScreen()) {
					g2d.draw(Projection.getTileBoundsPoly(tile, 0));
				}
			}
		}
		
		if ( debug_path_destination != null ) {
			g2d.setColor(new Color(0.8f, 0.1f, 0.1f, 0.5f));
			g2d.fill(Projection.getTileBoundsPoly(debug_path_destination, 0));
			g2d.setColor(Color.RED);
			g2d.draw(Projection.getTileBoundsPoly(debug_path_destination, 0));
		}
	}
	
	protected static WalkingCondition getWalkingCondition(final RSTile tile) {
		WalkingCondition c = new WalkingCondition() {
			public WalkingCondition.State action() {
				if (!TaskScript.isStarted()) {
					return WalkingCondition.State.EXIT_OUT_WALKER_SUCCESS;
				}

				if (AntiBan.randomChance(32))
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
	
	protected static Condition getWalkingConditionAlt(final RSTile tile) {
		Condition c = new Condition() {
			@Override
			public boolean active() {
				if (!TaskScript.isStarted()) {
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
