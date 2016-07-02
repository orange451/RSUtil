package scripts.util;

public enum Animations {
	MINING(625),
	NONE(-1);

	private int id;
	private Animations(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static boolean isA(int animation, Animations anim) {
		return anim.getId() == animation;
	}
}
