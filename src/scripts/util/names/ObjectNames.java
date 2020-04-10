package scripts.util.names;

import com.allatori.annotations.DoNotRename;

import scripts.util.misc.NameFormatter;

@DoNotRename
public enum ObjectNames {
	ANVIL(2097),
	BANK_BOOTH(10083, 7409),
	DOOR(131, 137, 138, 139, 140, 141, 142, 143, 144, 145, 9710),
	FURNACE(24009, 10082),
	COMPOSITE_HEAP(152),
	FOUNTAIN(153),
	FIRE(26185),
	BOOKCASE_INTERACT(155, 156),
	LEVER(146, 147, 148, 149, 150, 151, 160),
	ORE_NONE(11390, 11391),
	ORE_COPPER(11161, 10943, 10079),
	ORE_TIN(11360, 11361, 10080 ),
	ORE_IRON(11365, 11364),
	ORE_COAL(11367, 11366),
	ORE_GOLD(11371, 11370),
	ORE_ADAMANT(7493, 7460),
	WHEAT(15506, 15507),
	POLL_BOOTH(26815),
	ONION(3366),
	CABBAGE(1161),
	CAULDRON(2024),
	RANGE(26181, 9736),
	TREE(9730),
	HOPPER(24961),
	HOPPER_CONTROLS(24964),
	FLOWER_BIN(1781),
	STAIRCASE(12536, 12537, 12538, 16672, 16673, 16671, 11796, 17385, 11799, 24074, 24072, 24077, 24078, 24070, 24071, 881, 882, 6434, 6435),
	SPINNING_WHEEL(14889),
	LADDER(17385, 132, 133, 2417, 2418, 16683, 12964, 12965, 12966, 9726),
	NULL(23849),
	GATE(9470, 9708, 9718),
	COFFIN(2145),
	ALTAR(2146),
	COFFIN_OPENED(15061);

	private int[] id;

	private ObjectNames(int... id) { this.id = id; }

	public int[] getId() {
		return this.id;
	}

	public String getName() {
		return NameFormatter.get(toString());
	}

	public static ObjectNames find(String formattedName) {
		ObjectNames[] types = values();
		for (int i = 0; i < types.length; i++) {
			if (types[i].getName().equals(formattedName)) {
				return types[i];
			}
		}

		return null;
	}
}
