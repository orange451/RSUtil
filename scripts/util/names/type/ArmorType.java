package scripts.util.names.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;

public enum ArmorType implements EquippableItem {
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
	
	public static ArmorType[] getArmorTypes(ArmorClass type, EquipmentMaterial minimumMaterial) {
		ArmorType[] armorArray = ArmorType.values();
		
		// Get armors that match this type
		List<ArmorType> temp = new ArrayList<>();
		for (ArmorType armor : armorArray) {
			if ( armor.getMaterial().getQuality() < minimumMaterial.getQuality() )
				continue;
			
			if ( armor.getType().equals(type) )
				temp.add(armor);
		}
		
		// Sort on quality
		Collections.sort(temp, new Comparator<ArmorType>() {
			@Override
			public int compare(ArmorType arg0, ArmorType arg1) {
				return compareQuality(arg0.getMaterial().getQuality(), arg1.getMaterial().getQuality());
			}
		});
		
		return temp.toArray(new ArmorType[temp.size()]);
	}
	
	private static int compareQuality(int a, int b) {
		return a-b;
	}
}