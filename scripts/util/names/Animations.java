package scripts.util.names;

import com.allatori.annotations.DoNotRename;

@DoNotRename
public enum Animations {
	SMITHING(899),
	MINING(new int[] { 629, 628, 6753, 6752 }), 
	THIEVING(new int[] { 832 }), 
	NONE(new int[] { -1 }),
	FISHING(new int[] { 621, 623, 622, 618, 619 }),
	CHOPPING(879), 
	PUMPING_WATER(832),
	COOKING(896),
	;

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
