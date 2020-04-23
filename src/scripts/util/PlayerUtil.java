package scripts.util;

import java.util.ArrayList;
import java.util.List;

import org.tribot.api.General;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Banking;
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
import org.tribot.api2007.types.RSInterfaceMaster;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSPlayer;

import scripts.util.misc.AntiBan;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;
import scripts.util.names.internal.ItemNamesData;
import scripts.util.names.type.TrainMethod;

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
		loot(location, ItemNamesData.get(items));
	}
	
	/**
	 * Loots the position for the items specified.
	 * @param location
	 * @param items
	 */
	public static void loot( Positionable location, int...ids ) {
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
				for (int j = 0; j < ids.length; j++) {
					int id = ids[j];
					if ( id == o.getID() ) {
						if ( o.click(new String[] { "Take " + o.getDefinition().getName() }) )
							AntiBan.sleep(1000, 500);
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Loots the position for all of the items that have at least the minGP price on the GE. See {@link ItemUtil#getPrice(int)}.
	 * @param location
	 * @param minGP
	 */
	public static void lootByPrice( Positionable location, int minGP ) {
		RSGroundItem[] objs = GroundItems.getAll();
		objs = GroundItems.sortByDistance(location, objs);
		ArrayList<Integer> ids = new ArrayList<Integer>();

		// Get up desired items
		for (int i = 0; i < objs.length; i++) {
			RSGroundItem o = objs[i];
			int id = o.getID();
			int price = ItemUtil.getPrice(id);
			if ( price >= minGP ) {
				ids.add(id);
			}
		}
		
		// Loot the found items
		int[] ret = new int[ids.size()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = ids.get(i);
		}
		loot( location, ret);
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
		if (((run) && (Game.isRunOn())) || ((!run) && (!Game.isRunOn())))
			return;
		
		General.println("Current run: " + Game.isRunOn() + "  /  Setting run to: " + run);
		Options.setRunEnabled(run);
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


		RSInterfaceChild retaliateInterface = Interfaces.get(593, 34);
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
	 * Sets the desired users train method
	 */
	public static void setTrainMethod(TrainMethod trainMethod) {
		while (Banking.isBankScreenOpen()) {
			Banking.close();
			General.sleep(1000);
		}

		if (!GameTab.TABS.COMBAT.isOpen()) {
			GameTab.TABS.COMBAT.open();
			General.sleep(300, 600);
		}
		
		RSInterfaceChild[] trainInterface = {
				Interfaces.get(593, 4), // Attack
				Interfaces.get(593, 8), // Str
				Interfaces.get(593, 12), // Str
				Interfaces.get(593, 16), // Def
		};
		
		int lastIndex = 0;
		for (int i = 0; i < trainInterface.length; i++)
			if ( !trainInterface[i].isHidden() )
				lastIndex = i;
		
		switch(trainMethod) {
			case ATTACK_XP: {
				trainInterface[0].click("");
				break;
			}
			case STRENGTH_XP: {
				trainInterface[lastIndex-1].click("");
				break;
			}
			case DEFENSE_XP: {
				trainInterface[lastIndex].click("");
				break;
			}
		}

		General.sleep(300, 600);
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
		return getAmountItemsInInventory(true, check);
	}

	/**
	 * Returns the amount of a specific item type in the players inventory.
	 * @param check
	 * @return
	 */
	public static int getAmountItemsInInventory(boolean countStacks, ItemIds... check) {
		int amt = 0;
		RSItem[] items = Inventory.getAll();
		for (int i = 0; i < items.length; i++) {
			RSItem item = items[i];
			
			boolean notedMatch = false;
			for (ItemIds temp : check) {
				RSItem temp2 = new RSItem(0, temp.getIds()[0], 1, RSItem.TYPE.OTHER);
				if ( item.getDefinition().getName().equalsIgnoreCase(temp2.getDefinition().getName())) {
					notedMatch = true;
				}
			}
			
			if (ItemNames.is(item, check) || notedMatch) {
				amt += countStacks?item.getStack():1;
			}
		}

		return amt;
	}
	
	/**
	 * Returns whether the user has at least one of the specified items in the inventory.
	 * @param check
	 * @return
	 */
	public static boolean hasItemsInInventory(ItemIds...check) {
		return getAmountItemsInInventory(false, check) > 0;
	}

	/**
	 * Returns the first occurrence of a specific item in the players inventory.
	 * @param check
	 * @return
	 */
	public static RSItem getFirstItemInInventory(ItemIds... check) {
		RSItem[] items = Inventory.getAll();
		for (int i = 0; i < items.length; i++) {
			RSItem item = items[i];
			boolean notedMatch = false;
			for (ItemIds temp : check) {
				RSItem temp2 = new RSItem(0, temp.getIds()[0], 1, RSItem.TYPE.OTHER);
				if ( item.getDefinition().getName().equalsIgnoreCase(temp2.getDefinition().getName())) {
					notedMatch = true;
				}
			}
			
			if (ItemNames.is(item, check) || notedMatch) {
				return item;
			}
		}
		return null;
	}
	
	/**
	 * Equips a set of items. Returns false if no items were equipped
	 * @param bronzeArrow
	 */
	public static boolean equipItem(ItemIds... items) {
		int a = 0;
		for (int i = 0; i < items.length; i++) {

			RSItem item = PlayerUtil.getFirstItemInInventory(items[i]);
			if ( item == null )
				continue;
			
			a++;
			item.click("");
			
			if (i < items.length - 1 )
				AntiBan.sleep(500, 150);
		}
		
		return a > 0;
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
			
			if ( player.equals(Player.getRSPlayer()) )
				continue;
			
			boolean interact = player.isInteractingWithMe();
			boolean inCombat = player.isInCombat();
			boolean hasAnimation = player.getAnimation() != -1;
			boolean skulled = player.getSkullIcon() != -1;
			boolean inWilderness = (Combat.getWildernessLevel() > 0) || (getWildernessLevelBackup() > 0);
			boolean canWilderKill = canPlayerAttackUsWilderness(player);

			if ((inWilderness && skulled && canWilderKill) || 
					(inWilderness && interact) || 
					(hasAnimation && interact) ||
					(inCombat && interact)) {
				General.println(player.getName() + " is attacking us.");
				attacking.add(player);
			}
		}

		return attacking.toArray(new RSPlayer[attacking.size()]);
	}

	/**
	 * Returns a list of NPCS that are currently attacking you.
	 * @return
	 */
	public static RSNPC[] getAttackingNPCS() {
		RSNPC[] npcs = NPCs.getAll();
		
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
	 * Returns the amount of money in the players inventory.
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
		RSNPC[] attackingNPCS = getAttackingNPCS();
		List<RSNPC> attackingNPCSThatWeShouldActuallyWorryAboutPerhaps = new ArrayList<>();
		for (RSNPC npc : attackingNPCS) {
			if ( npc.getCombatLevel() > Player.getRSPlayer().getCombatLevel() ) {
				attackingNPCSThatWeShouldActuallyWorryAboutPerhaps.add(npc);
			}
		}
		
		boolean isInTimeout = System.currentTimeMillis() < DANGER_TIMEOUT;
		boolean attacked = (!attackingNPCSThatWeShouldActuallyWorryAboutPerhaps.isEmpty() && !ignoreNPC) || (getAttackingPlayers().length > 0 && !ignorePlayers);
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
		boolean npcAttack = (getAttackingNPCS().length > 0) && (includeNPC);
		boolean playerAttack = getAttackingPlayers().length > 0;
		return npcAttack || playerAttack || (Combat.isUnderAttack()&&includeNPC);
	}
	
	/**
	 * @return Whether the player is a p2p member or not.
	 */
	public static boolean isP2P() {
		RSInterfaceMaster root = Interfaces.get(109);
		if ( root == null )
			return false;
		
		RSInterfaceChild child = root.getChild(25);
		if ( child == null )
			return false;
		
		String text = child.getText();
		
		if ( text.contains("None") )
			return false;
		
		if ( text.contains(" days ") )
			return true;
		
		return false;
	}
}
