package scripts.util.task;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSTile;

import scripts.dax_api.api_lib.DaxWalker;
import scripts.dax_api.walker_engine.WalkingCondition;
import scripts.util.PlayerUtil;
import scripts.util.misc.AntiBan;
import scripts.util.names.Locations;
import scripts.util.player.Navigation;

@SuppressWarnings("deprecation")
public abstract class BotTaskWalk extends BotTask {
	protected static float radius = 6.0F;

	protected RSTile walkTo;
	protected int attempts;
	protected boolean shouldRun;

	public BotTaskWalk(Locations location, boolean runTo) {
		this(location.getRandomizedCenter(radius), runTo);
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
		return "Walk: " + (this.walkTo != null ? this.walkTo.toString() : nearestLoc != null ? nearestLoc : "null") + " ";
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

		General.println(this.walkTo.distanceTo(Player.getRSPlayer()) + "   /   " + radius);
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
						if (forceComplete) {
							return true;
						}

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
				return true;
			}

			// Use DaxWalker first
			WalkingCondition c = new WalkingCondition() {

				@Override
				public WalkingCondition.State action() {
					// Force quit
					if (forceComplete) {
						return WalkingCondition.State.EXIT_OUT_WALKER_FAIL;
					}

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
			DaxWalker.walkTo(finalTile, c);
			
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
