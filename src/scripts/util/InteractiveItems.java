package scripts.util;

public enum InteractiveItems {
	BONE(526);

	private int id;
	private InteractiveItems(int id) {
		this.id = id;
	}

	/**
	 * Returns the list of Runescape ids associated with this Interactive Object type
	 * @return
	 */
	public int getId() {
		return id;
	}
}
