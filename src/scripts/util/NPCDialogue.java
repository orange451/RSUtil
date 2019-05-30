package scripts.util;

import org.tribot.api2007.NPCChat;

import scripts.util.misc.AntiBan;

public class NPCDialogue {
	private static String lastChoice;

	/**
	 * Returns whether you are currently in a conversation with an NPC.
	 * @return
	 */
	public static boolean isInConversation() {
		return (NPCChat.getName() != null) || (NPCChat.getMessage() != null) || (NPCChat.getOptions() != null) || (NPCChat.getClickContinueInterface() != null);
	}

	/**
	 * Clicks the continue button.
	 * @return
	 */
	public static boolean clickContinue() {
		if (NPCChat.getClickContinueInterface() != null) {
			NPCChat.clickContinue(true);
			AntiBan.sleep(1000, 500);
			return true;
		}
		return false;
	}

	/**
	 * Returns whether a specific choice exists in a npc dialog.
	 * @param string
	 * @return
	 */
	public static boolean findChoice(String string) {
		String[] o = NPCChat.getOptions();
		if (o == null) {
			return false;
		}
		for (int i = 0; i < o.length; i++) {
			if (o[i].toLowerCase().contains(string.toLowerCase())) {
				lastChoice = o[i];
				return true;
			}
		}

		return false;
	}

	/**
	 * Clicks the last choice searched for via {@link #findChoice(String)}.
	 */
	public static void clickLastChoice() {
		if (lastChoice != null) {
			NPCChat.selectOption(lastChoice, true);
			AntiBan.sleep(1000, 500);
		}
	}
}
