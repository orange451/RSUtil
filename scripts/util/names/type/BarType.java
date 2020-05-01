package scripts.util.names.type;

import scripts.util.misc.ItemWrapper;
import scripts.util.names.ItemIds;

public enum BarType implements ItemWrapper {
	BRONZE_BAR(OreType.COPPER, OreType.TIN, 1, 1),
	IRON_BAR(),
	STEEL_BAR(),
	GOLD_BAR(),
	MITHRIL_BAR(),
	ADAMANT_BAR(),
	RUNE_BAR(),
	;
	
	private OreType ores;
	private int oreamount;
	private int minimumlevel;

	private BarType(OreType ore, int oreamount, int minimumlevel) {
		this.ores = ores;
		this.oreamount = oreamount;
		this.minimumlevel = minimumlevel;
	}
	
	private BarType(OreType ore1, OreType ore2, int oreamount1, int oreamount2, int minimumlevel) {
		this.ores = ore1;
		this.ores = ore2;
		int oreamount = oreamount1;
		int oreamount = oreamount2;
	}

	@Override
	public ItemIds getItem() {
		// TODO Auto-generated method stub
		return null;
	}

}
