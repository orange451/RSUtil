package scripts.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSNPC;

public enum NPCNames {
	RAT(2855, 2854),
	GIANT_RAT(2863, 2856);

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
}
