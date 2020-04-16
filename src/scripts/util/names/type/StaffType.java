package scripts.util.names.type;

import scripts.util.misc.ItemWrapper;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;

public enum StaffType implements ItemWrapper {
	STAFF_OF_AIR(ItemNames.STAFF_OF_AIR, RuneType.AIR),
	AIR_BATTLESTAFF(ItemNames.AIR_BATTLESTAFF, RuneType.AIR),
	MYSTIC_AIR_STAFF(ItemNames.MYSTIC_AIR_STAFF, RuneType.AIR),
	STAFF_OF_WATER(ItemNames.STAFF_OF_WATER, RuneType.WATER),
	WATER_BATTLESTAFF(ItemNames.WATER_BATTLESTAFF, RuneType.WATER),
	MYSTIC_WATER_STAFF(ItemNames.MYSTIC_WATER_STAFF, RuneType.WATER),
	STAFF_OF_EARTH(ItemNames.STAFF_OF_EARTH, RuneType.EARTH),
	EARTH_BATTLESTAFF(ItemNames.EARTH_BATTLESTAFF, RuneType.EARTH),
	MYSTIC_EARTH_STAFF(ItemNames.MYSTIC_EARTH_STAFF, RuneType.EARTH),
	STAFF_OF_FIRE(ItemNames.STAFF_OF_FIRE, RuneType.FIRE),
	FIRE_BATTLESTAFF(ItemNames.FIRE_BATTLESTAFF, RuneType.FIRE),
	MYSTIC_FIRE_STAFF(ItemNames.MYSTIC_FIRE_STAFF, RuneType.FIRE),
	LAVA_BATTLESTAFF(ItemNames.LAVA_BATTLESTAFF, RuneType.FIRE, RuneType.EARTH),
	MYSTIC_LAVA_STAFF(ItemNames.MYSTIC_LAVA_STAFF, RuneType.FIRE, RuneType.EARTH),
	MUD_BATTLESTAFF(ItemNames.MUD_BATTLESTAFF, RuneType.WATER, RuneType.EARTH),
	MYSTIC_MUD_STAFF(ItemNames.MYSTIC_MUD_STAFF, RuneType.WATER, RuneType.EARTH),
	STEAM_BATTLESTAFF(ItemNames.STEAM_BATTLESTAFF, RuneType.WATER, RuneType.FIRE),
	MYSTIC_STEAM_STAFF(ItemNames.MYSTIC_STEAM_STAFF, RuneType.WATER, RuneType.FIRE),
	SMOKE_BATTLESTAFF(ItemNames.SMOKE_BATTLESTAFF, RuneType.FIRE, RuneType.AIR),
	MYSTIC_SMOKE_STAFF(ItemNames.MYSTIC_SMOKE_STAFF, RuneType.FIRE, RuneType.AIR);
	
	private ItemIds item;
	private RuneType[] runes;
	
	StaffType(ItemIds item, RuneType... requiredRunes) {
		this.item = item;
		this.runes = requiredRunes;
	}
	
	public ItemIds getItem() {
		return this.item;
	}
	
	public RuneType[] getRequiredRunes() {
		return this.runes;
	}
}

