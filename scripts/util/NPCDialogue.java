package scripts.util;

import org.tribot.api.General;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSInterfaceComponent;
import org.tribot.api2007.types.RSInterfaceMaster;

import scripts.util.misc.AntiBan;

public class NPCDialogue {
	private static String lastChoice;

	/**
	 * Returns whether you are currently in a conversation with an NPC.
	 * @return
	 */
	public static boolean isInConversation() {
		/*General.println(hasClickToContinue()
				+ " / " + (NPCChat.getName() != null)
				+ " / " + (NPCChat.getMessage() != null)
				+ " / " + (NPCChat.getOptions() != null)
				+ " / " + (NPCChat.getClickContinueInterface() != null));*/
		return hasClickToContinue()
				|| NPCChat.getName() != null
				/*|| NPCChat.getMessage() != null*/
				|| NPCChat.getOptions() != null 
				/*|| NPCChat.getClickContinueInterface() != null*/;
	}

	/**
	 * Returns if the player has a NON NPC click to continue messge
	 * @return
	 */
	public static boolean hasClickToContinue() {
		return getContinueButton(162) != null || getContinueButton(193) != null;
	}
	
	private static RSInterface getContinueButton(int id) {
		RSInterfaceMaster root = Interfaces.get(id);
		if ( root == null )
			return null;
		
		RSInterfaceChild[] children = root.getChildren();
		if ( children != null ) {
			for (int i = 0; i < children.length; i++) {
				RSInterfaceChild child = children[i];
				if ( child.getText().contains("continue") && !child.isHidden() ) {
					return child;
				}
			}
		}
		
		RSInterfaceComponent[] components = root.getComponents();
		if ( components != null ) {
			for (int i = 0; i < components.length; i++) {
				RSInterfaceComponent child = components[i];
				if ( child.getText().contains("continue") && !child.isHidden() ) {
					return child;
				}
			}
		}
		
		return null;
	}

	/**
	 * Clicks the continue button.
	 * @return
	 */
	public static boolean clickContinue() {
		if ( hasClickToContinue() ) {
			if ( getContinueButton(162) != null )
				getContinueButton(162).click("");
			
			if ( getContinueButton(193) != null )
				getContinueButton(193).click("");
			
			AntiBan.sleep(800, 400);
			return true;
		}
		
		if (NPCChat.getClickContinueInterface() != null) {
			NPCChat.clickContinue(true);
			AntiBan.sleep(800, 400);
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
	public static boolean clickLastChoice() {
		if (lastChoice != null) {
			boolean worked = NPCChat.selectOption(lastChoice, true);
			AntiBan.sleep(1000, 500);
			return worked;
		}
		
		return false;
	}
}
