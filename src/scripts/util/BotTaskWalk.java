package scripts.util;

import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;

import scripts.util.names.Locations;
import scripts.util.player.Navigation;

public abstract class BotTaskWalk extends BotTask {
	protected Locations walkTo;
	protected boolean important;

	public BotTaskWalk( Locations location, boolean runTo ) {
		this.walkTo = location;
		WebWalking.setUseRun( runTo );
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
					if ( forceComplete )
						return true;

					if ( !important )
						Navigation.doWalkingTasks();

					return walkTo.contains(Player.getRSPlayer());
				}
			};
			Navigation.walkTo( this.walkTo, c );
			return false;
		}
		return true;
	}
}
