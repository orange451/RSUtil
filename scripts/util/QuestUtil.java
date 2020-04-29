package scripts.util;

import org.tribot.api2007.Game;
import scripts.util.names.Quests;

public class QuestUtil {
	public static int getQuestSetting(Quests quest) {
		return Game.getSetting(quest.getId());
	}

	public static boolean isQuestStarted(Quests quest) {
		int val = getQuestSetting(quest);
		return (val > 0) && (val < quest.getFinished());
	}

	public static boolean isQuestCompleted(Quests quest) {
		return getQuestSetting(quest) >= quest.getFinished();
	}
	
	public static int getQuestPoints() {
		return Game.getSetting(101);
	}
}
