package scripts.util.task;

public abstract class BotTask {
	protected boolean forceComplete;
	protected boolean cancelled;

	public BotTask() {
		init();
	}

	public void forceComplete() {
		this.forceComplete = true;
	}

	public boolean wasForceCompleted() { return this.forceComplete; }

	public abstract void init();

	public abstract String getTaskName();

	public abstract BotTask getNextTask();

	public abstract boolean isTaskComplete();
	
	public void cancel() {
		this.cancelled = true;
		this.forceComplete = true;
	}

	public boolean cancelled() {
		return this.cancelled;
	}
}
