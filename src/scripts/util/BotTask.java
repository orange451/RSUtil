package scripts.util;

public abstract class BotTask {

	public BotTask() {
		this.init();
	}


	public abstract void init();
	public abstract String getTaskName();
	public abstract BotTask getNextTask();
	public abstract boolean isTaskComplete();
}
