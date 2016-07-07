package scripts.util.names;

public enum ItemNames {
	FOOD_TUNA(361),
	FOOD_SALMON(329),
	FOOD_LOBSTER(379),
	FOOD_SWORDFISH(373),
	FOOD_SHARK(385),

	COINS(995),
	BONE(526);

	private int id;
	private ItemNames(int id) {
		this.id = id;
	}

	/**
	 * Returns the list of Runescape ids associated with this Interactive Object type
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the name of this enum with Title Casing.
	 * <br>
	 * Additionally the _ characters are replaced with a blank space.
	 * @return
	 */
	public String getName() {
	    String[] arr = toString().toLowerCase().replace("_", " ").split(" ");
	    StringBuffer sb = new StringBuffer();

	    for (int i = 0; i < arr.length; i++) {
	        sb.append(Character.toUpperCase(arr[i].charAt(0)))
	            .append(arr[i].substring(1)).append(" ");
	    }
	    return sb.toString().trim();
	}
}
