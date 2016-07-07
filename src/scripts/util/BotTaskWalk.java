package scripts.util;

import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;

import scripts.util.names.Locations;
import scripts.util.player.Navigation;

public abstract class BotTaskWalk extends BotTask {
	protected Locations walkTo;
	protected boolean forceComplete;
	protected boolean important;

	public BotTaskWalk( Locations location, boolean runTo ) {
		this.walkTo = location;
		WebWalking.setUseRun( runTo );
	}

	public void setForceCompleted( boolean complete ) {
		this.forceComplete = complete;
	}

	@Override
	public String getTaskName() {
		return "Walking to " + (walkTo != null?walkTo.toString():"null");
	}

	public abstract BotTask getNextTask();

	@Override
	public boolean isTaskComplete() {
		if ( walkTo == null )
			return true;

		if ( !walkTo.contains(Player.getRSPlayer()) ) {
			Condition c = new Condition() {

				@Override
				public boolean active() {
					if ( !important ) {
						if ( AntiBan.randomChance(4) )
							AntiBan.rotateCameraRandom();
						AntiBan.timedActions();
						AntiBan.afk( 5000 );
					}
					return walkTo.contains(Player.getRSPlayer()) || forceComplete;
				}
			};
			Navigation.walkTo( this.walkTo, c );
			return false;
		}
		return true;
	}
}
