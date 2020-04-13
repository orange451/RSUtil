package scripts.util.names;

import com.allatori.annotations.DoNotRename;

import scripts.util.misc.NameFormatter;

@DoNotRename
public enum NPCNames {
	ACCOUNT_GUIDE(false, "Account Guide"),
	AUBURY(false),
	AGGIE(false),
	ALKHARID_WARRIOR(true),
	BARBARIAN(true),
	BARTENDER(false),
	BANKER(false),
	BROTHER_BRACE(false),
	BETTY(false),
	BLACK_KNIGHT(true),
	BIG_FROG(true),
	CHICKEN(true),
	COW(true),
	COOK(false),
	COMBAT_INSTRUCTOR(false),
	CHAOS_DRUID(true),
	DAIRY_COW(false, 1172),
	DARK_WIZARD(true),
	DREZEL(false),
	DUKE_HORACIO(false),
	DWARF(true),
	DR_HARLOW(false),
	ELLIS(false),
	FATHER_AERECK(false),
	FATHER_URHNEY(false),
	FISHING_SPOT(false),
	FROG(true),
	FRED_THE_FARMER(false),
	GIANT_RAT(true),
	GIANT_FROG(true),
	GOBLIN(true),
	GUARD(true),
	GIELINOR_GUIDE(false),
	GHOST(true),
	HETTY(false),
	HILL_GIANT(true),
	IMP(true),
	KING_ROALD(false),
	MAN(true),
	MAGIC_INSTRUCTOR(false),
	MASTER_CHEF(false),
	MINING_INSTRUCTOR(false),
	MONK_OF_ZAMORAK(true),
	PROFESSOR_ODDENSTEIN(false),
	QUEST_GUIDE(false),
	RAT(true),
	RAM(true),
	RELDO(false),
	RESTLESS_GHOST(false),
	SEDRIDOR(false),
	SCORPION(true),
	SHEEP(false),
	SKELETON(true),
	SHOP_KEEPER(false),
	SQUIRE(false),
	SURVIVAL_EXPERT(false),
	TEMPLE_GUARDIAN(true),
	THIEF(true),
	THURGO(false),
	VERONICA(false),
	WIZARD(true),
	WHITE_KNIGHT(true),
	WYSON(false),
	ZOMBIE(true);

	private boolean attackable;
	private String name;
	private int[] ids = new int[0];
	
	private NPCNames(boolean attackable) {
		this.attackable = attackable;
		this.name = NameFormatter.get(toString());
	}
	
	private NPCNames(boolean attackable, int...ids) {
		this.attackable = attackable;
		this.ids = ids;
		this.name = NameFormatter.get(toString());
	}
	
	private NPCNames(boolean attackable, String name, int...ids) {
		this.attackable = attackable;
		this.ids = ids;
		this.name = name;
	}
	
	private NPCNames(boolean attackable, String name) {
		this.attackable = attackable;
		this.name = name;
	}

	public boolean isAttackable() {
		return this.attackable;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean hasId(int id) {
		for (int i = 0; i < ids.length; i++) {
			if ( ids[i] == id ) {
				return true;
			}
		}
		
		return false;
	}
}
