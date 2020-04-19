package scripts.util.names.type;

import scripts.util.misc.ItemWrapper;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;

public enum ArmorType implements ItemWrapper {
	BRONZE_PLATEBODY(EquipmentMaterial.BRONZE, ArmorClass.PLATEBODY, ItemNames.BRONZE_PLATEBODY),
	BRONZE_CHAINBODY(EquipmentMaterial.BRONZE, ArmorClass.PLATEBODY, ItemNames.BRONZE_CHAINBODY),
	IRON_PLATEBODY(EquipmentMaterial.IRON, ArmorClass.PLATEBODY, ItemNames.IRON_PLATEBODY),
	IRON_CHAINBODY(EquipmentMaterial.IRON, ArmorClass.PLATEBODY, ItemNames.IRON_CHAINBODY),
	STEEL_PLATEBODY(EquipmentMaterial.STEEL, ArmorClass.PLATEBODY, ItemNames.STEEL_PLATEBODY),
	STEEL_CHAINBODY(EquipmentMaterial.STEEL, ArmorClass.PLATEBODY, ItemNames.STEEL_CHAINBODY),
	MITHRIL_PLATEBODY(EquipmentMaterial.MITHRIL, ArmorClass.PLATEBODY, ItemNames.MITHRIL_PLATEBODY),
	MITHRIL_CHAINBODY(EquipmentMaterial.MITHRIL, ArmorClass.PLATEBODY, ItemNames.MITHRIL_CHAINBODY),
	ADAMANT_PLATEBODY(EquipmentMaterial.ADAMANT, ArmorClass.PLATEBODY, ItemNames.ADAMANT_PLATEBODY),
	ADAMANT_CHAINBODY(EquipmentMaterial.ADAMANT, ArmorClass.PLATEBODY, ItemNames.ADAMANT_CHAINBODY),
	RUNE_PLATEBODY(EquipmentMaterial.RUNE, ArmorClass.PLATEBODY, ItemNames.RUNE_PLATEBODY),
	RUNE_CHAINBODY(EquipmentMaterial.RUNE, ArmorClass.PLATEBODY, ItemNames.RUNE_CHAINBODY);
	
	private ItemIds item;
	private EquipmentMaterial material;
	private ArmorClass type;
	
	private ArmorType(EquipmentMaterial material, ArmorClass type, ItemIds item) {
		this.material = material;
		this.type = type;
		this.item = item;
	}
	
	public ArmorClass getType() {
		return this.type;
	}
	
	public EquipmentMaterial getMaterial() {
		return this.material;
	}
	
	public ItemIds getItem() {
		return this.item;
	}
}