package scripts.util.names.type;

import org.tribot.api2007.Skills.SKILLS;

public enum ToolClass {
	PICKAXE(SKILLS.MINING),
	SWORD(SKILLS.ATTACK),
	AXE(SKILLS.WOODCUTTING),
	BOW(SKILLS.RANGED),
	SHIELD(SKILLS.DEFENCE);
	
	private SKILLS primarySkill;
	
	private ToolClass(SKILLS primarySkill) {
		this.primarySkill = primarySkill;
	}
	
	public SKILLS getPrimarySkill() {
		return this.primarySkill;
	}
}