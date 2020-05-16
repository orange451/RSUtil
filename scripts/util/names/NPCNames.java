package scripts.util.names;

import com.allatori.annotations.DoNotRename;

import scripts.util.misc.NameFormatter;

@DoNotRename
public enum NPCNames {
	ACCOUNT_GUIDE(false, "Account Guide", 3),
	AUBURY(false),
	AGGIE(false, Locations.DRAYNOR_AGGIE),
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
	DAIRY_COW(false, "Dairy Cow", 0, 1172),
	DARK_WIZARD(true),
	DREZEL(false),
	DORIC(false),
	DUKE_HORACIO(false),
	DWARF(true),
	DR_HARLOW(false),
	ELLIS(false),
	FATHER_AERECK(false),
	FATHER_URHNEY(false),
	FATHER_LAWRENCE(false),
	FISHING_SPOT(false),
	ROD_FISHING_SPOT(false),
	FROG(true),
	FRED_THE_FARMER(false),
	GIANT_RAT(true, "Giant rat", 3),
	GIANT_FROG(true),
	GOBLIN(true),
	GUARD(true),
	GIELINOR_GUIDE(false),
	GHOST(true),
	HETTY(false),
	HILL_GIANT(true),
	IMP(true),
	KING_ROALD(false),
	MAN(true, "Man", 2, 3111, 3112, 3106, 3107, 3108),
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
	SHOP_KEEPER(false, "Shop keeper", 3, 2815, 2816),
	SQUIRE(false),
	SURVIVAL_EXPERT(false),
	TEMPLE_GUARDIAN(true),
	THIEF(true),
	THURGO(false),
	VERONICA(false),
	WIZARD(true),
	WHITE_KNIGHT(true),
	WYSON(false, "Wyson the gardener", Locations.FALADOR_GARDEN_SHED),
	ZOMBIE(true),
	GRAND_EXCHANGE_CLERK(false),
	ROMEO(false, "Romeo"),
	JULIET(false, "Juliet"),
	APOTHECARY(false, "Apothecary"),
	MINOTAUR(true, "Minotaur");

	private boolean attackable;
	private String name;
	private int[] ids = new int[0];
	private int combatLevel;
	private Locations knownLocation;

	private NPCNames(boolean attackable, String name, int combatLevel, Locations knownLocation, int...ids) {
		this.attackable = attackable;
		this.ids = ids;
		this.name = name;
		this.combatLevel = combatLevel;
		this.knownLocation = knownLocation;
	}

	private NPCNames(boolean attackable, String name, int combatLevel, int...ids) {
		this(attackable, name, combatLevel, null, ids);
	}
	
	private NPCNames(boolean attackable) {
		this(attackable, 3);
	}
	
	private NPCNames(boolean attackable, Locations location) {
		this(attackable, 3, location);
	}

	private NPCNames(boolean attackable, int combatLevel) {
		this(attackable, combatLevel, null);
	}
	
	private NPCNames(boolean attackable, int combatLevel, int...ids) {
		this(attackable, null, combatLevel, null, ids);
		
		this.name = NameFormatter.formatName(toString());
	}
	
	private NPCNames(boolean attackable, int combatLevel, Locations knownLocation, int...ids) {
		this(attackable, null, combatLevel, knownLocation, ids);
		
		this.name = NameFormatter.formatName(toString());
	}
	
	private NPCNames(boolean attackable, String name, int combatLevel) {
		this(attackable, name, combatLevel, null);
	}
	
	private NPCNames(boolean attackable, String name) {
		this(attackable, name, 3);
	}
	
	private NPCNames(boolean attackable, String name, Locations location) {
		this(attackable, name, 3, location);
	}
	
	public Locations getKnownLocation() {
		return this.knownLocation;
	}

	public boolean isAttackable() {
		return this.attackable;
	}
	
	public int getCombatLevel() {
		return this.combatLevel;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean hasId(int id) {
		if ( ids == null || ids.length == 0 )
			return false;
		
		for (int i = 0; i < ids.length; i++) {
			if ( ids[i] == id ) {
				return true;
			}
		}
		
		return false;
	}
}
