package scripts.util.names;

import java.util.ArrayList;

import org.tribot.api.General;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;

import com.allatori.annotations.DoNotRename;

import scripts.dax_api.api_lib.WebWalkerServerApi;
import scripts.dax_api.api_lib.models.PlayerDetails;
import scripts.dax_api.api_lib.models.Point3D;

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
	EDGEVILLE(Locations.EDGEVILLE_BANK);

	private Locations location;

	private Banks(Locations location) {
		this.location = location;
	}

	/**
	 * Returns the Locations object for this bank.
	 * @return
	 */
	public Locations getLocation() {
		return this.location;
	}

	/**
	 * Returns the nearest bank from the players current position.
	 * @return
	 */
	public static Banks getNearestBank() {
		Banks[] banks = values();
		Banks ret = null;
		int dist = 999999;
		for (int i = 0; i < banks.length; i++) {
			Banks bank = banks[i];
			RSTile center = bank.getLocation().getCenter();

			// Get distance to bank
			ArrayList<RSTile> tiles = WebWalkerServerApi.getInstance().getPath(Point3D.fromPositionable(Player.getPosition()), Point3D.fromPositionable(center), PlayerDetails.generate()).toRSTilePath();
			int pathDist = tiles.size();
			if ( pathDist == 0 )
				pathDist = center.distanceTo(Player.getPosition());
			
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
