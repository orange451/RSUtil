package scripts.util.task;

import org.tribot.script.Script;

public abstract class TaskScript extends Script {
	private boolean running;
	private BotTask currentTask;
	private boolean scriptStarted;

	public void run() {
		onInitialize();
		
		if ( running )
			return;
		
		this.running = true;
		
		// Start script
		if ( autoStart() )
			start();
		
		// logic loop
		while (this.running) {
			sleep(25L);

			onStep();
			
			if ((this.currentTask != null) && (this.currentTask.isTaskComplete())) {
				this.currentTask = this.currentTask.getNextTask();
			}
		}
		
		stop();
	}

	/**
	 * Stop the script from running.
	 */
	public void stop() {
		this.running = false;
		this.stopScript();
	}
	
	/**
	 * Start the script. This method will call {@link #getStartingTask()} to determine which task is first.
	 */
	public void start() {
		if ( scriptStarted )
			return;
		
		this.currentTask = getStartingTask();
		this.scriptStarted = true;
		
	}

	/**
	 * Stops the current task.
	 */
	public void stopCurrentTask() {
		this.currentTask.forceComplete = true;
		this.currentTask = null;
		this.scriptStarted = false;
	}

	/**
	 * Overrides the current task.
	 * @param task
	 */
	public void setCurrentTask(BotTask task) {
		if ( task == null ) {
			stopCurrentTask();
			return;
		}
		
		this.currentTask = task;
		this.scriptStarted = true;
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
