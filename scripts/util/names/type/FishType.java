package scripts.util.names.type;

import scripts.util.misc.ItemWrapper;
import scripts.util.misc.NameFormatter;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;

public enum FishType implements ItemWrapper {
	SHRIMPS(ItemNames.RAW_SHRIMPS, ItemNames.SHRIMPS, FishingEquipment.SMALL_FISHING_NET, 1, 1),
	ANCHOVIES(ItemNames.RAW_ANCHOVIES, ItemNames.ANCHOVIES, FishingEquipment.SMALL_FISHING_NET, 15, 1),
	SARDINE(ItemNames.RAW_SARDINE, ItemNames.SARDINE, FishingEquipment.FISHING_ROD, 5, 1),
	HERRING(ItemNames.RAW_HERRING, ItemNames.HERRING, FishingEquipment.FISHING_ROD, 10, 5),
	TROUT(ItemNames.RAW_TROUT, ItemNames.TROUT, FishingEquipment.FLY_FISHING_ROD, 20, 15),
	PIKE(ItemNames.RAW_PIKE, ItemNames.PIKE, FishingEquipment.FISHING_ROD, 25, 20),
	SALMON(ItemNames.RAW_SALMON, ItemNames.SALMON, FishingEquipment.FLY_FISHING_ROD, 30, 25),
	TUNA(ItemNames.RAW_TUNA, ItemNames.TUNA, FishingEquipment.HARPOON, 35, 30),
	LOBSTER(ItemNames.RAW_LOBSTER, ItemNames.LOBSTER, FishingEquipment.LOBSTER_POT, 40, 40);
	
	private FishingEquipment requiredEquipment;
	private ItemIds cookedItem;
	private ItemIds item;

	private int minimumLevelRequiredFish;
	private int minimumLevelRequiredCook;
	
	private FishType(ItemIds raw, ItemIds cooked, FishingEquipment requiredEquipment, int minimumLevelRequiredFish, int minimumLevelRequiredCook) {
		this.item = raw;
		this.cookedItem = cooked;
		this.requiredEquipment = requiredEquipment;
		this.minimumLevelRequiredFish = minimumLevelRequiredFish;
		this.minimumLevelRequiredCook = minimumLevelRequiredCook;
	}
	
	public FishType match(ItemIds id) {
		for (FishType fish : values()) {
			if ( id.equals(getRaw()) || id.equals(getCooked()) )
				return fish;
		}
		
		return null;
	}
	
	public FishType match(int id) {
		ItemIds temp = ItemNames.get(id);
		return match(temp);
	}

	public FishingEquipment getEquipmentRequired() {
		return this.requiredEquipment;
	}
	
	public String getName() {
		return NameFormatter.formatItemName(this.toString());
	}
	
	public int getMinimumLevelRequiredToFish() {
		return this.minimumLevelRequiredFish;
	}
	
	public int getMinimumLevelRequiredToCook() {
		return this.minimumLevelRequiredCook;
	}

	@Override
	public ItemIds getItem() {
		return this.item;
	}
	
	public ItemIds getRaw() {
		return this.item;
	}
	
	public ItemIds getCooked() {
		return this.cookedItem;
	}

	public static ItemIds[] raw() {
		FishType[] fishes = values();
		ItemIds[] ret = new ItemIds[fishes.length];
		for (int i = 0; i < fishes.length; i++) {
			ret[i] = fishes[i].getRaw();
		}
		
		return ret;
	}

	public static ItemIds[] cooked() {
		FishType[] fishes = values();
		ItemIds[] ret = new ItemIds[fishes.length];
		for (int i = 0; i < fishes.length; i++) {
			ret[i] = fishes[i].getCooked();
		}
		
		return ret;
	}
}
