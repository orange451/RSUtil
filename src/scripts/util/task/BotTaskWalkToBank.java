package scripts.util.task;

import org.tribot.api2007.Combat;
import scripts.util.names.Banks;

public abstract class BotTaskWalkToBank extends BotTaskWalk {
	
	protected int attempts;
	protected boolean shouldRun;
	
	public BotTaskWalkToBank(Banks bank) {
		super( bank.getLocation().getRandomizedCenter(6), Combat.getWildernessLevel() > 0 );
	}
	
	public BotTaskWalkToBank() {
		this(Banks.getNearestBank());
	}
}
