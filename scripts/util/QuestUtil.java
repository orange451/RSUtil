package scripts.util;

import org.tribot.api2007.Game;
import scripts.util.names.Quests;

public class QuestUtil {
	/**
	 * Returns the current progress of the quest.
	 * @param quest
	 * @return
	 */
	public static int getQuestSetting(Quests quest) {
		return Game.getSetting(quest.getId());
	}

	/**
	 * Returns whether or not a quest is started.
	 * @param quest
	 * @return
	 */
	public static boolean isQuestStarted(Quests quest) {
		int val = getQuestSetting(quest);
		return (val > 0) && (val < quest.getFinished());
	}

	/**
	 * Returns whether a quest is completed or not.
	 * @param quest
	 * @return
	 */
	public static boolean isQuestCompleted(Quests quest) {
		return getQuestSetting(quest) >= quest.getFinished();
	}
	
	/**
	 * Returns the amount of Quest Points the player has.
	 * @return
	 */
	public static int getQuestPoints() {
		return Game.getSetting(101);
	}
}
