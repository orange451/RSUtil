package scripts.util.names;

import org.tribot.api2007.types.RSObject;

import com.allatori.annotations.DoNotRename;

import scripts.util.misc.NameFormatter;

@DoNotRename
public enum ObjectNames {
	ANVIL(2097),
	BANK_BOOTH(10083, 7409),
	DOOR(131, 137, 138, 139, 140, 141, 142, 143, 144, 145, 9710),
	FURNACE("Furnace", 24009, 10082),
	COMPOSITE_HEAP(152),
	FOUNTAIN(153),
	FIRE(26185),
	BOOKCASE_INTERACT(155, 156),
	LEVER(146, 147, 148, 149, 150, 151, 160),
	ORE_NONE(11390, 11391, 26665),
	ORE_CLAY(11362, 11363),
	ORE_COPPER(11161, 10943, 10079),
	ORE_TIN(11360, 11361, 10080 ),
	ORE_IRON(11365, 11364),
	ORE_COAL(11367, 11366),
	ORE_GOLD(11371, 11370),
	ORE_MITHRIL(11372, 11373),
	ORE_ADAMANT(7493, 7460, 11375),
	ORE_PAY_DIRT("Ore vein", 26662, 26663, 26664, 26661, 26657),
	WHEAT(15506, 15507),
	POLL_BOOTH(26815),
	ONION(3366),
	CABBAGE(1161),
	CAULDRON(2024),
	RANGE("Range", 26181, 9736, 114),
	TREE(9730, 1278, 1276),
	OAK(10820),
	WILLOW(10833, 10829, 10819, 10831),
	YEW(10828, 10822, 10823),
	HOPPER(24961, 26674),
	HOPPER_CONTROLS(24964),
	FLOWER_BIN(1781),
	STAIRCASE(12536, 12537, 12538, 16672, 16673, 16671, 11796, 17385, 11799, 24074, 24072, 24077, 24078, 24070, 24071, 881, 882, 6434, 6435),
	SPINNING_WHEEL(14889),
	LADDER(17385, 132, 133, 2417, 2418, 16683, 12964, 12965, 12966, 9726),
	NULL(23849),
	GATE(9470, 9708, 9718),
	GRAND_EXCHANGE_BOOTH(10061),
	COFFIN(2145),
	ALTAR(2146),
	COFFIN_OPENED(15061),
	SACK(26688),
	STRUT("Strut", 26669),
	CRATE("Crate", 357),
	BROKEN_STRUT("Broken strut", 26670), 
	WATERPUMP("Waterpump", 24004), 
	LARGE_DOOR("Large Door", 3489, 3940), 
	TRAP_DOOR("Trapdoor", 1581),
	CADAVA_BUSH("Cadava bush"),
	;

	private int[] id;
	private String name;
	
	private ObjectNames(String name, int...id) {
		this.name = name;
		this.id = id;
	}
	
	private ObjectNames(String name) {
		this(name, null);
	}

	private ObjectNames(int... id) {
		this(null, id);
	}

	public int[] getIds() {
		return this.id;
	}

	public String getName() {
		return name == null ? NameFormatter.formatName(toString()) : name;
	}

	public static ObjectNames match(String formattedName) {
		ObjectNames[] types = values();
		for (int i = 0; i < types.length; i++) {
			if (types[i].getName().equalsIgnoreCase(formattedName)) {
				return types[i];
			}
		}

		return null;
	}
	
	public static ObjectNames match(RSObject object) {
		ObjectNames o = match(object.getDefinition().getName());
		if ( o != null )
			return o;
		
		o = match(object.getID());
		return o;
	}
	
	public static ObjectNames match(int id) {
		ObjectNames[] types = values();
		for (ObjectNames obj : types) {
			int[] t = obj.getIds();
			for (int i = 0; i < t.length; i++) {
				if (t[i] == id)
					return obj;
			}
		}
		
		return null;
	}
	
	public static int[] get(ObjectNames... itemNames) {
		int len = 0;
		for (int i = 0 ;i < itemNames.length; i++) {
			len += itemNames[i].getIds().length;
		}
		
		int a = 0;
		int[] ret = new int[len];
		for (int i = 0; i < itemNames.length; i++) {
			int[] t = itemNames[i].getIds();
			for (int j = 0; j < t.length; j++) {
				ret[a++] = t[j];
			}
		}
		
		return ret;
	}
}
