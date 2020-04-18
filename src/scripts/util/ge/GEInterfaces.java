package scripts.util.ge;

import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterface;

public enum GEInterfaces {
	OFFER_WINDOW(465),
	BACK_BUTTON(OFFER_WINDOW.parent, 4),
	
	COLLECT_ALL(OFFER_WINDOW.parent, 6, 1),
	
	BUY_WINDOW(OFFER_WINDOW.parent, 24, 19, 1118),
	QUANTITY_MANUAL(OFFER_WINDOW.parent, BUY_WINDOW.child, 7),
	PRICE_MANUAL(OFFER_WINDOW.parent, BUY_WINDOW.child, 12),
	CONFIRM_OFFER(OFFER_WINDOW.parent, 27),
	
	SEARCH_ITEM_RESULTS_CONTAINER(162, 46),
	SEARCH_ITEM_INPUT_TEXT(162, 38),
	SEARCH_RESULT_1(162, 53, 1),
	SEARCH_RESULT_2(162, 53, 4),
	SEARCH_RESULT_3(162, 53, 7),
	SEARCH_RESULT_4(162, 53, 10),
	SEARCH_RESULT_5(162, 53, 13),
	SEARCH_RESULT_6(162, 53, 16),
	SEARCH_RESULT_7(162, 53, 19),
	SEARCH_RESULT_8(162, 53, 22),
	SEARCH_RESULT_9(162, 53, 25);

	public static final int OFFER_BOX_OFFSET = 7;
	public static final int OFFER_BOX_ITEM_OFFSET = 18;
	public static final int OFFER_BOX_BAR1_OFFSET = 21;
	public static final int OFFER_BOX_BAR2_OFFSET = 22;
	
	private int parent, child, component, texture;
	private int[] id;

	GEInterfaces(int... ids) {
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