package scripts.util.task;

public abstract class BotTaskStartingTask extends BotTask {

	@Override
	public void init() {
		//
	}

	@Override
	public String getTaskName() {
		return "Choosing Starting Task";
	}

	@Override
	public boolean isTaskComplete() {
		return true;
	}

}
