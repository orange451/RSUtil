package scripts.util.names.type;

import scripts.util.misc.ItemWrapper;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;

public enum SmithableType implements ItemWrapper {
	BRONZE_DAGGER(ItemNames.BRONZE_DAGGER, 			1, BarType.BRONZE_BAR, 1),
	BRONZE_AXE(ItemNames.BRONZE_AXE, 				1, BarType.BRONZE_BAR, 1),
	BRONZE_MACE(ItemNames.BRONZE_MACE, 				2, BarType.BRONZE_BAR, 1),
	BRONZE_MED_HELM(ItemNames.BRONZE_MED_HELM, 		3, BarType.BRONZE_BAR, 1),
	BRONZE_SWORD(ItemNames.BRONZE_SWORD, 			4, BarType.BRONZE_BAR, 1),
	BRONZE_SCIMITAR(ItemNames.BRONZE_SCIMITAR, 		5, BarType.BRONZE_BAR, 2),
	BRONZE_LONGSWORD(ItemNames.BRONZE_LONGSWORD,	6, BarType.BRONZE_BAR, 2),
	BRONZE_FULL_HELM(ItemNames.BRONZE_FULL_HELM,	7, BarType.BRONZE_BAR, 2),
	BRONZE_SQ_SHIELD(ItemNames.BRONZE_SQ_SHIELD,	8, BarType.BRONZE_BAR, 2),
	BRONZE_WARHAMMER(ItemNames.BRONZE_WARHAMMER,	9, BarType.BRONZE_BAR, 3),
	BRONZE_BATTLEAXE(ItemNames.BRONZE_BATTLEAXE,	10, BarType.BRONZE_BAR, 3),
	BRONZE_CHAINBODY(ItemNames.BRONZE_CHAINBODY,	11, BarType.BRONZE_BAR, 3),
	BRONZE_KITESHIELD(ItemNames.BRONZE_KITESHIELD,	12, BarType.BRONZE_BAR, 3),
	BRONZE_2H_SWORD(ItemNames.BRONZE_2H_SWORD,	14, BarType.BRONZE_BAR, 3),
	BRONZE_PLATELEGS(ItemNames.BRONZE_PLATELEGS,	16, BarType.BRONZE_BAR, 3),
	BRONZE_PLATESKIRT(ItemNames.BRONZE_PLATESKIRT,	16, BarType.BRONZE_BAR, 3),
	BRONZE_PLATEBODY(ItemNames.BRONZE_PLATEBODY,	18, BarType.BRONZE_BAR, 5),
	
	;
	
	private ItemIds item;
	private int requiredSmithingLevel;
	private BarType requiredBarType;
	private int requiredBarAmount;
	
	SmithableType(ItemIds item, int level, BarType requiredBar, int amountBars) {
		this.item = item;
		this.requiredSmithingLevel = level;
		this.requiredBarType = requiredBar;
	}
	
	public BarType getRequiredBarType() {
		return this.requiredBarType;
	}
	
	public int getRequiredBarAmount() {
		return this.requiredBarAmount;
	}
	
	public int getRequiredSmithingLevel() {
		return this.requiredSmithingLevel;
	}
	
	@Override
	public ItemIds getItem() {
		return this.item;
	}

}
