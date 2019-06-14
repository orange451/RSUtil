package scripts.util;

import java.util.ArrayList;

import org.tribot.api.General;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Options;
import org.tribot.api2007.Player;
import org.tribot.api2007.Players;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSCharacter;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSPlayer;

import scripts.util.misc.AntiBan;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;

public class PlayerUtil {
	private static long DANGER_TIMEOUT;
	private static int HP_EAT_AT = -1;
	
	private static boolean ignoreNPC = false;
	private static boolean ignorePlayers = false;

	/**
	 * Returns whether the players inventory is empty.
	 * @return
	 */
	public static boolean isInventoryEmpty() {
		RSItem[] items = Inventory.getAll();
		return (items != null ? items.length : 0) == 0;
	}

	/**
	 * Returns whether the players inventory is full.
	 * @return
	 */
	public static boolean isInventoryFull() {
		RSItem[] items = Inventory.getAll();
		return (items != null ? items.length : 0) == 28;
	}
	
	/**
	 * Loots the position for the items specified.
	 * @param location
	 * @param items
	 */
	public static void loot( Positionable location, ItemIds... items) {
		RSGroundItem[] objs = GroundItems.getAll();
		objs = GroundItems.sortByDistance(location, objs);

		// Pick up desired items
		for (int i = 0; i < objs.length; i++) {
			RSGroundItem o = objs[i];
			
			// Stop picking up items if we're full
			if ( Inventory.isFull() ) {
				return;
			}

			// Pick up
			if (o.getPosition().distanceTo(location) <= 2) {
				if (items.length == 0 || ItemNames.is(o, items)) {
					if ( o.click(new String[] { "Take " + o.getDefinition().getName() }) )
						AntiBan.sleep(1000, 500);
				}
			}
		}
	}

	/**
	 * Returns whether the players inventory contains at least 1 food item.
	 * @return
	 */
	public static boolean hasFood() {
		RSItem[] items = Inventory.getAll();
		for (int i = 0; i < items.length; i++) {
			RSItem item = items[i];
			if (ItemUtil.isFood(item)) {
				if ( ItemUtil.hasAction(item, "eat") ) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Player eats first food item in their inventory.
	 * @return Successfully ate food
	 */
	public static boolean eatFood() {
		RSItem[] items = Inventory.getAll();
		for (int i = 0; i < items.length; i++) {
			RSItem item = items[i];
			if (ItemUtil.isFood(item)) {
				if ( ItemUtil.hasAction(item, "eat") ) {
					if (item.click(new String[] { "eat" })) {
						HP_EAT_AT = getHPToEatAt();
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * Returns what HP the player should eat food at. This number constantly changes.
	 * @return
	 */
	public static int getHPToEatAt() {
		int timeout = 0;
		while ((timeout < 96) && (HP_EAT_AT < getMaxHealth() * 0.6F)) {
			HP_EAT_AT = (int)(AntiBan.generateEatAtHP() * 0.01F * getMaxHealth());
			if (HP_EAT_AT < getMaxHealth() * 0.15F) {
				HP_EAT_AT = (int)(getMaxHealth() * 0.15F);
			}

			timeout++;
		}
		if (timeout >= 96) {
			HP_EAT_AT = (int)(getMaxHealth() * 0.5D);
		}
		
		// Minimum eat alt.
		if ( HP_EAT_AT < 4 )
			HP_EAT_AT = 4;
		
		return HP_EAT_AT;
	}

	/**
	 * Returns whether the player should eat.
	 * @return
	 */
	public static boolean needsToEat() {
		if (HP_EAT_AT < 1)
			getHPToEatAt();
		return getHealth() <= getHPToEatAt();
	}

	/**
	 * Returns whether the player is dead or not.
	 * @return
	 */
	public static boolean isDead() {
		return getHealth() <= 0;
	}

	/**
	 * Returns the current amount of health the player has.
	 * @return
	 */
	public static int getHealth() {
		return Skills.getCurrentLevel(Skills.SKILLS.HITPOINTS);
	}

	/**
	 * Returns the max health the player has.
	 * @return
	 */
	public static int getMaxHealth() {
		return Skills.getActualLevel(Skills.SKILLS.HITPOINTS);
	}
	
	/**
	 * Sets the run toggle for the player.
	 * @param run
	 */
	public static void setRun(boolean run) {
		if (((run) && (Game.isRunOn())) || ((!run) && (!Game.isRunOn()))) {
			return;
		}
		General.println("Current run: " + Game.isRunOn() + "  /  Setting run to: " + run);
		Options.setRunOn(run);
	}

	/**
	 * Toggles the players autoretaliate option.
	 * @param retaliate
	 */
	public static void setAutoRetaliate(boolean retaliate) {
		GameTab.TABS returnTab = GameTab.getOpen();

		if (!GameTab.TABS.COMBAT.isOpen()) {
			GameTab.TABS.COMBAT.open();
			General.sleep(300, 600);
		}


		RSInterfaceChild retaliateInterface = Interfaces.get(593, 33);
		if (retaliateInterface == null) {
			return;
		}

		String text = retaliateInterface.getText();
		if (text == null) {
			return;
		}

		if (((text.contains("(On)")) && (!retaliate)) || ((text.contains("(Off)")) && (retaliate))) {
			retaliateInterface.click(new String[] { "" });
			General.sleep(400, 700);
		}


		returnTab.open();
	}

	/**
	 * Returns the amount of run energy the player currently has.
	 * @return
	 */
	public static int getRunEnergy() {
		RSInterfaceChild runText = Interfaces.get(160, 23);
		if (runText == null) {
			return 0;
		}

		String text = runText.getText();
		int energy;
		try {
			energy = Integer.parseInt(text);
		} catch (Exception e) {
			energy = 0;
		}

		return energy;
	}

	/**
	 * Returns the amount of a specific item type in the players inventory.
	 * @param check
	 * @return
	 */
	public static int getAmountItemsInInventory(ItemIds... check) {
		int amt = 0;
		RSItem[] items = Inventory.getAll();
		for (int i = 0; i < items.length; i++) {
			RSItem item = items[i];
			if (ItemNames.is(item, check)) {
				amt += item.getStack();
			}
		}

		return amt;
	}

	/**
	 * Returns the first occurence of a specific item in the players inventory.
	 * @param check
	 * @return
	 */
	public static RSItem getFirstItemInInventory(ItemIds... check) {
		RSItem[] items = Inventory.getAll();
		for (int i = 0; i < items.length; i++) {
			RSItem item = items[i];
			if (ItemNames.is(item, check)) {
				return item;
			}
		}
		return null;
	}
	
	/**
	 * Returns a list of all Players interacting with you.
	 * @return
	 */
	public static RSPlayer[] getInteractingPlayers() {
		ArrayList<RSPlayer> ret = new ArrayList<>();
		
		RSPlayer[] npcs = Players.getAll();
		for (int i = 0; i < npcs.length; i++) {
			RSPlayer npc = npcs[i];
			
			if ( npc.getInteractingCharacter() != null && npc.getInteractingCharacter().equals(Player.getRSPlayer()) )
				ret.add(npc);
		}
		
		return ret.toArray(new RSPlayer[ret.size()]);
	}
	
	/**
	 * Returns a list of all NPCS interacting with you.
	 * @return
	 */
	public static RSNPC[] getInteractingNPCS() {
		ArrayList<RSNPC> ret = new ArrayList<>();
		
		RSNPC[] npcs = NPCs.getAll();
		for (int i = 0; i < npcs.length; i++) {
			RSNPC npc = npcs[i];
			
			if ( npc.getInteractingCharacter() != null && npc.getInteractingCharacter().equals(Player.getRSPlayer()) )
				ret.add(npc);
		}
		
		return ret.toArray(new RSNPC[ret.size()]);
	}
	
	/**
	 * Convenience method to get a list of all attacking entities. See {@link #getAttackingPlayers()} and {@link #getAttackingNPCS()}
	 * @param includeNPC
	 * @return
	 */
	public static RSCharacter[] getAttackingEntities(boolean includeNPC) {
		RSPlayer[] players = getAttackingPlayers();
		RSNPC[] npcs = getAttackingNPCS();
		
		// Calculate total array len
		int totalLen = players.length;
		if ( includeNPC )
			totalLen += npcs.length;
		
		int a = 0;
		
		// Add players
		RSCharacter[] ret = new RSCharacter[totalLen];
		for (int i = 0; i < players.length; i++) {
			ret[a++] = players[i];
		}
		
		// Add NPCS
		if ( a < totalLen ) {
			for (int i = 0; i < npcs.length; i++) {
				ret[a++] = npcs[i];
			}
		}
		
		// Return
		return ret;
	}

	/**
	 * Returns a list of all attacking players.
	 * @return
	 */
	public static RSPlayer[] getAttackingPlayers() {
		RSPlayer[] players = Players.getAll();
		ArrayList<RSPlayer> attacking = new ArrayList<RSPlayer>();
		for (int i = 0; i < players.length; i++) {
			RSPlayer player = players[i];
			boolean interact = player.isInteractingWithMe();
			boolean inCombat = player.isInCombat();
			boolean hasAnimation = player.getAnimation() != -1;
			boolean skulled = player.getSkullIcon() != -1;
			boolean inWilderness = (Combat.getWildernessLevel() > 0) || (getWildernessLevelBackup() > 0);
			boolean canWilderKill = canPlayerAttackUsWilderness(player);


			if (((inWilderness) && (skulled) && (canWilderKill)) || 
					((inWilderness) && (interact)) || 
					((hasAnimation) && (interact)) || (
							(inCombat) && (interact))) {
				attacking.add(player);
			}
		}


		players = new RSPlayer[attacking.size()];
		for (int i = 0; i < players.length; i++) {
			players[i] = ((RSPlayer)attacking.get(i));
		}


		return players;
	}

	/**
	 * Returns a list of NPCS that are currently attacking you.
	 * @return
	 */
	public static RSNPC[] getAttackingNPCS() {
		RSNPC[] npcs = NPCUtil.findAttackableNPCs();
		
		ArrayList<RSNPC> attacking = new ArrayList<RSNPC>();
		for (int i = 0; i < npcs.length; i++) {
			RSNPC npc = npcs[i];
			
			if ( npc.getHealthPercent() <= 0 )
				continue;

			String[] actions = npc.getActions();
			boolean canAttack = false;
			if (actions != null) {
				for (int j = 0; j < actions.length; j++) {
					String action = actions[j];
					if (action.toLowerCase().contains("attack")) {
						canAttack = true;
					}
				}
			}

			if ((npc.isInteractingWithMe()) && (canAttack)) {
				attacking.add(npc);
			}
		}


		npcs = new RSNPC[attacking.size()];
		for (int i = 0; i < attacking.size(); i++) {
			npcs[i] = ((RSNPC)attacking.get(i));
		}


		return npcs;
	}

	/**
	 * Returns whether a player is able to attack you in the wilderness.
	 * @param player
	 * @return
	 */
	public static boolean canPlayerAttackUsWilderness(RSPlayer player) {
		int wildernessLevel = Math.max(Combat.getWildernessLevel(), getWildernessLevelBackup());


		int c1 = player.getCombatLevel();
		int c2 = Player.getRSPlayer().getCombatLevel();
		int combatDiff = Math.abs(c1 - c2);


		return combatDiff <= wildernessLevel;
	}

	/**
	 * Backup method to get the wilderness level.
	 * @return
	 */
	public static int getWildernessLevelBackup() {
		RSInterfaceChild node = Interfaces.get(90, 43);


		if (node == null) {
			return 0;
		}


		String text = node.getText();


		text = text.replace("Level: ", "");

		try
		{
			return Integer.parseInt(text);
		} catch (Exception e) {}
		return 0;
	}

	/**
	 * Returns the amount of money i nthe players inventory.
	 * @return
	 */
	public static int getAmountOfMoney() {
		RSItem coinStack = getFirstItemInInventory(ItemNames.COINS);
		if (coinStack == null) {
			return 0;
		}
		return coinStack.getStack();
	}
	
	/**
	 * Sets the ignore flags for the AntiBan wait system. <br>
	 * When performing AntiBan idle or afk tasks, the task will break with an NPC or Player attacks us. Ignoring them will prevent the task from stopping.
	 * @param ignoreNPC
	 * @param ignorePlayers
	 */
	public static void setIgnoreAttacking( boolean ignoreNPC, boolean ignorePlayers ) {
		PlayerUtil.ignoreNPC = ignoreNPC;
		PlayerUtil.ignorePlayers = ignorePlayers;
	}

	/**
	 * Returns whether the player is currently in danger.<br>
	 * Being in danger means that a player/npc is currently attacking you OR your health is less than 25% of your max health.
	 * @return
	 */
	public static boolean isInDanger() {
		boolean isInTimeout = System.currentTimeMillis() < DANGER_TIMEOUT;
		boolean attacked = (getAttackingNPCS().length > 0 && !ignoreNPC) || (getAttackingPlayers().length > 0 && !ignorePlayers);
		boolean danger = (attacked) || (getHealth() < getMaxHealth() * 0.25F);

		// You're in danger for 8 seconds 
		if ((danger) && (!isInTimeout)) {
			DANGER_TIMEOUT = System.currentTimeMillis() + 8000L;
		}

		return (danger) || (isInTimeout);
	}

	/**
	 * Returns whether the player is currently under attack.
	 * @param includeNPC
	 * @return
	 */
	public static boolean isUnderAttack(boolean includeNPC) {
		boolean isInTimeout = System.currentTimeMillis() < DANGER_TIMEOUT;
		boolean npcAttack = (getAttackingNPCS().length > 0) && (includeNPC);
		boolean playerAttack = getAttackingPlayers().length > 0;
		boolean underAttack = npcAttack || playerAttack || Combat.isUnderAttack();

		return (underAttack) || (isInTimeout);
	}
}
