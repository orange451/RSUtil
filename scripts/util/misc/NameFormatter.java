package scripts.util.misc;

public class NameFormatter {
	/**
	 * Returns a properly formatted string. <br>
	 * Example: IRON_HELMET -> Iron helmet
	 * @param input
	 * @return
	 */
	public static String get(Object input) {
		if ( input == null )
			return "";
		String[] arr = input.toString().toLowerCase().replace("_", " ").split(" ");
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < arr.length; i++) {
			if ( i == 0 )
				sb.append(Character.toUpperCase(arr[i].charAt(0))).append(arr[i].substring(1));
			else
				sb.append(arr[i]);
			
			if ( i < arr.length - 1 )
				sb.append(" ");
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
