package scripts.util;

import org.tribot.api2007.Equipment;

import scripts.util.misc.ItemWrapper;
import scripts.util.names.ItemIds;

public class EquipmentUtil {
	/**
	 * Returns if the specified item is equipped.
	 * @param staff
	 * @return
	 */
	public boolean isEquipped(ItemWrapper item) {
		return isEquipped(item.getItem());
	}

	/**
	 * Returns if the specified item is equipped.
	 * @param staff
	 * @return
	 */
	public boolean isEquipped(ItemIds item) {
		return Equipment.isEquipped(item.getIds());
	}
}
