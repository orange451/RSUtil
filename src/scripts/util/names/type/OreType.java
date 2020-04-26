package scripts.util.names.type;

import scripts.util.misc.ItemWrapper;
import scripts.util.misc.NameFormatter;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;

public enum OreType implements ItemWrapper {
	COPPER(ItemNames.COPPER_ORE),
	TIN(ItemNames.TIN_ORE),
	COAL(ItemNames.COAL),
	GOLD(ItemNames.GOLD_ORE),
	IRON(ItemNames.IRON_ORE),
	MITHRIL(ItemNames.MITHRIL_ORE),
	ADAMANT(ItemNames.ADAMANTITE_ORE), 
	CLAY(ItemNames.CLAY);
	
	private ItemIds item;
	
	private OreType(ItemIds item) {
		this.item = item;
	}
	
	@Override
	public ItemIds getItem() {
		return this.item;
	}
	
	public String getName() {
		return NameFormatter.get(this.toString());
	}

	public static OreType get(ItemNames item) {
		for (OreType rockType : values()) {
			if ( rockType.getItem().equals(item) ) {
				return rockType;
			}
		}
		return null;
	}
}