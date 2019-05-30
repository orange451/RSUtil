package scripts.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.tribot.api.General;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.util.DPathNavigator;

import scripts.util.names.NPCNames;

public class NPCUtil {
	
	/**
	 * Sleeps the script until the given NPC finishes dying.
	 * @param npc
	 */
	public static void waitForDeath(RSNPC npc) {
		if (npc == null) {
			return;
		}
		int steps = 0;
		while ((npc.getModel() != null) && (steps < 50)) {
			steps++;
			General.sleep(100L);
		}
	}

	/**
	 * Returns the first npc of a given npc type near the player.
	 * @param npcType
	 * @return
	 */
	public static RSNPC getFirstNPC(NPCNames npcType) {
		RSNPC[] t = getNPCS(npcType);
		if ((t != null) && (t.length > 0)) {
			return t[0];
		}
		return null;
	}

	/**
	 * Returns a list of attackable NPCs with a specific npcType.<br>
	 * ALL NPCs returned are able to pathfind to<br>
	 * AND are currently not in combat.
	 * @param npcType
	 * @return
	 */
	public static RSNPC[] findAttackableNPCs(NPCNames... npcType) {
		RSNPC[] npcs = getNPCS(npcType);
		ArrayList<RSNPC> attackable = new ArrayList<RSNPC>();
	
		// Create initial list of attackable NPCS
		for (int i = 0; i < npcs.length; i++) {
			RSNPC npc = npcs[i];
	
			if ((npc != null) && (npc.getName() != null)) {
				if ((!npc.isInCombat()) && (npc.getInteractingIndex() == -1)) {
					attackable.add(npc);
				}
			}
		}
		
		// Filter out nonreachable NPCS
		DPathNavigator pather = new DPathNavigator();
		HashMap<RSNPC,Double> pathDistance = new HashMap<RSNPC,Double>();
		for (int i = 0; i < attackable.size(); i++) {
			RSNPC npc = attackable.get(i);
			
			// Get the path
			RSTile[] path = pather.findPath(npc.getPosition());
			if ( path == null || path.length > 20 )
				attackable.remove(i--);
			
			// Store path distance
			pathDistance.put(npc, (double) path.length);
		}
		
		// Sort based on distance
		Collections.sort(attackable, new Comparator<RSNPC>() {
			@Override
			public int compare(RSNPC arg0, RSNPC arg1) {
				Double d1 = pathDistance.get(arg0);
				Double d2 = pathDistance.get(arg1);

				double u1 = d1 != null ? d1.doubleValue() : Double.MAX_VALUE;
				double u2 = d2 != null ? d2.doubleValue() : Double.MAX_VALUE;
				
				return (u1-u2>0)?(1):((u2-u1>0)?(-1):(0));
			}
		});
	
		// return RSNPC array
		return attackable.toArray(new RSNPC[attackable.size()]);
	}

	/**
	 * Return NPCS of a specific type.
	 * @param npcTypes
	 * @return
	 */
	public static RSNPC[] getNPCS(NPCNames... npcTypes) {
		ArrayList<RSNPC> ret = new ArrayList<RSNPC>();

		// Combine all ids into 1 array
		int len = 0;
		int k = 0;
		for (int i = 0; i < npcTypes.length; i++)
			len += npcTypes[i].getIds().length;
		int[] types = new int[len];
		for (int i = 0; i < npcTypes.length; i++) {
			int[] subids = npcTypes[i].getIds();
			for (int j = 0; j < subids.length; j++) {
				types[k++] = subids[j];
			}
		}
		
		// Find all npcs that match our ids
		RSNPC[] npcs = NPCs.getAll();
		for (int i = 0; i < npcs.length; i++) {
			RSNPC npc = npcs[i];
			if ((npc != null) && (npc.getName() != null)) {
				for (int a = 0; a < types.length; a++) {
					if (npc.getID() == types[a]) {
						ret.add(npcs[i]);
						break;
					}
				}
			}
		}

		// Sort based on distance
		Collections.sort(ret, new Comparator<RSNPC>() {
			@Override
			public int compare(RSNPC o1, RSNPC o2) {
				return o1.getPosition().distanceTo(Player.getPosition()) <= o2.getPosition().distanceTo(Player.getPosition()) ? -1 : 1;
			}
		});

		// Return array
		return ret.toArray(new RSNPC[ret.size()]);
	}
}
