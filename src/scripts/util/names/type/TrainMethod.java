package scripts.util.names.type;

import scripts.util.misc.AntiBan;

public enum TrainMethod {
	ATTACK_XP,
	STRENGTH_XP,
	DEFENSE_XP;

	public static TrainMethod random() {
		TrainMethod[] values = values();
		return values[AntiBan.random(values.length)];
	}
}
