package scripts.util.names.type;

import org.tribot.api2007.Skills.SKILLS;

public enum ToolClass implements EquipmentClass {
	PICKAXE(SKILLS.MINING, 1),
	SWORD(SKILLS.ATTACK, 0),
	AXE(SKILLS.WOODCUTTING, 1),
	BOW(SKILLS.RANGED, 0),
	SHIELD(SKILLS.DEFENCE, 0);
	
	private SKILLS primarySkill;
	private int skillOffset;
	
	private ToolClass(SKILLS primarySkill, int skillOffset) {
		this.primarySkill = primarySkill;
		this.skillOffset = skillOffset;
	}
	
	public SKILLS getPrimarySkill() {
		return this.primarySkill;
	}

	public int getSkillOffset() {
		return this.skillOffset;
	}
}