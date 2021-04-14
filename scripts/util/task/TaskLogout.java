package scripts.util.task;

import org.tribot.api2007.Login;

public abstract class TaskLogout extends BotTask {
	public TaskLogout() {
		//
	}
	
	@Override
	public void init() {
		//
	}

	@Override
	public String getTaskName() {
		return "Logging out";
	}
	
	@Override
	public boolean isTaskComplete() {
		return Login.logout();
	}

}
