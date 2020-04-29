package scripts.util.task;

import org.tribot.api.util.abc.preferences.OpenBankPreference;
import org.tribot.api2007.Banking;
import scripts.util.misc.AntiBan;
import scripts.util.task.BotTask;

public abstract class BotTaskOpenBank extends BotTask {
	
	public boolean isTaskComplete() {
		
		// If we're already in the bank...
		if (Banking.isBankScreenOpen()) {
			// Finish task
			return true;
		}
		
		// Wait until we're in the bank
		if ( !Banking.isInBank() )
			return false;
		
		// Open the bank
		final OpenBankPreference pref = AntiBan.getABC().generateOpenBankPreference();
			switch (pref) {
			case BANKER:
				Banking.openBankBanker();
				break;
	
			case BOOTH:
				Banking.openBankBooth();
				break;
	
			default:
				throw new RuntimeException("Unhandled open bank preference.");
		}
		
		// Wait a little bit
		AntiBan.sleep(2000, 500);
		AntiBan.afk(AntiBan.generateAFKTime(5000));
		return false;
	}


	public String getTaskName() {
		return "Banking Items";
	}
}
