package scripts.util.task;

import org.tribot.script.Script;

public abstract class TaskScript extends Script {
	private boolean running;
	private BotTask currentTask;

	public void run() {
		onInitialize();
		start();
	}

	/**
	 * Stop the script from running.
	 */
	public void stop() {
		this.running = false;
	}
	
	/**
	 * Start the script. This method will call {@link #getStartingTask()} to determine which task is first.
	 */
	public void start() {
		if ( running )
			return;
		
		this.running = true;
		
		if ( autoStart() )
			this.currentTask = getStartingTask();
		
		while (this.running) {
			sleep(25L);

			onStep();
			if ((this.currentTask != null) && (this.currentTask.isTaskComplete())) {
				this.currentTask = this.currentTask.getNextTask();
			}
		}
	}

	/**
	 * Stops the current task.
	 */
	public void stopCurrentTask() {
		this.currentTask.forceComplete = true;
		this.currentTask = null;
	}

	/**
	 * Overrides the current task.
	 * @param task
	 */
	public void setCurrentTask(BotTask task) {
		this.currentTask = task;
	}

	/**
	 * Returns the current task
	 * @return 
	 */
	public BotTask getCurrentTask() {
		return this.currentTask;
	}

	/**
	 * The first task the script will run.
	 * @return
	 */
	public abstract BotTask getStartingTask();

	public abstract void onInitialize();

	public abstract void onStep();
	
	/**
	 * If this method returns true, the current task is initially set to whatever is returned with {@link #getStartingTask()} when the script initializes.
	 * @return
	 */
	public abstract boolean autoStart();
}
