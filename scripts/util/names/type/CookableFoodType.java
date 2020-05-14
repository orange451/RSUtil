package scripts.util.names.type;

import scripts.util.misc.ItemWrapper;
import scripts.util.misc.NameFormatter;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;

public enum CookableFoodType implements ItemWrapper {
	SHRIMPS(ItemNames.RAW_SHRIMPS, 1),
	ANCHOVIES(ItemNames.RAW_ANCHOVIES, 1),
	SARDINE(ItemNames.RAW_SARDINE, 1),
	HERRING(ItemNames.RAW_HERRING, 1),
	TROUT(ItemNames.RAW_TROUT, 15),
	PIKE(ItemNames.RAW_PIKE, 20),
	SALMON(ItemNames.RAW_SALMON, 25),
	TUNA(ItemNames.RAW_TUNA, 30),
	LOBSTER(ItemNames.RAW_LOBSTER, 40),
	SWORDFISH(ItemNames.RAW_SWORDFISH, 45),
	;

	private ItemIds item;
	private int minimumLevel;
	
	private CookableFoodType(ItemIds item, int minimumLevel) {
		this.item = item;
		this.minimumLevel = minimumLevel;
	}
	
	public String getName() {
		return NameFormatter.formatItemName(toString());
	}
	
	public int getMinimumCookingLevelRequired() {
		return this.minimumLevel;
	}
	
	public static CookableFoodType get(ItemNames item) {
		for (CookableFoodType foodType : values()) {
			if (foodType.getItem().equals(item))
				return foodType;
		}
		return null;	
	}
	
	@Override
	public ItemIds getItem() {
		return this.item;
	}

}
