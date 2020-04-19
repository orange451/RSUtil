package scripts.util.task;

import org.tribot.script.Script;

public abstract class TaskScript extends Script {
	private boolean running;
	private BotTask currentTask;
	private boolean scriptStarted;
	
	private static TaskScript script;

	public void run() {
		onInitialize();
		
		script = this;
		
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
	 * Start the script. This method will call {@link #getStartingTask()} to determine which task is first.
	 */
	public static void start() {
		if ( script.scriptStarted )
			return;
		
		script.currentTask = script.getStartingTask();
		script.scriptStarted = true;
	}

	/**
	 * Stop the script from running.
	 */
	public static void stop() {
		script.running = false;
		//script.stopScript();
	}
	
	/**
	 * Stops the current task.
	 */
	public static void stopCurrentTask() {
		script.currentTask.forceComplete = true;
		script.currentTask = null;
		script.scriptStarted = false;
	}

	/**
	 * Overrides the current task.
	 * @param task
	 */
	public static void setCurrentTask(BotTask task) {
		if ( task == null ) {
			stopCurrentTask();
			return;
		}
		
		script.currentTask = task;
		script.scriptStarted = true;
	}
	
	/**
	 * Returns the current running script instance
	 * @return
	 */
	public static Script getScript() {
		return script;
	}
	
	/**
	 * Returns whether the task script is currently trying to perform tasks
	 */
	public static boolean isStarted() {
		return script.scriptStarted;
	}

	/**
	 * Returns the current task
	 * @return 
	 */
	public static BotTask getCurrentTask() {
		return script.currentTask;
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
