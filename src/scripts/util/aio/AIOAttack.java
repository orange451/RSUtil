package scripts.util.aio;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Game;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Players;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSPlayer;
import org.tribot.api2007.util.DPathNavigator;

import scripts.util.AccurateMouse;
import scripts.util.NPCUtil;
import scripts.util.PlayerUtil;
import scripts.util.misc.AntiBan;
import scripts.util.names.Animations;
import scripts.util.names.NPCNames;

@SuppressWarnings("deprecation")
public class AIOAttack {
	public static boolean CANCEL_IF_NEAR_DEATH = true;
	public static int MAX_AFK_TIME = 25000;

	/**
	 * Will attempt to search-for and attack specific NPCs. <br>
	 * Returns (THE NPC) when the npc is found, attacked, and killed.<br>
	 * Returns (NULL) if any of the above did not complete.<br>
	 * You can query the status of the AIOStatus object to see the current status of the AIO.
	 * @param status
	 * @param types
	 * @return
	 */
	public static RSNPC attackNPC(AIOStatus status, NPCNames...types ) {
		// Requires a status
		if ( status == null )
			return null;
		
		// Ded
		if (PlayerUtil.isDead() ) {
			return null;
		}
		
		// Player attacking us
		if ( PlayerUtil.isUnderAttack(false) && Combat.getWildernessLevel() > 0 ) {
			status.setType(StatusType.EMERGENCY);
			return null;
		}
		
		// Ignore attacking npcs as marking us as in danger, since we're attacking npcs.
		PlayerUtil.setIgnoreAttacking(true, false);
		
		// Reset status
		status.setStatus("Searching for npc");
		status.setType(StatusType.IN_PROGRESS);
		
		// Try to heal
		doHealth(status);
		
		// We're in danger, stop targeting.
		if ( PlayerUtil.isInDanger() && CANCEL_IF_NEAR_DEATH ) {
			status.setType(StatusType.EMERGENCY);
			return null;
		}
		
		// Maybe we're already under attack...
		RSNPC alreadyAttacking = fixAlreadyAttack(status);
		if (alreadyAttacking != null) {
			if ( sucessfullyTargeted(status, alreadyAttacking) ) {
				return waitForAttackResult(status, alreadyAttacking);
			}
		}

		// Get list of all attackable NPCS
		RSNPC[] npcs = NPCUtil.findAttackableNPCs(types);
		if ( npcs == null || npcs.length == 0) {
			status.setType(StatusType.FAILED);
			return null;
		}
		
		// Use ABC to find next target
		RSNPC npc = (RSNPC) AntiBan.getABC().selectNextTarget(npcs);
		
		// Walk to enemy
		if ( !PathFinding.canReach(npc.getPosition(), false) ) {
			status.setStatus("Walking to npc");
			walkTo(npc);
			AntiBan.sleep(1000, 250);
		}
		
		// Wait until we're near it
		while(Player.isMoving()) {
			if ( PlayerUtil.isInDanger() && CANCEL_IF_NEAR_DEATH ) {
				status.setType(StatusType.EMERGENCY);
				return null;
			}
			AntiBan.idle(250);
		}
			
		// Try to click
		for (int a = 0; a <= 2 + (int)(Math.random() * 3.0D); a++) {
			
			// If the npc is attacking someone else...
			if ( !NPCUtil.canAttack(npc) ) {
				break;
			}
			
			// Player attacking us
			if ( PlayerUtil.isUnderAttack(false) && Combat.getWildernessLevel() > 0 ) {
				status.setType(StatusType.EMERGENCY);
				return null;
			}
			
			// We're in danger, stop targeting.
			if ( PlayerUtil.isInDanger() && CANCEL_IF_NEAR_DEATH ) {
				status.setType(StatusType.EMERGENCY);
				return null;
			}
			
			// Maybe we're already under attack...
			alreadyAttacking = fixAlreadyAttack(status);
			if (alreadyAttacking != null) {
				if ( sucessfullyTargeted(status, npc) ) {
					return waitForAttackResult(status, alreadyAttacking);
				} else {
					return attackNPC(status, types); // Try again.
				}
			}
			
			// Walk to enemy sometimes.
			if ( a > 0 && AntiBan.randomChance(2) ) {
				walkTo(npc);
				AntiBan.sleep(1000, 500);
			}
			
			// Try to click
			if (clickAttack(npc)) {
				if ( sucessfullyTargeted(status, npc) ) {
					return waitForAttackResult(status, npc);
				} else {
					return attackNPC(status, types); // Try again.
				}
			} else {
				// Maybe we need to rotate?
				AntiBan.rotateCameraRandom();
			}

			AntiBan.sleep(350, 200);
		}
		
		// We didn't click on anyone :(
		status.setType(StatusType.FAILED);
		return null;
	}
	
	/**
	 * Will attempt to search-for and attack specific NPCs. <br>
	 * Returns (THE NPC) when the npc is found, attacked, and killed.<br>
	 * Returns (NULL) if any of the above did not complete.
	 * @param npc
	 * @return
	 */
	public static RSNPC attackNPC(NPCNames... types) {
		return attackNPC(new AIOStatus(), types);
	}
	
	/**
	 * Returns if the npc was successfully targeted.
	 * @param attacking
	 * @return
	 */
	private static boolean sucessfullyTargeted(AIOStatus status, RSNPC attacking) {
		AntiBan.sleep(700, 250);
		
		double dist = attacking.getPosition().distanceToDouble(Player.getRSPlayer());
		if ( Player.isMoving() && dist > 2 && Game.getRunEnergy() > 70 )
			PlayerUtil.setRun(true);
		
		AntiBan.sleep(700, 250);
		while(Player.isMoving())
			General.sleep(500);
		AntiBan.sleep(500, 250);
		
		boolean areWeInteracting = Player.getRSPlayer().getInteractingCharacter() != null && Player.getRSPlayer().getInteractingCharacter().equals(attacking);
		boolean areTheyInteracting = attacking.getInteractingCharacter() != null && attacking.getInteractingCharacter().equals(Player.getRSPlayer());
		boolean inCombat = PlayerUtil.isUnderAttack(true);
		
		// Player attacking us
		if ( PlayerUtil.isUnderAttack(false) && Combat.getWildernessLevel() > 0 ) {
			status.setType(StatusType.EMERGENCY);
			return false;
		}
		
		// Ded
		if (PlayerUtil.isDead() ) {
			status.setType(StatusType.DEATH);
			return false;
		}
		
		// We're in danger, stop targeting.
		if ( PlayerUtil.isInDanger() && CANCEL_IF_NEAR_DEATH )
			return false;
		
		if ( !areWeInteracting || !areTheyInteracting || !inCombat ) {
			return false;
		}
		
		return true;
	}

	/**
	 * Attempts to wait until we've finished attacking and killing an RSNPC.<br>
	 * If successfully, it will return exactly when the loot drops.
	 * @param attacking
	 * @return
	 */
	private static RSNPC waitForAttackResult(AIOStatus status, RSNPC attacking) {
		long timeStart = System.currentTimeMillis();
		
		while(true) {
			status.setStatus("Fighting npc");
			
			// Ded
			if (PlayerUtil.isDead() ) {
				status.setType(StatusType.DEATH);
				return null;
			}
			
			// Player attacking us
			if ( PlayerUtil.isUnderAttack(false) && Combat.getWildernessLevel() > 0 ) {
				status.setType(StatusType.EMERGENCY);
				return null;
			}
			
			// Player CAN attack us
			if ( Combat.getWildernessLevel() > 0 ) {
				RSPlayer[] players = Players.getAll();
				for (int i = 0; i < players.length; i++) {
					RSPlayer player = players[i];

					// Ignore ourself.
					if (!player.equals(Player.getRSPlayer())) {
						
						// If they can attack us that's not good!
						if (PlayerUtil.canPlayerAttackUsWilderness(player)) {

							// If they're skulled, ALERT us!
							if (player.getSkullIcon() != -1) {
								status.setType(StatusType.EMERGENCY);
								return null;
							}
						}
					}
				}
			}
			
			// Do some idling
			PlayerUtil.setIgnoreAttacking(true, false);
			AntiBan.afk(AntiBan.generateAFKTime(1000.0F));
			AntiBan.idle(AntiBan.generateResponseTime(MAX_AFK_TIME * 0.1f));
			AntiBan.afk(AntiBan.generateAFKTime(MAX_AFK_TIME));
			
			// Heal!
			if (doHealth(status)) {
				int afkTime = (int)(500.0D + Math.random() * 2000.0D);
				General.sleep(afkTime);
				attacking.hover();

				AntiBan.sleep(1200, 500);

				// Reclick
				int clicks = (int)(1.0D + Math.pow(Math.random(), 18.0D) * 4.0D);
				if (attacking != null) {
					for (int i = 0; i < clicks; i++) {
						clickAttack(attacking);
						AntiBan.sleep(150,70);
					}
				}
			}
			
			// We're in danger, stop targeting.
			if ( PlayerUtil.isInDanger() && AIOAttack.CANCEL_IF_NEAR_DEATH ) {
				status.setType(StatusType.EMERGENCY);
				return null;
			}
			
			// Interaction variables.
			boolean att = Combat.isUnderAttack();
			boolean inter = Player.getRSPlayer().getInteractingIndex() != -1;
			boolean idling = System.currentTimeMillis() - timeStart > 5000L;
			boolean stand = Animations.isA(Player.getAnimation(), Animations.NONE);
			
			// If we're not underattack and we're not interacting, and we're idling, and we're standing, we've failed this target.
			if (!att && !inter && idling && stand) {

				// We were afk, come back and return the npc if it died.
				if ( attacking.getHealthPercent() == 0 ) {
					doHealth(status);
					
					status.setStatus("Killed npc");
					NPCUtil.waitForDeath(attacking);
					status.setType(StatusType.SUCCESS);
					return attacking;
				}
				
				// If we're near it, click it
				double dist = attacking.getPosition().distanceToDouble(Player.getPosition());
				if ( dist < 2 ) {
					if ( clickAttack(attacking) ) {
						continue;
					}
				}
				
				// We failed...
				status.setType(StatusType.FAILED);
				status.setStatus("Failed");
				return null;
			}
			
			// It's still alive.
			if ( attacking.getHealthPercent() > 0 )
				continue;
			
			// It's dead, wait for it to die.
			status.setStatus("Killed npc");
			NPCUtil.waitForDeath(attacking);
			status.setType(StatusType.SUCCESS);
			return attacking;
		}
	}

	/**
	 * Attempts to click the attack option on an npc.
	 * @param toAttack
	 * @return
	 */
	private static boolean clickAttack(RSNPC toAttack) {
		return AccurateMouse.click(toAttack, new String[] { "Attack" });
	}
	
	/**
	 * Attempts to walk to the desired npc. It will stop when it's reachable via direct walking.
	 * @param npc
	 */
	private static void walkTo(RSNPC npc) {
		if ( npc == null )
			return;
		
		DPathNavigator nav = new DPathNavigator();
		nav.setStoppingConditionCheckDelay(100L);
		nav.setStoppingCondition(new Condition() {

			@Override
			public boolean active() {
				if ( PlayerUtil.isInDanger() )
					return true;
				return PathFinding.canReach(npc.getPosition(), false);
			}
		});

		nav.traverse(npc.getPosition());
		AccurateMouse.clickMinimap(npc.getPosition());
	}
	
	/**
	 * Attempts to attacking an NPC that is already attacking us.
	 * @return
	 */
	private static RSNPC fixAlreadyAttack(AIOStatus status) {
		RSNPC[] attackingNPC = PlayerUtil.getAttackingNPCS();
		
		if (attackingNPC.length > 0) {
			RSNPC npc = attackingNPC[0];
			
			// This NPC is too far away for us to care about.
			if ( npc.getPosition().distanceTo(Player.getPosition())>2 )
				return null;
			
			// Walk to it
			if ( !PathFinding.canReach(npc, false) )
				walkTo(npc);

			// Try to click/moveto target
			int fails = 0;
			while (fails <= 3 && !clickAttack(npc)) {
				General.sleep(50, 120);
				AntiBan.rotateCameraRandom();
				AntiBan.sleep(1000, 500);
				fails++;
			}
			status.setStatus("Already being attacked...");
			return npc;
		}
		return null;
	}
	
	/**
	 * Perform healing actions.
	 * @param status
	 * @return
	 */
	private static boolean doHealth(AIOStatus status) {
		if (PlayerUtil.needsToEat()) {

			if ((!PlayerUtil.hasFood()) && (AIOAttack.CANCEL_IF_NEAR_DEATH)) {
				return false;
			}

			General.sleep(500, 1500);


			boolean eat = false;
			while ((PlayerUtil.needsToEat()) && (PlayerUtil.hasFood())) {
				status.setStatus("Eating food");
				if (PlayerUtil.eatFood()) {
					eat = true;
					General.sleep(500, 900);
				}
			}

			if (eat) {
				return true;
			}
		}

		// Randomly eat
		int minHealth = (int)(Skills.getActualLevel(Skills.SKILLS.HITPOINTS) * 0.7D);
		int currentHealth = Skills.getCurrentLevel(Skills.SKILLS.HITPOINTS);
		if ((PlayerUtil.hasFood()) && (currentHealth <= minHealth) && (AntiBan.randomChance(60))) {
			status.setStatus("Eating food");
			PlayerUtil.eatFood();
			return true;
		}
		return false;
	}
}
