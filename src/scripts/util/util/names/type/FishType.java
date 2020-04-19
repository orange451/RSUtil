package scripts.util.names.type;

import scripts.util.misc.ItemWrapper;
import scripts.util.misc.NameFormatter;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;
import scripts.util.names.ItemNamesData;

public enum FishType implements ItemWrapper {
	SHRIMPS(ItemNames.RAW_SHRIMPS, ItemNames.SHRIMPS, FishingEquipment.SMALL_FISHING_NET, 1, 1),
	ANCHOVIES(ItemNames.RAW_ANCHOVIES, ItemNames.ANCHOVIES, FishingEquipment.SMALL_FISHING_NET, 15, 1),
	SARDINE(ItemNames.RAW_SARDINE, ItemNames.SARDINE, FishingEquipment.FISHING_ROD, 5, 1),
	HERRING(ItemNames.RAW_HERRING, ItemNames.HERRING, FishingEquipment.FISHING_ROD, 10, 5);
	
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
		return NameFormatter.get(this.toString());
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
