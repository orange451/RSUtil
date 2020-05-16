package scripts.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	
	private static final int[] potentialClickContinueDialogs = new int[] {
			162, 193, 217, 229, 231
	};
	
	private static final int[] potentialClickChoiceDialogs = new int[] {
			219,
	};

	/**
	 * Returns whether you are currently in a conversation with an NPC.
	 * @return
	 */
	public static boolean isInConversation() {
		/*General.println(hasClickToContinue()
				+ " / " + (NPCChat.getName() != null)
				+ " / " + (NPCChat.getMessage() != null)
				+ " / " + (NPCChat.getOptions() != null)
				+ " / " + (NPCChat.getClickContinueInterface() != null)
		);*/
		
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
		for (Integer id : potentialClickContinueDialogs) {
			if ( getContinueButton(id) != null )
				return true;
		}
		return false;
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
		if ( isInConversation() ) {
			//General.println("CLICKING CONTINUE");
			if ( hasClickToContinue() ) {
				for (Integer id : potentialClickContinueDialogs) {
					if ( getContinueButton(id) != null ) {
						getContinueButton(id).click("");
						break;
					}
				}
				
				AntiBan.sleep(800, 400);
				return true;
			}
			
			if (NPCChat.getOptions().length == 0 && NPCChat.getClickContinueInterface() != null) {
				NPCChat.clickContinue(true);
				AntiBan.sleep(800, 400);
				return true;
			}
		}
		return false;
	}
	
	public static String[] getOptions() {
		List<String> options = new ArrayList<>();
		String[] t = NPCChat.getOptions();
		if ( t != null )
			for (String option : t)
				options.add(option);
		
		RSInterfaceComponent[] t2 = getOptionsInternal();
		if ( t2 != null )
			for (RSInterfaceComponent option : t2)
				options.add(option.getText());
		
		return options.toArray(new String[options.size()]);
	}
	
	private static RSInterfaceComponent[] getOptionsInternal() {
		List<RSInterfaceComponent> options = new ArrayList<>();
		
		for (Integer interfaceId : potentialClickChoiceDialogs) {
			RSInterfaceMaster interf = Interfaces.get(interfaceId);
			if ( interf != null ) {
				RSInterfaceChild[] children = interf.getChildren();
				for (RSInterfaceChild child : children) {
					RSInterfaceComponent[] ioptions = child.getChildren();
					if ( ioptions != null ) {
						for (RSInterfaceComponent ioption : ioptions) {
							if ( ioption.getTextureID() > -1 || ioption.getText() == null || ioption.getText().length() == 0 )
								continue;
							options.add(ioption);
						}
					}
				}
			}
		}
		
		return options.toArray(new RSInterfaceComponent[options.size()]);
	}
	
	public static boolean selectOption(String choice, boolean wait) {
		RSInterfaceComponent[] t2 = getOptionsInternal();
		for (RSInterfaceComponent option : t2) {
			if ( option.getText().startsWith(choice) ) {
				option.click("");
				General.sleep(50,150);
				return true;
			}
		}
		
		return NPCDialogue.selectOption(choice, false);
	}

	/**
	 * Returns whether a specific choice exists in a npc dialog.
	 * @param string
	 * @return
	 */
	public static boolean findChoice(String string) {
		String[] o = NPCDialogue.getOptions();
		if (o == null)
			return false;
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
