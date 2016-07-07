package scripts.util;

import java.awt.Rectangle;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSInterfaceComponent;

/**
 * This script was not created by me.
 * However I do not know the original author.
 *
 * If the author of this java file finds this,
 * and does not want it shared on my hithub,
 * please notify me!
 * I will remove it!
 *
 * @author NOT WRITTEN BY ME.
 *
 */
public class WorldSwitcher extends Condition {
	private int world;
	public WorldSwitcher(int world) {
		this.world = world;
	}

	public static WorldSwitcher switchedWorld(int world) {
		return new WorldSwitcher(world);
	}

	public static RSInterfaceComponent getWorldComponent(int world) {
		RSInterfaceChild worldList = Interfaces.get(69, 7);

		if (worldList != null) {
			for (RSInterfaceComponent i : worldList.getChildren()) {
				if (i.getText().equals(String.valueOf(world))) {
					return worldList.getChildren()[(i.getIndex() - 2)];
				}
			}
		}
		return null;
	}

	public static void scrollToWorld(int world) {
		RSInterfaceComponent worldComponent = getWorldComponent(world);
		Rectangle rect = new Rectangle(547, 234, 190, 190);

		if (worldComponent != null)
			if (rect.contains(Mouse.getPos()))
				while (!rect.contains(worldComponent.getAbsolutePosition())) {
					General.println(Integer.valueOf(worldComponent.getAbsoluteBounds().y));
					Mouse.scroll(worldComponent.getAbsoluteBounds().y < rect.y);
					General.sleep(50, 100);
				}
			else
				Mouse.moveBox(rect);
	}

	public static void switchWorld(int world) {
		RSInterfaceChild worldList = Interfaces.get(69, 7);
		RSInterfaceChild btnWorldSwitcher = Interfaces.get(182, 1);
		Rectangle rect = new Rectangle(547, 234, 190, 190);

		if (GameTab.TABS.LOGOUT.isOpen()) {
			if (worldList != null) {
				RSInterfaceComponent worldComponent = getWorldComponent(world);

				if (NPCChat.getOptions() != null) {
					if ((NPCChat.getOptions()[0] != null) && (NPCChat.getOptions()[0].contains("Yes"))) {
						if (NPCChat.selectOption("Yes", true))
							Timing.waitCondition(switchedWorld(world), General.random(3000, 6000));
					}
					else if ((NPCChat.getOptions()[1] != null) && (NPCChat.getOptions()[1].contains("Switch")) &&
							(NPCChat.selectOption(NPCChat.getOptions()[1], true))) {
						Timing.waitCondition((Condition)switchedWorld(world), General.random(3000, 6000));
					}

				}

				General.println(Integer.valueOf(worldComponent.getIndex()));

				if (worldComponent != null) {
					if (rect.contains(worldComponent.getAbsolutePosition()))
						worldComponent.click(new String[] { "Switch" });
					else {
						scrollToWorld(world);
					}
				}
			}
			else if (btnWorldSwitcher != null) {
				btnWorldSwitcher.click(new String[] { "World Switcher" });
			}
		}
		else
			GameTab.TABS.LOGOUT.open();
	}

	@Override
	public boolean active() {
		return Game.getCurrentWorld() == this.world;
	}
}