package scripts.util.names;

import java.util.ArrayList;
import java.util.Random;

import com.allatori.annotations.DoNotRename;

import scripts.util.misc.NameFormatter;

@DoNotRename
public class NameGenerator {
	private static ArrayList<String> names;
	private static Random random;
	
	/**
	 * Returns a really shitty random name. It's really yikes. Max 12 characters.
	 * @return
	 */
	public static String generateName() {
		if ( names == null )
			generateNames();
		
		String str = "";
		
		while(str.length() < 10) {
			String nextName = names.get((int)(random.nextDouble()*names.size()));
			if ( nextName.length() + str.length() <= 12 ) {
				str += nextName;
			} else {
				if ( (int)(random.nextDouble() * 2) == 1 )
					str += (int)(random.nextDouble()*10);
			}
			
			if ( (int)(random.nextDouble() * 3) == 1 )
				str += (int)(random.nextDouble()*1000);
		}
		
		return str.substring(0, Math.min(str.length(), 12));
	}

	private static void generateNames() {
		random = new Random();
		
		// Add the names to the array
		names = new ArrayList<String>();
		names.addAll(generateStrings(ItemNamesData.values()));
		names.addAll(generateStrings(Locations.values()));
		names.addAll(generateStrings(NPCNames.values()));
		names.addAll(generateStrings(ObjectNames.values()));
	}

	private static ArrayList<String> generateStrings(Object[] values) {
		ArrayList<String> ret = new ArrayList<String>();
		for (int i = 0; i < values.length; i++) {
			String t = values[i].toString();
			if ( t.startsWith("_") )
				continue;
			
			// Format it
			String formatted = NameFormatter.get(t);
			
			// Remove some stuff
			String[] temp = formatted.split(" ");
			for (int j = 0; j < temp.length; j++) {
				String word = temp[j];
				if ( word.length() < 3 )
					continue;
				if ( word.equalsIgnoreCase("the") )
					continue;
				
				// Add it
				ret.add(word);
			}
		}
		
		return ret;
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 25; i++) {
			System.out.println(generateName());
		}
	}
}
