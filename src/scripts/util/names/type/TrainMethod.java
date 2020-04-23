package scripts.util.names.type;

import java.util.ArrayList;
import java.util.List;

import org.tribot.api2007.Skills.SKILLS;

import scripts.util.misc.AntiBan;

public enum TrainMethod {
	ATTACK_XP(SKILLS.ATTACK),
	STRENGTH_XP(SKILLS.STRENGTH),
	DEFENSE_XP(SKILLS.DEFENCE);
	
	private SKILLS skill;
	
	TrainMethod(SKILLS skill) {
		this.skill = skill;
	}
	
	public SKILLS getSkill() {
		return this.skill;
	}

	/**
	 * Return a random TrainMethod
	 * @return
	 */
	public static TrainMethod random() {
		TrainMethod[] values = values();
		return values[AntiBan.random(values.length)];
	}
	
	/**
	 * Return the train method that trains your lowest skill. (Picks at random if there's a tie)
	 * @return
	 */
	public static TrainMethod getLowestSkill() {
		List<TrainMethod> temp = new ArrayList<>();
		int min = values()[0].getSkill().getActualLevel();
		
		// Find lowest one
		for (TrainMethod method : values()) {
			SKILLS skill = method.getSkill();
			if ( skill.getActualLevel() < min ) {
				min = skill.getActualLevel();
			}
		}
		
		// Find duplicates
		for (TrainMethod method : values()) {
			SKILLS skill = method.getSkill();
			if ( skill.getActualLevel() == min ) {
				temp.add(method);
			}
		}
		
		return temp.get(AntiBan.random(temp.size()));
	}
}
