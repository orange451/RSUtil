package scripts.util.aio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.tribot.api2007.Banking;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;

import scripts.util.BankingUtil;
import scripts.util.PlayerUtil;
import scripts.util.misc.AntiBan;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;
import static scripts.util.names.ItemNamesData.get;

public class AIOEquipment {
	
	public static boolean equipTool(ToolType tool) {
		return equipTool(tool, EquipmentMaterial.BRONZE);
	}
	
	public static boolean equipTool(ToolType tool, EquipmentMaterial minimumMaterial) {
		ItemIds[] desiredItems = convertToItems(getToolTypes(tool, minimumMaterial));
		int[] desiredItemIds = get(desiredItems);
		
		// Check if we have it already equipped
		int count = Equipment.getCount(desiredItemIds);
		if ( count > 0 )
			return true;
		
		// Check if it is in inventory, and equip if not.
		count = Inventory.getCount(desiredItemIds);
		if ( count > 0 ) {
			RSItem item = PlayerUtil.getFirstItemInInventory(desiredItems);
			Inventory.open();
			AntiBan.sleep(500, 250);
			item.click("");
			AntiBan.sleep(800, 500);
			return equipTool(tool, minimumMaterial);
		}
		
		// Lets check the bank!
		if ( !AIOBank.walkToNearestBankAndOpen() )
			return false;
		if ( BankingUtil.withdrawFirstItem(desiredItems) ) {
			while(Banking.isBankScreenOpen()) {
				Banking.close();
				AntiBan.sleep(500, 100);
			}
			return equipTool(tool, minimumMaterial);
		}
		
		// Not in bank, lets go to GE and buy it!
		// TODO implement GE stuff
		return false;
	}

	private static Tools[] getToolTypes(ToolType type, EquipmentMaterial minimumMaterial) {
		
		// Get Tools that match type, and minimum quality
		List<Tools> ret = new ArrayList<>();
		Tools[] temp = Tools.values();
		for (Tools tool : temp) {
			if ( !tool.getType().equals(type) )
				continue;
			
			if ( tool.getMaterial().getQuality() < minimumMaterial.getQuality() )
				continue;
			
			ret.add(tool);
		}
		
		// Sort based on quality
		Collections.sort(ret, new Comparator<Tools>() {
			@Override
			public int compare(Tools arg0, Tools arg1) {
				return compareQuality(arg0.getMaterial().getQuality(), arg1.getMaterial().getQuality());
			}
		});
		
		return ret.toArray(new Tools[ret.size()]);
	}
	
	private static int compareQuality(int a, int b) {
		return a-b;
	}

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
				return compareQuality(arg0.getMaterial().getQuality(), arg1.getMaterial().getQuality());
			}
		});
		
		// Turn into list of ItemIds
		ItemIds[] ret = new ItemIds[temp.size()];
		for (int i = 0; i < temp.size(); i++) {
			ret[i] = temp.get(i).getItem();
		}
		
		return ret;
	}
	
	private static ItemIds[] convertToItems(Tools[] toolTypes) {
		// Turn into list of ItemIds
		ItemIds[] ret = new ItemIds[toolTypes.length];
		for (int i = 0; i < toolTypes.length; i++) {
			ret[i] = toolTypes[i].getItem();
		}
		
		return ret;
	}

	public static enum Tools {
		BRONZE_PICKAXE(EquipmentMaterial.BRONZE, ToolType.PICKAXE, ItemNames.BRONZE_PICKAXE),
		IRON_PICKAXE(EquipmentMaterial.IRON, ToolType.PICKAXE, ItemNames.IRON_PICKAXE),
		STEEL_PICKAXE(EquipmentMaterial.STEEL, ToolType.PICKAXE, ItemNames.STEEL_PICKAXE),
		MITHRIL_PICKAXE(EquipmentMaterial.MITHRIL, ToolType.PICKAXE, ItemNames.MITHRIL_PICKAXE),
		ADAMANT_PICKAXE(EquipmentMaterial.ADAMANT, ToolType.PICKAXE, ItemNames.ADAMANT_PICKAXE),
		RUNE_PICKAXE(EquipmentMaterial.RUNE, ToolType.PICKAXE, ItemNames.RUNE_PICKAXE);
		
		private ItemIds item;
		private EquipmentMaterial material;
		private ToolType type;
		
		private Tools(EquipmentMaterial material, ToolType type, ItemIds item) {
			this.material = material;
			this.type = type;
			this.item = item;
		}
		
		public ToolType getType() {
			return this.type;
		}
		
		public EquipmentMaterial getMaterial() {
			return this.material;
		}
		
		public ItemIds getItem() {
			return this.item;
		}
	}
	
	public static enum ToolType {
		PICKAXE,
		SWORD,
	}
	
	public static enum EquipmentMaterial {
		BRONZE(1),
		IRON(2),
		STEEL(3),
		BLACK(4),
		MITHRIL(5),
		ADAMANT(6),
		RUNE(7),
		DRAGON(8);
		
		private int quality;
		
		public int getQuality() {
			return this.quality;
		}
		
		EquipmentMaterial(int quality) {
			this.quality = quality;
		}
	}
	
	public static enum ArmorType {
		PLATEBODY,
		LEGGINGS,
		HELMET,
		BOOTS
	}
	
	public static enum Armor {
		BRONZE_PLATEBODY(EquipmentMaterial.BRONZE, ArmorType.PLATEBODY, ItemNames.BRONZE_PLATEBODY),
		IRON_PLATEBODY(EquipmentMaterial.IRON, ArmorType.PLATEBODY, ItemNames.IRON_PLATEBODY),
		STEEL_PLATEBODY(EquipmentMaterial.STEEL, ArmorType.PLATEBODY, ItemNames.STEEL_PLATEBODY),
		MITHRIL_PLATEBODY(EquipmentMaterial.MITHRIL, ArmorType.PLATEBODY, ItemNames.MITHRIL_PLATEBODY),
		ADAMANT_PLATEBODY(EquipmentMaterial.ADAMANT, ArmorType.PLATEBODY, ItemNames.ADAMANT_PLATEBODY),
		RUNE_PLATEBODY(EquipmentMaterial.RUNE, ArmorType.PLATEBODY, ItemNames.RUNE_PLATEBODY);
		
		private ItemIds item;
		private EquipmentMaterial material;
		private ArmorType type;
		
		private Armor(EquipmentMaterial material, ArmorType type, ItemIds item) {
			this.material = material;
			this.type = type;
			this.item = item;
		}
		
		public ArmorType getType() {
			return this.type;
		}
		
		public EquipmentMaterial getMaterial() {
			return this.material;
		}
		
		public ItemIds getItem() {
			return this.item;
		}
	}
}
