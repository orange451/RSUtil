package scripts.util.names.type;

import scripts.util.misc.ItemWrapper;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;

public enum FoodType implements ItemWrapper {
	TUNA(ItemNames.TUNA, 10),
	BASS(ItemNames.BASS, 13),
	SWORDFISH(ItemNames.SWORDFISH, 14),
	LOBSTER(ItemNames.LOBSTER, 12),
	SHARK(ItemNames.SHARK, 20, true),
	MANTA_RAY(ItemNames.MANTA_RAY, 22, true),
	SEA_TURTLE(ItemNames.SEA_TURTLE, 21, true),
	MACKEREL(ItemNames.MACKEREL, 6, true),
	PIKE(ItemNames.PIKE, 8),
	HERRING(ItemNames.HERRING, 5),
	COD(ItemNames.COD, 7),
	TROUT(ItemNames.TROUT, 7),
	SALMON(ItemNames.SALMON, 9),
	SARDINE(ItemNames.SARDINE, 4),
	ANCHOVIES(ItemNames.ANCHOVIES, 1),
	SHRIMPS(ItemNames.SHRIMPS, 3),
	BREAD(ItemNames.BREAD, 5),
	BEER(ItemNames.BEER, 1);
	
	private ItemIds item;
	private int health;
	private boolean members;
	
	FoodType(ItemIds item, int health) {
		this(item, health, false);
	}
	
	FoodType(ItemIds item, int health, boolean members) {
		this.item = item;
		this.health = health;
		this.members = members;
	}
	
	public boolean isMembers() {
		return this.members;
	}

	@Override
	public ItemIds getItem() {
		return this.item;
	}
	
	public int getHealth() {
		return this.health;
	}
}
