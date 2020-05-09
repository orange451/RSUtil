package scripts.util.names;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;

import com.allatori.annotations.DoNotRename;

import scripts.dax_api.api_lib.DaxWalker;
import scripts.dax_api.api_lib.WebWalkerServerApi;
import scripts.dax_api.api_lib.models.PlayerDetails;
import scripts.dax_api.api_lib.models.Point3D;
import scripts.util.PlayerUtil;
import scripts.util.names.type.WorldType;

@DoNotRename
public enum Banks {
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
	public static List<Banks> getBanks() {
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

	/**
	 * Returns the nearest bank from the players current position.
	 * @return
	 */
	public static Banks getNearestBank() {
		// Sort banks based on distance
		List<Banks> tempBank = getBanks();
		Collections.sort(tempBank, (Banks arg0, Banks arg1) -> {
			int d1 = arg0.getLocation().getCenter().distanceTo(Player.getPosition());
			int d2 = arg1.getLocation().getCenter().distanceTo(Player.getPosition());
			
			return d1-d2;
		});
		
		// Get distance of first 4
		Banks ret = null;
		int dist = Integer.MAX_VALUE;
		for (int i = 0; i < Math.min(4, tempBank.size()); i++) {
			Banks bank = tempBank.get(i);

			int tries = 0;
			
			// Get distance to bank
			int pathDist = -1;
			while(tries < 6) {
				// Force initialize dax
				DaxWalker.getGlobalWalkingCondition();
				
				RSTile center = bank.getLocation().getRandomizedPosition();
				ArrayList<RSTile> tiles = WebWalkerServerApi.getInstance().getPath(Point3D.fromPositionable(Player.getPosition()), Point3D.fromPositionable(center), PlayerDetails.generate()).toRSTilePath();
				
				int tempDist = tiles.size();
				if ( tempDist > 0 ) {
					pathDist = tempDist;
					break;
				}
				
				tries++;
			}
			
			// Fallback if no valid bank tile was found
			if ( pathDist == -1 )
				pathDist = bank.getLocation().getCenter().distanceTo(Player.getPosition());
			
			// Check if it's close
			if (pathDist < dist) {
				dist = pathDist;
				ret = bank;
			}
		}
		General.println("Closest Bank: " + ret.toString());

		return ret;
	}
}
