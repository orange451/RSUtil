package scripts.util.names;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSNPC;

public enum NPCNames {
	ALKHARID_WARRIOR(3103),
	BARBARIAN(3057, 3058, 3059, 3060, 3064, 3066, 3067, 3069, 3070, 3102),
	BLACK_KNIGHT(2874),
	CHICKEN(2692, 2693),
	COW(2805, 2806, 2808, 2809),
	DWARF(290),
	FROG(2145),
	FROG_BIG(2144),
	GIANT_RAT(2863, 2856, 2864),
	GOBLIN(3029, 3030, 3031, 3032, 3033, 3034, 3035, 3036),
	GUARD(3010, 3011, 3094, 3269, 3271, 3272),
	IMP(5007),
	MAN(3015, 3078, 3079, 3080, 3085, 3101, 3264, 3279, 5213),
	RAT(2855, 2854),
	SCORPION(3024),
	THIEF(5217),
	WIZARD(3097),
	ZOMBIE(1); //TODO get ID

	private int[] id;
	private NPCNames(int... id) {
		this.id = id;
	}

	public int[] getIds() {
		return id;
	}

	public static RSNPC[] getNPCS( NPCNames npcType ) {
		ArrayList<RSNPC> ret = new ArrayList<RSNPC>();
		// Get all NPCs
		RSNPC[] npcs = NPCs.getAll();
		int[] types = npcType.getIds();
		for (int i = 0; i < npcs.length; i++) {
			RSNPC npc = npcs[i];
			if ( npc != null && npc.getName() != null ) {
				for (int a = 0; a < types.length; a++ ) {
					if ( npc.getID() == types[a] ) {
						ret.add(npcs[i]);
						break;
					}
				}
			}
		}
		Collections.sort(ret, new Comparator<RSNPC>() {

			@Override
			public int compare(RSNPC o1, RSNPC o2) {
				return o1.getPosition().distanceTo(Player.getPosition()) < o2.getPosition().distanceTo(Player.getPosition()) ? -1 : 1;
			}

		});

		RSNPC[] ret2 = new RSNPC[ret.size()];
		for (int i = 0; i < ret.size(); i++) {
			ret2[i] = ret.get(i);
		}

		return ret2;
	}

	/**
	 * Returns the name of this enum with Title Casing.
	 * <br>
	 * Additionally the _ characters are replaced with a blank space.
	 * @return
	 */
	public String getName() {
	    String[] arr = toString().toLowerCase().replace("_", " ").split(" ");
	    StringBuffer sb = new StringBuffer();

	    for (int i = 0; i < arr.length; i++) {
	        sb.append(Character.toUpperCase(arr[i].charAt(0)))
	            .append(arr[i].substring(1)).append(" ");
	    }
	    return sb.toString().trim();
	}
}
