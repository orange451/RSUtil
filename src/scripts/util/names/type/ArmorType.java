package scripts.util.names.type;

import scripts.util.misc.ItemWrapper;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;

public enum ArmorType implements ItemWrapper {
	BRONZE_PLATEBODY(EquipmentMaterial.BRONZE, ArmorClassification.PLATEBODY, ItemNames.BRONZE_PLATEBODY),
	BRONZE_CHAINBODY(EquipmentMaterial.BRONZE, ArmorClassification.PLATEBODY, ItemNames.BRONZE_CHAINBODY),
	IRON_PLATEBODY(EquipmentMaterial.IRON, ArmorClassification.PLATEBODY, ItemNames.IRON_PLATEBODY),
	IRON_CHAINBODY(EquipmentMaterial.IRON, ArmorClassification.PLATEBODY, ItemNames.IRON_CHAINBODY),
	STEEL_PLATEBODY(EquipmentMaterial.STEEL, ArmorClassification.PLATEBODY, ItemNames.STEEL_PLATEBODY),
	STEEL_CHAINBODY(EquipmentMaterial.STEEL, ArmorClassification.PLATEBODY, ItemNames.STEEL_CHAINBODY),
	MITHRIL_PLATEBODY(EquipmentMaterial.MITHRIL, ArmorClassification.PLATEBODY, ItemNames.MITHRIL_PLATEBODY),
	MITHRIL_CHAINBODY(EquipmentMaterial.MITHRIL, ArmorClassification.PLATEBODY, ItemNames.MITHRIL_CHAINBODY),
	ADAMANT_PLATEBODY(EquipmentMaterial.ADAMANT, ArmorClassification.PLATEBODY, ItemNames.ADAMANT_PLATEBODY),
	ADAMANT_CHAINBODY(EquipmentMaterial.ADAMANT, ArmorClassification.PLATEBODY, ItemNames.ADAMANT_CHAINBODY),
	RUNE_PLATEBODY(EquipmentMaterial.RUNE, ArmorClassification.PLATEBODY, ItemNames.RUNE_PLATEBODY),
	RUNE_CHAINBODY(EquipmentMaterial.RUNE, ArmorClassification.PLATEBODY, ItemNames.RUNE_CHAINBODY);
	
	private ItemIds item;
	private EquipmentMaterial material;
	private ArmorClassification type;
	
	private ArmorType(EquipmentMaterial material, ArmorClassification type, ItemIds item) {
		this.material = material;
		this.type = type;
		this.item = item;
	}
	
	public ArmorClassification getType() {
		return this.type;
	}
	
	public EquipmentMaterial getMaterial() {
		return this.material;
	}
	
	public ItemIds getItem() {
		return this.item;
	}
}