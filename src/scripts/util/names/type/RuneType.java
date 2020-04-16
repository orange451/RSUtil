package scripts.util.names.type;

import scripts.util.misc.ItemWrapper;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;

public enum RuneType implements ItemWrapper {
	AIR(ItemNames.AIR_RUNE),
	WATER(ItemNames.WATER_RUNE),
	EARTH(ItemNames.EARTH_RUNE),
	FIRE(ItemNames.FIRE_RUNE);
	
	private ItemIds item;
	
	RuneType(ItemIds item) {
		this.item = item;
	}
	
	public ItemIds getItem() {
		return this.item;
	}
}
