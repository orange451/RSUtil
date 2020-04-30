package scripts.util.aio;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.types.RSCharacter;
import org.tribot.api2007.types.RSItem;

import scripts.util.PlayerUtil;
import scripts.util.names.ItemNames;
import scripts.util.names.type.FoodType;

public class AIOPlayer {

	/**
	 * If the player is being attacked, walk to the nearest bank. If the player needs food, he will withdraw food and eat it.
	 * @param ignoreNPC
	 * @return
	 */
	public static boolean runToNearestBankIfAttacked(boolean ignoreNPC) {
		RSCharacter[] chars = ignoreNPC?PlayerUtil.getAttackingPlayers():PlayerUtil.getAttackingCharacters();
		
		if ( chars.length > 0 ) {
			AIOWalk.walkToNearestBank();
			
			if ( PlayerUtil.getHealth() < PlayerUtil.getHPToEatAt() ) {
				if ( AIOBank.walkToNearestBankAndWithdrawFirstItem(1, ItemNames.get(FoodType.values())) ) {
					General.sleep(1000,2000);
					Banking.close();
					General.sleep(1000,2000);
					RSItem food = PlayerUtil.getFirstItemInInventory(ItemNames.get(FoodType.values()));
					if ( food != null ) {
						food.click("");
						General.sleep(1000,2000);
					}
				} else {
					General.sleep(30000, 60000);
				}
			}
			return true;
		}
		
		return false;
	}

}
