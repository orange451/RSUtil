package scripts.util.task;

public abstract class BotTask {
	protected boolean forceComplete;
	protected boolean cancelled;
	protected boolean initialized;

	public BotTask() {
		//
	}

	public void forceComplete() {
		this.forceComplete = true;
	}

	public boolean wasForceCompleted() { return this.forceComplete; }

	public abstract String getTaskName();

	public abstract BotTask getNextTask();

	public abstract boolean isTaskComplete();
	
	public void init() {
		//
	}
	
	/**
	 * Mark this task as cancelled. If finished, it will not go on to the next task.
	 */
	public void cancel() {
		this.cancelled = true;
		this.forceComplete = true;
	}
	
	/**
	 * Reset a bot task.
	 */
	public void reset() {
		this.forceComplete = false;
		this.cancelled = false;
		this.initialized = false;
	}

	/**
	 * Returns whether the task is cancelled.
	 * @return
	 */
	public boolean isCancelled() {
		return this.cancelled;
	}
	
	/**
	 * Returns whether the task has already initialized. A task initializes on the first tick it runs.
	 * @return
	 */
	public boolean initialized() {
		return this.initialized;
	}
	
	@Override
	public String toString() {
		return this.getTaskName();
	}
}
