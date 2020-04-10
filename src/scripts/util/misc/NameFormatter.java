package scripts.util.misc;

public class NameFormatter {
	/**
	 * Returns a properly formatted string. <br>
	 * Example: IRON_HELMET -> Iron Helmet
	 * @param input
	 * @return
	 */
	public static String get(String input) {
		String[] arr = input.toLowerCase().replace("_", " ").split(" ");
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < arr.length; i++) {
			sb.append(Character.toUpperCase(arr[i].charAt(0))).append(arr[i].substring(1)).append(" ");
		}
		return sb.toString().trim();
	}
}
