package scripts.util.player;

import org.tribot.api2007.Skills;

public class CombatStats {
	private TrackXP attack = new TrackXP(Skills.SKILLS.ATTACK);
	private TrackXP strength = new TrackXP(Skills.SKILLS.STRENGTH);
	private TrackXP defense = new TrackXP(Skills.SKILLS.DEFENCE);
	private TrackXP hitpoints = new TrackXP(Skills.SKILLS.HITPOINTS);
	private int monstersSlain;

	public void update() {
		this.attack.update();
		this.strength.update();
		this.defense.update();
		this.hitpoints.update();
	}

	public void killMonster() {
		this.monstersSlain += 1;
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
