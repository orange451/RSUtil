package scripts.util.names.type;

import scripts.util.misc.ItemWrapper;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;

public enum ToolType implements ItemWrapper {
	BRONZE_SWORD(EquipmentMaterial.BRONZE, ToolClass.SWORD, ItemNames.BRONZE_SWORD),
	IRON_SWORD(EquipmentMaterial.IRON, ToolClass.SWORD, ItemNames.IRON_SWORD),
	STEEL_SWORD(EquipmentMaterial.STEEL, ToolClass.SWORD, ItemNames.STEEL_SWORD),
	MITHRIL_SWORD(EquipmentMaterial.MITHRIL, ToolClass.SWORD, ItemNames.MITHRIL_SWORD),
	ADAMANT_SWORD(EquipmentMaterial.ADAMANT, ToolClass.SWORD, ItemNames.ADAMANT_SWORD),
	RUNE_SWORD(EquipmentMaterial.RUNE, ToolClass.SWORD, ItemNames.RUNE_SWORD),

	BRONZE_SCIMITAR(EquipmentMaterial.BRONZE, ToolClass.SWORD, ItemNames.BRONZE_SCIMITAR),
	IRON_SCIMITAR(EquipmentMaterial.IRON, ToolClass.SWORD, ItemNames.IRON_SCIMITAR),
	STEEL_SCIMITAR(EquipmentMaterial.STEEL, ToolClass.SWORD, ItemNames.STEEL_SCIMITAR),
	MITHRIL_SCIMITAR(EquipmentMaterial.MITHRIL, ToolClass.SWORD, ItemNames.MITHRIL_SCIMITAR),
	ADAMANT_SCIMITAR(EquipmentMaterial.ADAMANT, ToolClass.SWORD, ItemNames.ADAMANT_SCIMITAR),
	RUNE_SCIMITAR(EquipmentMaterial.RUNE, ToolClass.SWORD, ItemNames.RUNE_SCIMITAR),
	
	BRONZE_LONGSWORD(EquipmentMaterial.BRONZE, ToolClass.SWORD, ItemNames.BRONZE_LONGSWORD),
	IRON_LONGSWORD(EquipmentMaterial.IRON, ToolClass.SWORD, ItemNames.IRON_LONGSWORD),
	STEEL_LONGSWORD(EquipmentMaterial.STEEL, ToolClass.SWORD, ItemNames.STEEL_LONGSWORD),
	MITHRIL_LONGSWORD(EquipmentMaterial.MITHRIL, ToolClass.SWORD, ItemNames.MITHRIL_LONGSWORD),
	ADAMANT_LONGSWORD(EquipmentMaterial.ADAMANT, ToolClass.SWORD, ItemNames.ADAMANT_LONGSWORD),
	RUNE_LONGSWORD(EquipmentMaterial.RUNE, ToolClass.SWORD, ItemNames.RUNE_LONGSWORD),

	BRONZE_PICKAXE(EquipmentMaterial.BRONZE, ToolClass.PICKAXE, ItemNames.BRONZE_PICKAXE),
	IRON_PICKAXE(EquipmentMaterial.IRON, ToolClass.PICKAXE, ItemNames.IRON_PICKAXE),
	STEEL_PICKAXE(EquipmentMaterial.STEEL, ToolClass.PICKAXE, ItemNames.STEEL_PICKAXE),
	MITHRIL_PICKAXE(EquipmentMaterial.MITHRIL, ToolClass.PICKAXE, ItemNames.MITHRIL_PICKAXE),
	ADAMANT_PICKAXE(EquipmentMaterial.ADAMANT, ToolClass.PICKAXE, ItemNames.ADAMANT_PICKAXE),
	RUNE_PICKAXE(EquipmentMaterial.RUNE, ToolClass.PICKAXE, ItemNames.RUNE_PICKAXE),

	BRONZE_AXE(EquipmentMaterial.BRONZE, ToolClass.AXE, ItemNames.BRONZE_AXE),
	IRON_AXE(EquipmentMaterial.IRON, ToolClass.AXE, ItemNames.IRON_AXE),
	STEEL_AXE(EquipmentMaterial.STEEL, ToolClass.AXE, ItemNames.STEEL_AXE),
	MITHRIL_AXE(EquipmentMaterial.MITHRIL, ToolClass.AXE, ItemNames.MITHRIL_AXE),
	ADAMANT_AXE(EquipmentMaterial.ADAMANT, ToolClass.AXE, ItemNames.ADAMANT_AXE),
	RUNE_AXE(EquipmentMaterial.RUNE, ToolClass.AXE, ItemNames.RUNE_AXE),

	WOODEN_SHIELD(EquipmentMaterial.WOODEN, ToolClass.SHIELD, ItemNames.WOODEN_SHIELD),
	
	BRONZE_SQ_SHIELD(EquipmentMaterial.BRONZE, ToolClass.SHIELD, ItemNames.BRONZE_SQ_SHIELD),
	IRON_SQ_SHIELD(EquipmentMaterial.IRON, ToolClass.SHIELD, ItemNames.IRON_SQ_SHIELD),
	STEEL_SQ_SHIELD(EquipmentMaterial.STEEL, ToolClass.SHIELD, ItemNames.STEEL_SQ_SHIELD),
	MITHRIL_SQ_SHIELD(EquipmentMaterial.MITHRIL, ToolClass.SHIELD, ItemNames.MITHRIL_SQ_SHIELD),
	ADAMANT_SQ_SHIELD(EquipmentMaterial.ADAMANT, ToolClass.SHIELD, ItemNames.ADAMANT_SQ_SHIELD),
	RUNE_SQ_SHIELD(EquipmentMaterial.RUNE, ToolClass.SHIELD, ItemNames.RUNE_SQ_SHIELD),

	BRONZE_KITESHIELD(EquipmentMaterial.BRONZE, ToolClass.SHIELD, ItemNames.BRONZE_KITESHIELD),
	IRON_KITESHIELD(EquipmentMaterial.IRON, ToolClass.SHIELD, ItemNames.IRON_KITESHIELD),
	STEEL_KITESHIELD(EquipmentMaterial.STEEL, ToolClass.SHIELD, ItemNames.STEEL_KITESHIELD),
	MITHRIL_KITESHIELD(EquipmentMaterial.MITHRIL, ToolClass.SHIELD, ItemNames.MITHRIL_KITESHIELD),
	ADAMANT_KITESHIELD(EquipmentMaterial.ADAMANT, ToolClass.SHIELD, ItemNames.ADAMANT_KITESHIELD),
	RUNE_KITESHIELD(EquipmentMaterial.RUNE, ToolClass.SHIELD, ItemNames.RUNE_KITESHIELD);
	
	private ItemIds item;
	private EquipmentMaterial material;
	private ToolClass type;
	
	private ToolType(EquipmentMaterial material, ToolClass type, ItemIds item) {
		this.material = material;
		this.type = type;
		this.item = item;
	}
	
	public ToolClass getType() {
		return this.type;
	}
	
	public EquipmentMaterial getMaterial() {
		return this.material;
	}
	
	public ItemIds getItem() {
		return this.item;
	}
}