package scripts.util.misc;

import java.awt.Rectangle;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSInterfaceComponent;

@SuppressWarnings("deprecation")
public class WorldSwitcher {
	
	/**
	 * Attempts to switch the current world.
	 * @param world
	 */
	public static void switchWorld(int world) {
		switchWorld(world, null);
	}

	/**
	 * Attempts to switch the current world. Runs the callback object if the world change was successful.
	 * @param world
	 * @param callback
	 */
	public static void switchWorld(int world, Runnable callback) {
		RSInterfaceChild worldList = Interfaces.get(69, 7);
		RSInterfaceChild btnWorldSwitcher = Interfaces.get(182, 3);

		// Open logout menu
		while (!GameTab.TABS.LOGOUT.isOpen()) {
			GameTab.TABS.LOGOUT.open();
			General.sleep(1000+AntiBan.generateResponseTime(2000));
		}
		
		// Change world
		if (worldList != null) {
			// Scroll to the world component
			scrollToWorld(world);
			
			// Get the world component
			RSInterfaceComponent worldComponent = getWorldInterfaceButton(world);

			// Click the world component
			if (worldComponent != null)
				worldComponent.click(new String[] { "Switch" });
			
			// Wait for chat options
			General.sleep(250+AntiBan.generateResponseTime(1000));
			
			// Click through the chat options
			if (NPCChat.getOptions() != null) {
				Condition c = new Condition() {
					@Override
					public boolean active() {
						return Game.getCurrentWorld() == world;
					}
				};
				
				if ((NPCChat.getOptions()[0] != null) && (NPCChat.getOptions()[0].contains("Yes"))) {
					if (NPCChat.selectOption("Yes", true)) {
						Timing.waitCondition(c, General.random(3000, 6000));
						if ( c.active() && callback != null )
							callback.run();
					}
				} else if ((NPCChat.getOptions()[1] != null) && (NPCChat.getOptions()[1].contains("Switch")) && 
						(NPCChat.selectOption(NPCChat.getOptions()[1], true))) {
					Timing.waitCondition(c, General.random(3000, 6000));
					if ( c.active() && callback != null )
						callback.run();
				}
			}
		} else if (btnWorldSwitcher != null) {
			// Go into world switcher mode
			btnWorldSwitcher.click(new String[] { "World Switcher" });
		}
	}
	
	private static RSInterfaceComponent getWorldInterfaceButton(int world) {
		RSInterfaceChild worldList = Interfaces.get(69, 17);

		if (worldList != null) {
			RSInterfaceComponent[] arrayOfRSInterfaceComponent;
			int j = (arrayOfRSInterfaceComponent = worldList.getChildren()).length;
			
			for (int i = 2; i < j; i+=6) {
				RSInterfaceComponent intf = arrayOfRSInterfaceComponent[i];
				if (intf.getText().equals(String.valueOf(world))) {
					return worldList.getChildren()[i-2];
				}
			}
		}
		return null;
	}

	private static void scrollToWorld(int world) {
		RSInterfaceComponent worldComponent = getWorldInterfaceButton(world);
		Rectangle rect = new Rectangle(547, 234, 190, 190);

		if (worldComponent != null) {
			// Move mouse into the box
			Mouse.moveBox(rect);
			
			// Scroll to the world
			if (rect.contains(Mouse.getPos())) {
				while (!rect.contains(worldComponent.getAbsolutePosition())) {
					Mouse.scroll(worldComponent.getAbsoluteBounds().y < rect.y);
					General.sleep(25, 75);
				}
			}
		}
	}
}
