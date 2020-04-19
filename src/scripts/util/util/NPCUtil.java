package scripts.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.tribot.api.General;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSNPCDefinition;
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
		while ((npc.getModel() != null) && (steps < 500)) {
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
	 * Returns whether this npc is currently attackable. An NPC is attackable if they have an attack option, are not engaging in battle with another player, and have at least 1% health.
	 * @param npc
	 * @return
	 */
	public static boolean canAttack(RSNPC npc) {
		boolean interacting = npc.getInteractingIndex() != -1;
		boolean interactingWithMe = npc.getInteractingCharacter() != null && npc.getInteractingCharacter().equals(Player.getRSPlayer());
		
		boolean atkble = false;
		RSNPCDefinition definition = npc.getDefinition();
		if ( definition != null ) {
			String[] actions = definition.getActions();
			for (int i = 0; i < actions.length; i++) {
				String action = actions[i];
				if ( action.contains("Attack") )
					atkble = true;
			}
			
			if ( !atkble )
				return false;
		}
		
		if ( interacting && !interactingWithMe )
			return false;
		
		if ( npc.getHealthPercent() <= 0 )
			return false;
		
		return true;
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
				if ( canAttack(npc) ) {
					attackable.add(npc);
				}
			}
		}
		
		// Filter out nonreachable NPCS
		DPathNavigator pather = new DPathNavigator();
		Map<RSNPC,Double> pathDistance = new HashMap<RSNPC,Double>();
		for (int i = 0; i < attackable.size(); i++) {
			RSNPC npc = attackable.get(i);
			
			// Get the path
			RSTile[] path = pather.findPath(npc.getPosition());
			if ( path == null || path.length > 50 )
				attackable.remove(i--);
			
			// Store path distance
			pathDistance.put(npc, (double) path.length);
		}
		
		// Sort based on distance
		try {
			Collections.sort(attackable, new Comparator<RSNPC>() {
				@Override
				public int compare(RSNPC arg0, RSNPC arg1) {
					Double d1 = pathDistance.get(arg0);
					Double d2 = pathDistance.get(arg1);
	
					double u1 = d1 != null ? d1.doubleValue() : Double.MAX_VALUE;
					double u2 = d2 != null ? d2.doubleValue() : Double.MAX_VALUE;
					
					return (u1>u2)?(1):((u2>u1)?(-1):(0));
				}
			});
		} catch(Exception e) {
			//
		}
	
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
		
		// Find all npcs that match our ids
		RSNPC[] npcs = NPCs.getAll();
		for (int i = 0; i < npcs.length; i++) {
			RSNPC npc = npcs[i];
			if (npc != null) {
				String npcName = npc.getName();
				if ( npcName != null ) {
					npcName = npcName.toLowerCase();
					
					if ( npcTypes != null ) {
						for (int a = 0; a < npcTypes.length; a++) {
							String n1 = npcTypes[a].getName().toLowerCase();
							
							if ( n1.equalsIgnoreCase(npcName) ) {
								ret.add(npcs[i]);
								break;
							}
							
							if ( npcTypes[a].hasId(npc.getID()) ) {
								ret.add(npcs[i]);
								break;
							}
						}
					} else {
						ret.add(npcs[i]);
					}
				}
			}
		}

		// Sort based on distance
		Collections.sort(ret, new Comparator<RSNPC>() {
			@Override
			public int compare(RSNPC o1, RSNPC o2) {
				double u1 = o1.getPosition().distanceToDouble(Player.getPosition());
				double u2 = o2.getPosition().distanceToDouble(Player.getPosition());
				
				return (u1-u2>0)?(1):((u2-u1>0)?(-1):(0));
			}
		});

		// Return array
		return ret.toArray(new RSNPC[ret.size()]);
	}
	
	/**
	 * Returns NPCS of a specific type that also contain a specific right-click action.
	 * @param sheep
	 * @param string
	 * @return
	 */
	public static RSNPC[] getNPCSWithAction(String option, NPCNames... types) {
		RSNPC[] npcs = getNPCS(types);
		ArrayList<RSNPC> npcsA = new ArrayList<RSNPC>(Arrays.asList(npcs));
		for (int i = 0; i < npcsA.size(); i++) {
			RSNPC t = npcsA.get(i);
			if ( !hasAction(t, option) ) {
				npcsA.remove(i--);
			}
		}
		
		return npcsA.toArray(new RSNPC[npcsA.size()]);
	}

	/**
	 * Returns whether an npc has a specific right-click action
	 * @param npc
	 * @param option
	 * @return
	 */
	public static boolean hasAction(RSNPC npc, String option) {
		RSNPCDefinition definition = npc.getDefinition();
		if ( definition == null )
			return false;
		
		String[] actions = definition.getActions();
		for (int i = 0; i < actions.length; i++) {
			String a = actions[i];
			if ( a.equalsIgnoreCase(option) )
				return true;
		}
		
		return false;
	}

	public static boolean interactWithFirstNPC(String action, NPCNames npcs) {
		RSNPC[] npcList = NPCUtil.getNPCSWithAction("Exchange", NPCNames.GRAND_EXCHANGE_CLERK);
		if ( npcList == null || npcList.length == 0 )
			return false;
		
		return npcList[0].click(new String[] { action });
	}
}
