package scripts.util;

public abstract class BotTask {
	protected boolean forceComplete;

	public BotTask() {
		this.init();
	}

	public void forceComplete() {
		this.forceComplete = true;
	}

	public boolean wasForceCompleted() {
		return this.forceComplete;
	}

	public abstract void init();
	public abstract String getTaskName();
	public abstract BotTask getNextTask();
	public abstract boolean isTaskComplete();
}
