package scripts.util.misc;

public class NameFormatter {
	/**
	 * Returns a properly formatted string. <br>
	 * Example: IRON_HELMET -> Iron Helmet
	 * @param input
	 * @return
	 */
	public static String get(Object input) {
		if ( input == null )
			return "";
		String[] arr = input.toString().toLowerCase().replace("_", " ").split(" ");
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < arr.length; i++) {
			sb.append(Character.toUpperCase(arr[i].charAt(0))).append(arr[i].substring(1)).append(" ");
		}
		return sb.toString().trim();
	}
	
	/**
	 * Unformats a previously formatted string. Puts it into Java Enum format.
	 * @param input
	 * @return
	 */
	public static String unformat(Object input) {
		if ( input == null )
			return "";
		
		return input.toString().toUpperCase().replace(" ", "_");
	}
}
