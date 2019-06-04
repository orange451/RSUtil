package scripts.util.names;

import com.allatori.annotations.DoNotRename;

@DoNotRename
public enum NPCNames {
	ACCOUNT_GUIDE(false, 3310),
	AGGIE(false, 4284),
	ALKHARID_WARRIOR(true, 3103),
	BARBARIAN(true, 3056, 3057, 3058, 3059, 3060, 3061, 3064, 3065, 3066, 3067, 3069, 3070, 3071, 3072, 3102),
	BARTENDER(false, 1312),
	BANKER(false, 3318),
	BROTHER_BRACE(false, 3319),
	BETTY(false, 1052),
	BLACK_KNIGHT(true, 2874),
	BIG_FROG(true, 2144),
	CHICKEN(true, 2819, 2820, 2692, 2693, 3316),
	COW(true, 2805, 2806, 2808, 2809),
	COOK(false, 4626),
	COMBAT_INSTRUCTOR(false, 3307),
	DAIRY_COW(false, 2691),
	DARK_WIZARD(true, 5086, 5087, 5088, 5089),
	DWARF(true, 290),
	DR_HARLOW(false, 3480),
	FISHING_SPOT(false, 3317),
	FROG(true, 2145),
	FRED_THE_FARMER(false, 732),
	GIANT_RAT(true, 3313, 2863, 2856, 2864),
	GIANT_FROG(true, 2143),
	GOBLIN(true, 3029, 3030, 3031, 3032, 3033, 3034, 3035, 3036),
	GUARD(true, 3010, 3011, 3094, 3269, 3271, 3272),
	GIELINOR_GUIDE(false, 3308),
	GHOST(true, 85, 89, 92),
	HETTY(false, 4619),
	IMP(true, 5007),
	MAN(true, 3015, 3078, 3079, 3080, 3085, 3101, 3264, 3279, 5213),
	MAGIC_INSTRUCTOR(false, 3309),
	MASTER_CHEF(false, 3305),
	MINING_INSTRUCTOR(false, 3311),
	PROFESSOR_ODENSTEIN(false, 3562),
	QUEST_GUIDE(false, 3312),
	RAT(true, 2855, 2854),
	RAM(true, 1262, 1263, 1264),
	SCORPION(true, 3024),
	SHEEP(false, 2794, 2795, 2796, 2800, 2801, 2802),
	SKELETON(true, 70, 71, 72, 74, 75, 76, 77, 78, 79),
	SHOP_KEEPER(false, 516, 517),
	SQUIRE(false, 4737),
	SURVIVAL_EXPERT(false, 8503),
	THIEF(true, 5217),
	VERONICA(false, 3561),
	WIZARD(true, 3097),
	WHITE_KNIGHT(true, 1798, 1799),
	WYSON(false, 3253),
	ZOMBIE(true, 39, 40, 55, 56, 57, 58);

	private int[] id;
	private boolean attackable;

	private NPCNames(boolean attackable, int... id) { this.attackable = attackable;
	this.id = id;
	}

	public boolean isAttackable() {
		return this.attackable;
	}

	public int[] getIds() {
		return this.id;
	}
}
