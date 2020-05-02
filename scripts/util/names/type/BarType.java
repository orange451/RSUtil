package scripts.util.names.type;

import scripts.util.misc.ItemWrapper;
import scripts.util.misc.NameFormatter;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;

public enum BarType implements ItemWrapper {
	BRONZE_BAR(ItemNames.BRONZE_BAR, OreType.COPPER, OreType.TIN, 1, 1),
	IRON_BAR(ItemNames.IRON_BAR, OreType.IRON, 15),
	STEEL_BAR(ItemNames.STEEL_BAR, OreType.IRON, OreType.COAL, 2, 30),
	GOLD_BAR(ItemNames.GOLD_BAR, OreType.GOLD, 40),
	MITHRIL_BAR(ItemNames.MITHRIL_BAR, OreType.MITHRIL, OreType.COAL, 4, 50),
	ADAMANT_BAR(ItemNames.ADAMANTITE_BAR, OreType.ADAMANT, OreType.COAL, 6, 70),
	RUNE_BAR(ItemNames.RUNITE_BAR, OreType.RUNE, OreType.COAL, 8, 85);
	
	private ItemIds item;
	private OreType primaryOre;
	private OreType secondaryOre;
	private int secondaryOreAmmount;
	private int minimumLevel;

	private BarType(ItemIds item, OreType primaryOre, OreType secondaryOre, int secondaryOreAmmount, int minimumLevel) {
		this.item = item;
		this.primaryOre = primaryOre;
		this.secondaryOre = secondaryOre;
		this.secondaryOreAmmount = secondaryOreAmmount;
		this.minimumLevel = minimumLevel;
	}

	private BarType(ItemIds item, OreType primaryOre, int minimumLevel) {
		this(item, primaryOre, null, 0, minimumLevel);
	}
	
	public String getName() {
		return NameFormatter.get(this.toString());
	}
	
	public OreType getPrimaryOre() {
		return this.primaryOre;
	}
	
	public int getPrimaryOreAmount() {
		return 1;
	}
	
	public OreType getSecondaryOre() {
		return this.secondaryOre;
	}
	
	public OreType[] getOresUsed() {
		if ( this.getSecondaryOre() == null || this.getSecondaryOreAmount() == 0 )
			return new OreType[] {this.getPrimaryOre()};
		
		return new OreType[] { this.getPrimaryOre(), this.getSecondaryOre() };
	}
	
	public int getSecondaryOreAmount() {
		return this.secondaryOreAmmount;
	}
	
	public int getTotalOresRequired() {
		return this.getSecondaryOreAmount() + this.getPrimaryOreAmount();
	}
	
	public int getMinimumSmithingLevelRequired() {
		return this.minimumLevel;
	}

	@Override
	public ItemIds getItem() {
		return item;
	}

	public static BarType get(ItemNames item) {
		for (BarType barType : values()) {
			if (barType.getItem().equals(item))
				return barType;
		}
		return null;	
	}
}


