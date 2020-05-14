package scripts.util.names;

import java.util.ArrayList;
import java.util.List;

import org.tribot.api2007.Game;
import com.allatori.annotations.DoNotRename;

import scripts.util.names.type.WorldType;

@DoNotRename
public enum Banks implements LocationCollection {
	VARROK_EAST(Locations.VARROK_BANK_EAST), 
	VARROK_WEST(Locations.VARROK_BANK_WEST), 
	GRAND_EXCHANGE(Locations.GRAND_EXCHANGE), 
	FALADOR_EAST(Locations.FALADOR_BANK_EAST), 
	FALADOR_WEST(Locations.FALADOR_BANK_WEST), 
	LUMBRIDGE(Locations.LUMBRIDGE_BANK), 
	ALKHARID(Locations.ALKHARID_BANK), 
	DRAYNOR(Locations.DRAYNOR_BANK), 
	EDGEVILLE(Locations.EDGEVILLE_BANK),
	CATHERBY(Locations.CATHERBY_BANK, true);

	private Locations location;
	private boolean members;

	private Banks(Locations location) {
		this(location, false);
	}

	private Banks(Locations location, boolean members) {
		this.location = location;
	}
	
	/**
	 * Returns whether this bank is for members only.
	 * @return
	 */
	public boolean isMembers() {
		return this.members;
	}

	/**
	 * Returns the Locations object for this bank.
	 * @return
	 */
	public Locations getLocation() {
		return this.location;
	}
	
	/**
	 * Returns list of banks you can bank at.
	 * @return
	 */
	public static List<Banks> getValidBanks() {
		List<Banks> ret = new ArrayList<>();
		for (Banks bank : values()) {
			boolean isInMemberWorld = WorldType.getWorldType(Game.getCurrentWorld()) == WorldType.MEMBER;
			if ( bank.isMembers() && isInMemberWorld )
				ret.add(bank);
			else if ( bank.isMembers() && !isInMemberWorld )
				continue;
			
			ret.add(bank);
		}
		
		return ret;
	}
}
