package scripts.util.names.type;

import scripts.util.misc.ItemWrapper;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;

public enum GemType implements ItemWrapper {
	SAPPHIRE(ItemNames.UNCUT_SAPPHIRE, ItemNames.SAPPHIRE, false, 20),
	EMERALD(ItemNames.UNCUT_EMERALD, ItemNames.EMERALD, false, 27),
	RUBY(ItemNames.UNCUT_RUBY, ItemNames.RUBY, false, 34),
	DIAMOND(ItemNames.UNCUT_DIAMOND, ItemNames.DIAMOND, false, 43),
	OPAL(ItemNames.UNCUT_OPAL, ItemNames.OPAL, false, 1),
	JADE(ItemNames.UNCUT_JADE, ItemNames.JADE, false, 13),
	RED_TOPAZ(ItemNames.UNCUT_RED_TOPAZ, ItemNames.RED_TOPAZ, false, 16),
	DRAGONSTONE(ItemNames.UNCUT_DRAGONSTONE, ItemNames.DRAGONSTONE, false, 55),
	ONYX(ItemNames.UNCUT_ONYX, ItemNames.ONYX, false, 67);

	private ItemIds item;
	private ItemIds uncut;
	private boolean members;
	private int minimumCraftingLevel;
	
	GemType(ItemIds uncut, ItemIds item, boolean members, int minimumCraftingLevel) {
		this.uncut = uncut;
		this.item = item;
		this.members = members;
		this.minimumCraftingLevel = minimumCraftingLevel;
	}
	
	public boolean isMembers() {
		return this.members;
	}
	
	public int getMinimumCraftingLevel() {
		return minimumCraftingLevel;
	}
	
	public ItemIds getItem() {
		return this.item;
	}
	
	public ItemIds getUncut() {
		return this.uncut;
	}
	
	public static ItemIds[] cut() {
		return ItemNames.get(GemType.values());
	}
	
	public static ItemIds[] uncut() {
		GemType[] objects = values();
		ItemIds[] ret = new ItemIds[objects.length];
		for (int i = 0; i < objects.length; i++) {
			ret[i] = objects[i].getUncut();
		}
		
		return ret;
	}
}
