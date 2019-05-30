package scripts.util.names;

public enum Animations {
	MINING(new int[] { 629, 628 }), 
	THIEVING(new int[] { 832 }), 
	NONE(new int[] { -1 });

	private int[] ids;

	private Animations(int... id) { this.ids = id; }

	public int[] getIds() {
		return this.ids;
	}

	public static boolean isA(int animation, Animations anim) {
		for (int i = 0; i < anim.ids.length; i++) {
			if (anim.ids[i] == animation)
				return true;
		}
		return false;
	}
}
