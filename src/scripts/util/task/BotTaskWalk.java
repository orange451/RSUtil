package scripts.util.task;

import java.util.ArrayList;
import java.util.Arrays;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.util.DPathNavigator;

import scripts.dax_api.api_lib.DaxWalker;
import scripts.dax_api.walker_engine.WalkerEngine;
import scripts.dax_api.walker_engine.WalkingCondition;
import scripts.util.PlayerUtil;
import scripts.util.aio.AIOWalk;
import scripts.util.misc.AntiBan;
import scripts.util.names.Locations;
import scripts.util.player.Navigation;

@SuppressWarnings("deprecation")
public abstract class BotTaskWalk extends BotTask {
	protected static float radius = 6.0F;

	// Walking variables
	protected RSTile walkTo;
	protected int attempts;
	protected boolean shouldRun;
	
	// Timeout variables
	private RSTile lastPosition;
	boolean timedOut = false;
	long startTimedOut = -1;
	final long MAX_TIME_OUT = 1000;

	public BotTaskWalk(Locations location, boolean runTo) {
		this(location.getRandomizedCenter(radius), runTo);
		
		if ( location.contains(Player.getRSPlayer()) )
			this.forceComplete();
	}

	public BotTaskWalk(RSTile location, boolean runTo) {
		this.walkTo = location;
		this.shouldRun = runTo;

		PlayerUtil.setRun(runTo);
	}

	public String getTaskName() {
		String nearestLoc = null;
		if (this.walkTo != null) {
			Locations loc = Locations.get(this.walkTo);
			if (loc != null) {
				nearestLoc = loc.getName();
			}
		}
		return "Walk: " + (nearestLoc != null ? nearestLoc : this.walkTo.toString());
	}

	public abstract BotTask getNextTask();

	public boolean isTaskComplete() {
		if ((this.walkTo == null) || (this.forceComplete)) {
			return true;
		}
		if (Player.isMoving()) {
			return false;
		}
		
		// Break wait tasks if we're attacked by both NPCS and Players
		PlayerUtil.setIgnoreAttacking(false, false);

		//General.println(this.walkTo.distanceTo(Player.getRSPlayer()) + "   /   " + radius);
		if ((this.walkTo.distanceTo(Player.getRSPlayer()) > radius) || (this.walkTo.getPlane() != Player.getPosition().getPlane())) {
			
			// Compute final tile
			RSTile finalTileTemp = this.walkTo;
			if (this.attempts > 3) {
				Locations loc = Locations.get(finalTileTemp);
				if (loc != null) {
					finalTileTemp = loc.getRandomizedCenter(radius);
				} else {
					int x = finalTileTemp.getX();
					int y = finalTileTemp.getY();
					x = (int)(x + (Math.random() - Math.random()) * radius);
					y = (int)(y + (Math.random() - Math.random()) * radius);

					finalTileTemp = new RSTile(x, y, finalTileTemp.getPlane());
				}
			}
			RSTile finalTile = finalTileTemp;
			
			// Fallback method!
			if (this.attempts > 30) {
				Condition c = new Condition() {
					@Override
					public boolean active() {
						// Force quit
						if (forceComplete)
							return true;

						// Idle while walking.
						AntiBan.idle(Game.isRunOn() ? (100+AntiBan.generateResponseTime(400)):(100+AntiBan.generateResponseTime(1000)), new Condition() {
							@Override
							public boolean active() {
								return (forceComplete) || (finalTile.distanceTo(Player.getRSPlayer()) <= BotTaskWalk.radius);
							}
						});
						
						// Sometimes AFK
						AntiBan.afk(AntiBan.generateResponseTime( Game.isRunOn()?1000:5000 ));

						// Handle run logic
						runLogic();
						
						// Exit condition
						return finalTile.distanceTo(Player.getRSPlayer()) <= BotTaskWalk.radius ? true : false;
					}
				};
				Navigation.walkTo(finalTile, c);
				if ( finalTile.distanceTo(Player.getRSPlayer()) < 6)
					return true;
			}

			// Reset timeout variables
			lastPosition = null;
			timedOut = false;
			startTimedOut = -1;

			// Use DaxWalker first
			WalkingCondition c = new WalkingCondition() {

				@Override
				public WalkingCondition.State action() {
					// Force quit
					if (forceComplete || timedOut)
						return WalkingCondition.State.EXIT_OUT_WALKER_FAIL;
					
					// Handle timeout
					RSTile currentPosition = Player.getPosition();
					if ( currentPosition.equals(lastPosition) && startTimedOut == -1 ) {
						startTimedOut = System.currentTimeMillis();
					} else {
						startTimedOut = -1;
					}
					if ( startTimedOut > 0 && (System.currentTimeMillis()-startTimedOut > MAX_TIME_OUT)) {
						General.println("Timed out");
						timedOut = true;
					}
					lastPosition = currentPosition;

					// Idle while walking.
					AntiBan.idle(Game.isRunOn() ? (100+AntiBan.generateResponseTime(400)):(100+AntiBan.generateResponseTime(1000)), new Condition() {
						@Override
						public boolean active() {
							return (forceComplete) || (finalTile.distanceTo(Player.getRSPlayer()) <= BotTaskWalk.radius);
						}
					});
					
					// Sometimes AFK.
					AntiBan.afk(AntiBan.generateAFKTime(Game.isRunOn()?3000:15000));

					// Handle run logic
					runLogic();

					// Exit condition
					WalkingCondition.State ret = finalTile.distanceTo(Player.getRSPlayer()) <= BotTaskWalk.radius ? WalkingCondition.State.EXIT_OUT_WALKER_SUCCESS : WalkingCondition.State.CONTINUE_WALKER;
					return ret;
				}
				
			};
			
			General.println("Attempting to daxwalk " + attempts);
			if ( !DaxWalker.walkTo(finalTile, c)) {
				General.println("Failed... Attempting to legacy walk...");
				/*DPathNavigator nav = new DPathNavigator();
				nav.setAcceptAdjacent(false);
				RSTile[] path = nav.findPath(finalTile);
				if ( path != null && path.length < 2 )
					return true;
				
				// Force another local dax walk
				WalkerEngine.getInstance().walkPath(new ArrayList(Arrays.asList(path)));*/
				
				AIOWalk.walkToLegacy(finalTile);
				
				// Also do a DPath nav
				//nav.traverse(finalTile);
			}
			
			// If we're close by, we did it!
			if ( finalTile.distanceTo(Player.getRSPlayer()) < 2)
				return true;
			
			this.attempts += 1;
			return false;
		}
		
		return true;
	}
	
	private void runLogic() {
		if ((PlayerUtil.isInDanger()) || (PlayerUtil.isUnderAttack(true))) {
			PlayerUtil.setRun(true);
			WebWalking.setUseRun(true);
		} else {
			if ( shouldRun ) {
				if (Game.getRunEnergy() > 15.0D + Math.random() * 25.0D) {
					PlayerUtil.setRun(true);
					WebWalking.setUseRun(true);
				}
			} else {
				PlayerUtil.setRun(false);
				WebWalking.setUseRun(false);
			}
		}
	}
}
