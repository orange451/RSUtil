package scripts.util.names.type;

import org.tribot.api2007.Skills.SKILLS;

public enum ArmorClass implements EquipmentClass {
	PLATEBODY,
	LEGGINGS,
	HELMET,
	BOOTS;

	@Override
	public SKILLS getPrimarySkill() {
		return SKILLS.DEFENCE;
	}

	@Override
	public int getSkillOffset() {
		return 0;
	}
}