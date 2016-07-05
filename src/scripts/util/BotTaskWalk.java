package scripts.util;

import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;

public abstract class BotTaskWalk extends BotTask {
	protected Locations walkTo;
	protected boolean forceComplete;

	public BotTaskWalk( Locations location, boolean runTo ) {
		this.walkTo = location;
		WebWalking.setUseRun( runTo );
	}

	public void setForceCompleted( boolean complete ) {
		this.forceComplete = complete;
	}

	@Override
	public String getTaskName() {
		return "Walking to " + walkTo.toString();
	}

	public abstract BotTask getNextTask();

	@Override
	public boolean isTaskComplete() {
		if ( !walkTo.contains(Player.getRSPlayer()) ) {
			Condition c = new Condition() {

				@Override
				public boolean active() {
					AntiBan.timedActions();
					return walkTo.contains(Player.getRSPlayer()) || forceComplete;
				}
			};
			Navigation.walkTo( this.walkTo, c );
			return false;
		}
		return true;
	}
}
