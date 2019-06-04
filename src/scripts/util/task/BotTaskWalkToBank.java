package scripts.util.task;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Player;
import scripts.dax_api.api_lib.DaxWalker;
import scripts.util.PlayerUtil;
import scripts.util.names.Banks;

public abstract class BotTaskWalkToBank extends BotTask {
	
	protected int attempts;
	protected boolean shouldRun;
	protected Positionable walkTo;
	
	public BotTaskWalkToBank(Banks bank) {
		walkTo = bank.getLocation().getRandomizedCenter(8);
	}
	
	public BotTaskWalkToBank() {
		this(Banks.getNearestBank());
	}

	public String getTaskName() {
		return "Walking to bank";
	}

	public boolean isTaskComplete() {
		
		// Quit task if interrupted.
		if (this.forceComplete)
			return true;
		
		// We're still moving, dont recheck.
		if (Player.isMoving())
			return false;
		
		// Break wait tasks if we're attacked by both NPCS and Players
		PlayerUtil.setIgnoreAttacking(false, false);

		// Walk to bank
		if ( DaxWalker.walkTo(walkTo) )
			return true;
		
		return false;
	}
}
