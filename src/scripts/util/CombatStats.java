package scripts.util;

import org.tribot.api2007.Skills.SKILLS;

import scripts.util.TrackXP;

public class CombatStats {
	private TrackXP attack = new TrackXP( SKILLS.ATTACK );
	private TrackXP strength = new TrackXP( SKILLS.STRENGTH );
	private TrackXP defense = new TrackXP( SKILLS.DEFENCE );
	private TrackXP hitpoints = new TrackXP( SKILLS.HITPOINTS );

	private int monstersSlain;

	public void update() {
		attack.update();
		strength.update();
		defense.update();
		hitpoints.update();
	}

	public void killMonster() {
		this.monstersSlain++;
	}

	public int getMonstersKilled() {
		return this.monstersSlain;
	}

	public TrackXP getAttack() {
		return this.attack;
	}

	public TrackXP getStrength() {
		return this.strength;
	}

	public TrackXP getDefense() {
		return this.defense;
	}

	public TrackXP getHitpoints() {
		return this.hitpoints;
	}
}
