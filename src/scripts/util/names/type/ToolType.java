package scripts.util.names.type;

import scripts.util.misc.ItemWrapper;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;

public enum ToolType implements ItemWrapper {
	BRONZE_SWORD(EquipmentMaterial.BRONZE, ToolClassification.SWORD, ItemNames.BRONZE_SWORD),
	IRON_SWORD(EquipmentMaterial.IRON, ToolClassification.SWORD, ItemNames.IRON_SWORD),
	STEEL_SWORD(EquipmentMaterial.STEEL, ToolClassification.SWORD, ItemNames.STEEL_SWORD),
	MITHRIL_SWORD(EquipmentMaterial.MITHRIL, ToolClassification.SWORD, ItemNames.MITHRIL_SWORD),
	ADAMANT_SWORD(EquipmentMaterial.ADAMANT, ToolClassification.SWORD, ItemNames.ADAMANT_SWORD),
	RUNE_SWORD(EquipmentMaterial.RUNE, ToolClassification.SWORD, ItemNames.RUNE_SWORD),

	BRONZE_SCIMITAR(EquipmentMaterial.BRONZE, ToolClassification.SWORD, ItemNames.BRONZE_SCIMITAR),
	IRON_SCIMITAR(EquipmentMaterial.IRON, ToolClassification.SWORD, ItemNames.IRON_SCIMITAR),
	STEEL_SCIMITAR(EquipmentMaterial.STEEL, ToolClassification.SWORD, ItemNames.STEEL_SCIMITAR),
	MITHRIL_SCIMITAR(EquipmentMaterial.MITHRIL, ToolClassification.SWORD, ItemNames.MITHRIL_SCIMITAR),
	ADAMANT_SCIMITAR(EquipmentMaterial.ADAMANT, ToolClassification.SWORD, ItemNames.ADAMANT_SCIMITAR),
	RUNE_SCIMITAR(EquipmentMaterial.RUNE, ToolClassification.SWORD, ItemNames.RUNE_SCIMITAR),
	
	BRONZE_LONGSWORD(EquipmentMaterial.BRONZE, ToolClassification.SWORD, ItemNames.BRONZE_LONGSWORD),
	IRON_LONGSWORD(EquipmentMaterial.IRON, ToolClassification.SWORD, ItemNames.IRON_LONGSWORD),
	STEEL_LONGSWORD(EquipmentMaterial.STEEL, ToolClassification.SWORD, ItemNames.STEEL_LONGSWORD),
	MITHRIL_LONGSWORD(EquipmentMaterial.MITHRIL, ToolClassification.SWORD, ItemNames.MITHRIL_LONGSWORD),
	ADAMANT_LONGSWORD(EquipmentMaterial.ADAMANT, ToolClassification.SWORD, ItemNames.ADAMANT_LONGSWORD),
	RUNE_LONGSWORD(EquipmentMaterial.RUNE, ToolClassification.SWORD, ItemNames.RUNE_LONGSWORD),

	BRONZE_PICKAXE(EquipmentMaterial.BRONZE, ToolClassification.PICKAXE, ItemNames.BRONZE_PICKAXE),
	IRON_PICKAXE(EquipmentMaterial.IRON, ToolClassification.PICKAXE, ItemNames.IRON_PICKAXE),
	STEEL_PICKAXE(EquipmentMaterial.STEEL, ToolClassification.PICKAXE, ItemNames.STEEL_PICKAXE),
	MITHRIL_PICKAXE(EquipmentMaterial.MITHRIL, ToolClassification.PICKAXE, ItemNames.MITHRIL_PICKAXE),
	ADAMANT_PICKAXE(EquipmentMaterial.ADAMANT, ToolClassification.PICKAXE, ItemNames.ADAMANT_PICKAXE),
	RUNE_PICKAXE(EquipmentMaterial.RUNE, ToolClassification.PICKAXE, ItemNames.RUNE_PICKAXE),

	BRONZE_AXE(EquipmentMaterial.BRONZE, ToolClassification.AXE, ItemNames.BRONZE_AXE),
	IRON_AXE(EquipmentMaterial.IRON, ToolClassification.AXE, ItemNames.IRON_AXE),
	STEEL_AXE(EquipmentMaterial.STEEL, ToolClassification.AXE, ItemNames.STEEL_AXE),
	MITHRIL_AXE(EquipmentMaterial.MITHRIL, ToolClassification.AXE, ItemNames.MITHRIL_AXE),
	ADAMANT_AXE(EquipmentMaterial.ADAMANT, ToolClassification.AXE, ItemNames.ADAMANT_AXE),
	RUNE_AXE(EquipmentMaterial.RUNE, ToolClassification.AXE, ItemNames.RUNE_AXE),

	WOODEN_SHIELD(EquipmentMaterial.WOODEN, ToolClassification.SHIELD, ItemNames.WOODEN_SHIELD),
	
	BRONZE_SQ_SHIELD(EquipmentMaterial.BRONZE, ToolClassification.SHIELD, ItemNames.BRONZE_SQ_SHIELD),
	IRON_SQ_SHIELD(EquipmentMaterial.IRON, ToolClassification.SHIELD, ItemNames.IRON_SQ_SHIELD),
	STEEL_SQ_SHIELD(EquipmentMaterial.STEEL, ToolClassification.SHIELD, ItemNames.STEEL_SQ_SHIELD),
	MITHRIL_SQ_SHIELD(EquipmentMaterial.MITHRIL, ToolClassification.SHIELD, ItemNames.MITHRIL_SQ_SHIELD),
	ADAMANT_SQ_SHIELD(EquipmentMaterial.ADAMANT, ToolClassification.SHIELD, ItemNames.ADAMANT_SQ_SHIELD),
	RUNE_SQ_SHIELD(EquipmentMaterial.RUNE, ToolClassification.SHIELD, ItemNames.RUNE_SQ_SHIELD),

	BRONZE_KITESHIELD(EquipmentMaterial.BRONZE, ToolClassification.SHIELD, ItemNames.BRONZE_KITESHIELD),
	IRON_KITESHIELD(EquipmentMaterial.IRON, ToolClassification.SHIELD, ItemNames.IRON_KITESHIELD),
	STEEL_KITESHIELD(EquipmentMaterial.STEEL, ToolClassification.SHIELD, ItemNames.STEEL_KITESHIELD),
	MITHRIL_KITESHIELD(EquipmentMaterial.MITHRIL, ToolClassification.SHIELD, ItemNames.MITHRIL_KITESHIELD),
	ADAMANT_KITESHIELD(EquipmentMaterial.ADAMANT, ToolClassification.SHIELD, ItemNames.ADAMANT_KITESHIELD),
	RUNE_KITESHIELD(EquipmentMaterial.RUNE, ToolClassification.SHIELD, ItemNames.RUNE_KITESHIELD);
	
	private ItemIds item;
	private EquipmentMaterial material;
	private ToolClassification type;
	
	private ToolType(EquipmentMaterial material, ToolClassification type, ItemIds item) {
		this.material = material;
		this.type = type;
		this.item = item;
	}
	
	public ToolClassification getType() {
		return this.type;
	}
	
	public EquipmentMaterial getMaterial() {
		return this.material;
	}
	
	public ItemIds getItem() {
		return this.item;
	}
}