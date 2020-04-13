package scripts.util.aio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.tribot.api2007.types.RSItem;

import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;

public class AIOEquipment {
	
	public static RSItem equipArmor(ArmorType armor) {
		//Equipment.
		// Check current wearing armor
		// 		CAN RETURN TRUE IF SUCCESS
		
		// Check if inventory already has armor (or close enough) and equip
		// 		CAN RETURN TRUE IF SUCCESS
		
		// Walk to bank to check for armor
		
		// Check if inventory already has armor (or close enough) and equip
		// 		CAN RETURN TRUE IF SUCCESS
		
		// If we still don't have armor go to GE to buy armor...
		
		// Collect GE items
		
		// Check if inventory already has armor (or close enough) and equip
		// 		CAN RETURN TRUE IF SUCCESS
		
		return null;
	}
	
	private static ItemIds[] getItemsForArmorType(ArmorType type) {
		Armor[] armorArray = Armor.values();
		
		// Get armors that match this type
		List<Armor> temp = new ArrayList<>();
		for (Armor armor : armorArray) {
			if ( armor.getType().equals(type) )
				temp.add(armor);
		}
		
		// Sort on quality
		Collections.sort(temp, new Comparator<Armor>() {
			@Override
			public int compare(Armor arg0, Armor arg1) {
				return arg0.getMaterial().getQuality()-arg1.getMaterial().getQuality();
			}
		});
		
		// Turn into list of ItemIds
		ItemIds[] ret = new ItemIds[temp.size()];
		for (int i = 0; i < temp.size(); i++) {
			ret[i] = temp.get(i).getItem();
		}
		
		return ret;
	}
	
	static enum ArmorType {
		PLATEBODY,
		LEGGINGS,
		HELMET,
		BOOTS
	}
	
	static enum ArmorMaterial {
		BRONZE(1),
		IRON(2),
		STEEL(3),
		MITHRIL(4),
		ADAMANT(5),
		RUNE(6),
		DRAGON(7);
		
		private int quality;
		
		public int getQuality() {
			return this.quality;
		}
		
		ArmorMaterial(int quality) {
			this.quality = quality;
		}
	}
	
	public static enum Armor {
		BRONZE_PLATEBODY(ArmorMaterial.BRONZE, ArmorType.PLATEBODY, ItemNames.BRONZE_PLATEBODY),
		IRON_PLATEBODY(ArmorMaterial.IRON, ArmorType.PLATEBODY, ItemNames.IRON_PLATEBODY),
		STEEL_PLATEBODY(ArmorMaterial.STEEL, ArmorType.PLATEBODY, ItemNames.STEEL_PLATEBODY),
		MITHRIL_PLATEBODY(ArmorMaterial.MITHRIL, ArmorType.PLATEBODY, ItemNames.MITHRIL_PLATEBODY),
		ADAMANT_PLATEBODY(ArmorMaterial.ADAMANT, ArmorType.PLATEBODY, ItemNames.ADAMANT_PLATEBODY),
		RUNE_PLATEBODY(ArmorMaterial.RUNE, ArmorType.PLATEBODY, ItemNames.RUNE_PLATEBODY);
		
		private ItemIds item;
		private ArmorMaterial material;
		private ArmorType type;
		
		private Armor(ArmorMaterial material, ArmorType type, ItemIds item) {
			this.material = material;
			this.type = type;
			this.item = item;
		}
		
		public ArmorType getType() {
			return this.type;
		}
		
		public ArmorMaterial getMaterial() {
			return this.material;
		}
		
		public ItemIds getItem() {
			return this.item;
		}
	}
}
