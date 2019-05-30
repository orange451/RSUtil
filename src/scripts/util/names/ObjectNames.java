package scripts.util.names;

public enum ObjectNames {
	ANVIL(new int[] { 2097 }), 
	BANK_BOOTH(new int[] { 7409 }), 
	DOOR(new int[] { 131, 137, 138, 139, 140, 141, 142, 143, 144, 145 }), 
	FURNACE(new int[] { 24009 }), 
	COMPOSITE_HEAP(new int[] { 152 }), 
	FOUNTAIN(new int[] { 153 }), 
	BOOKCASE_INTERACT(new int[] { 155, 156 }), 
	LEVER(new int[] { 146, 147, 148, 149, 150, 151, 160 }), 
	ORE_NONE(new int[] { 11390, 11391 }), 
	ORE_COPPER(new int[] { 11161, 10943 }), 
	ORE_TIN(new int[] { 11360, 11361  }), 
	ORE_IRON(new int[] { 11365, 11364 }), 
	ORE_COAL(new int[] { 11367, 11366 }), 
	ORE_GOLD(new int[] { 11371, 11370 }), 
	ORE_ADAMANT(new int[] { 7493, 7460 }), 
	WHEAT(new int[] { 15506, 15507 }), 
	ONION(new int[] { 3366 }), 
	CABBAGE(new int[] { 1161 }), 
	CAULDRON(new int[] { 2024 }), 
	RANGE(new int[] { 26181 }), 
	HOPPER(new int[] { 24961 }), 
	HOPPER_CONTROLS(new int[] { 24964 }), 
	FLOWER_BIN(new int[] { 1781 }), 
	STAIRCASE(new int[] { 12536, 12537, 12538, 16672, 16673, 16671, 11796, 17385, 11799, 24074, 24072, 24077, 24078, 24070, 24071, 881, 882, 6434, 6435 }), 
	SPINNING_WHEEL(new int[] { 14889 }), 
	LADDER(new int[] { 17385, 132, 133, 2417, 2418, 16683, 12964, 12965, 12966 }), 
	NULL(new int[] { 23849 });

	private int[] id;

	private ObjectNames(int... id) { this.id = id; }

	public int[] getId() {
		return this.id;
	}

	public String getName() {
		String[] arr = toString().toLowerCase().replace("_", " ").split(" ");
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < arr.length; i++) {
			sb.append(Character.toUpperCase(arr[i].charAt(0))).append(arr[i].substring(1)).append(" ");
		}
		return sb.toString().trim();
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
