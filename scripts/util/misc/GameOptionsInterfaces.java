package scripts.util.misc;

import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterface;

public enum GameOptionsInterfaces {
	OPTIONS_WINDOW(134),
	MORE_INFORMATION(OPTIONS_WINDOW.parent, 6, 9),
	CONTROLS(OPTIONS_WINDOW.parent, 22, 3),
	SHIFT_DROP_CHECKBOX(OPTIONS_WINDOW.parent, 17, 26),
	CLOSE(OPTIONS_WINDOW.parent, 4),
	;
	
	private int parent, child, component, texture;
	private int[] id;

	GameOptionsInterfaces(int... ids) {
		this.id = ids;
		switch (id.length) {
		case 1:
			this.parent = id[0];
			break;
		case 2:
			this.parent = id[0];
			this.child = id[1];
			break;
		case 3:
			this.parent = id[0];
			this.child = id[1];
			this.component = id[2];
			break;
		case 4:
			this.parent = id[0];
			this.child = id[1];
			this.component = id[2];
			this.texture = id[3];
		}
	}

	public boolean isVisible() {
		switch (id.length) {
		case 1:
			return Interfaces.get(parent) != null && !Interfaces.get(parent).isHidden();
		case 2:
			return Interfaces.get(parent, child) != null && !Interfaces.get(parent, child).isHidden();
		case 3:
			return Interfaces.get(parent, child, component) != null
					&& !Interfaces.get(parent, child, component).isHidden();
		case 4:
			return Interfaces.get(parent, child, component) != null
					&& !Interfaces.get(parent, child, component).isHidden()
					&& Interfaces.get(parent, child, component).getTextureID() == texture;
		}

		return false;
	}

	public int[] getID() {
		switch (id.length) {
		case 1:
			return new int[] { parent };
		case 2:
			return new int[] { parent, child };
		}
		return new int[] { parent, child, component };
	}

	public RSInterface get() {
		switch (id.length) {
		case 1:
			return Interfaces.get(parent);

		case 2:
			return Interfaces.get(parent, child);
		}
		return Interfaces.get(parent, child, component);
	}

	public int getParent() {
		return this.parent;
	}
	
	public int getChild() {
		return this.child;
	}
	
	public int getComponent() {
		return this.component;
	}

	public void click(String... option) {
		this.get().click(option);
	}
}