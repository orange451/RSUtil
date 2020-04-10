package scripts.util.aio;

import scripts.util.names.ItemNames;
import scripts.util.names.ItemNamesData;

public class AIOEquipment {
	public static boolean equipArmor(ArmorType armor) {
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
		
		return true;
	}
	
	public static enum ArmorType {
		BRONZE(1, ItemNames.BRONZE_PLATELEGS, ItemNames.BRONZE_PLATEBODY),
		IRON(2, ItemNames.IRON_PLATELEGS, ItemNames.IRON_PLATEBODY),
		STEEL(3, ItemNames.STEEL_PLATELEGS, ItemNames.STEEL_PLATEBODY),
		MITHRIL(4, ItemNames.MITHRIL_PLATELEGS, ItemNames.MITHRIL_PLATEBODY),
		ADAMANT(5, ItemNames.ADAMANT_PLATELEGS, ItemNames.ADAMANT_PLATEBODY),
		RUNE(6, ItemNames.RUNE_PLATELEGS, ItemNames.RUNE_PLATEBODY);
		
		private ItemNamesData[] items;
		private int quality;
		
		private ArmorType(int quality, ItemNamesData... items) {
			this.items = items;
		}
		
		public int getQuality() {
			return this.quality;
		}
		
		public ItemNamesData[] getItems() {
			return this.items;
		}
	}
}
