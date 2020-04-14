package scripts.util.aio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.tribot.api.General;
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
	
	/**
	 * Equip a tool type with a minimum material type.<br>
	 * Instantly returns true if tool is currently equipped.<br>
	 * If not equipped, first checks inventory to see if it is currently in inventory but not equipped.<br>
	 * If not in inventory, it checks the bank for the item.<br>
	 * TODO Make it go to GE and buy item.
	 * @return Whether or not the tool was/is equipped.
	 */
	public static boolean equipTool(ToolType tool) {
		return equipTool(tool, EquipmentMaterial.BRONZE);
	}
	
	/**
	 * Equip a tool type with a minimum material type.<br>
	 * Instantly returns true if tool is currently equipped.<br>
	 * If not equipped, first checks inventory to see if it is currently in inventory but not equipped.<br>
	 * If not in inventory, it checks the bank for the item.<br>
	 * TODO Make it go to GE and buy item.
	 * @return Whether or not the tool was/is equipped.
	 */
	public static boolean equipTool(ToolType tool, EquipmentMaterial minimumMaterial) {
		ItemIds[] desiredItems = convertToItems(getToolTypes(tool, minimumMaterial));
		int[] desiredItemIds = get(desiredItems);
		
		// Check if we have it already equipped
		int count = Equipment.getCount(desiredItemIds);
		if ( count > 0 ) {
			General.println("Has equipped: " + tool);
			return true;
		}
		
		General.println("Checking inventory for: " + tool);
		
		// Check if it is in inventory, and equip if not.
		count = Inventory.getCount(desiredItemIds);
		if ( count > 0 ) {
			RSItem item = PlayerUtil.getFirstItemInInventory(desiredItems);
			Inventory.open();
			AntiBan.sleep(500, 250);
			item.click("wield");
			AntiBan.sleep(800, 500);
			return equipTool(tool, minimumMaterial);
		}
		
		General.println("Checking bank for: " + tool + "(" + Arrays.toString(desiredItemIds) + ")");
		
		// Go to bank
		if ( !AIOBank.walkToNearestBankAndOpen() )
			return false;
		
		// Withdraw
		if ( BankingUtil.withdrawFirstItem(desiredItems) )
			return equipTool(tool, minimumMaterial);
		
		General.println("Could not find items at bank! " + "(" + Arrays.toString(desiredItemIds) + ")");
		
		// Not in bank, lets go to GE and buy it!
		// TODO implement GE stuff
		return false;
	}

	public static RSItem equipArmor(ArmorType armor) {
		return equipArmor( armor, EquipmentMaterial.BRONZE );
	}

	public static RSItem equipArmor(ArmorType armor, EquipmentMaterial minimumMaterial) {
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
	
	private static Armor[] getArmorTypes(ArmorType type) {
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
		
		return temp.toArray(new Armor[temp.size()]);
	}
	
	private static int compareQuality(int a, int b) {
		return a-b;
	}
	
	private static ItemIds[] convertToItems(Tools[] toolTypes) {
		// Turn into list of ItemIds
		ItemIds[] ret = new ItemIds[toolTypes.length];
		for (int i = 0; i < toolTypes.length; i++) {
			ret[i] = toolTypes[i].getItem();
		}
		
		return ret;
	}
	
	private static ItemIds[] convertToItems(Armor[] armorTypes) {
		// Turn into list of ItemIds
		ItemIds[] ret = new ItemIds[armorTypes.length];
		for (int i = 0; i < armorTypes.length; i++) {
			ret[i] = armorTypes[i].getItem();
		}
		
		return ret;
	}

	public static enum Tools {
		BRONZE_SWORD(EquipmentMaterial.BRONZE, ToolType.SWORD, ItemNames.BRONZE_SWORD),
		IRON_SWORD(EquipmentMaterial.IRON, ToolType.SWORD, ItemNames.IRON_SWORD),
		STEEL_SWORD(EquipmentMaterial.STEEL, ToolType.SWORD, ItemNames.STEEL_SWORD),
		MITHRIL_SWORD(EquipmentMaterial.MITHRIL, ToolType.SWORD, ItemNames.MITHRIL_SWORD),
		ADAMANT_SWORD(EquipmentMaterial.ADAMANT, ToolType.SWORD, ItemNames.ADAMANT_SWORD),
		RUNE_SWORD(EquipmentMaterial.RUNE, ToolType.SWORD, ItemNames.RUNE_SWORD),

		BRONZE_SCIMITAR(EquipmentMaterial.BRONZE, ToolType.SWORD, ItemNames.BRONZE_SCIMITAR),
		IRON_SCIMITAR(EquipmentMaterial.IRON, ToolType.SWORD, ItemNames.IRON_SCIMITAR),
		STEEL_SCIMITAR(EquipmentMaterial.STEEL, ToolType.SWORD, ItemNames.STEEL_SCIMITAR),
		MITHRIL_SCIMITAR(EquipmentMaterial.MITHRIL, ToolType.SWORD, ItemNames.MITHRIL_SCIMITAR),
		ADAMANT_SCIMITAR(EquipmentMaterial.ADAMANT, ToolType.SWORD, ItemNames.ADAMANT_SCIMITAR),
		RUNE_SCIMITAR(EquipmentMaterial.RUNE, ToolType.SWORD, ItemNames.RUNE_SCIMITAR),
		
		BRONZE_LONGSWORD(EquipmentMaterial.BRONZE, ToolType.SWORD, ItemNames.BRONZE_LONGSWORD),
		IRON_LONGSWORD(EquipmentMaterial.IRON, ToolType.SWORD, ItemNames.IRON_LONGSWORD),
		STEEL_LONGSWORD(EquipmentMaterial.STEEL, ToolType.SWORD, ItemNames.STEEL_LONGSWORD),
		MITHRIL_LONGSWORD(EquipmentMaterial.MITHRIL, ToolType.SWORD, ItemNames.MITHRIL_LONGSWORD),
		ADAMANT_LONGSWORD(EquipmentMaterial.ADAMANT, ToolType.SWORD, ItemNames.ADAMANT_LONGSWORD),
		RUNE_LONGSWORD(EquipmentMaterial.RUNE, ToolType.SWORD, ItemNames.RUNE_LONGSWORD),

		BRONZE_PICKAXE(EquipmentMaterial.BRONZE, ToolType.PICKAXE, ItemNames.BRONZE_PICKAXE),
		IRON_PICKAXE(EquipmentMaterial.IRON, ToolType.PICKAXE, ItemNames.IRON_PICKAXE),
		STEEL_PICKAXE(EquipmentMaterial.STEEL, ToolType.PICKAXE, ItemNames.STEEL_PICKAXE),
		MITHRIL_PICKAXE(EquipmentMaterial.MITHRIL, ToolType.PICKAXE, ItemNames.MITHRIL_PICKAXE),
		ADAMANT_PICKAXE(EquipmentMaterial.ADAMANT, ToolType.PICKAXE, ItemNames.ADAMANT_PICKAXE),
		RUNE_PICKAXE(EquipmentMaterial.RUNE, ToolType.PICKAXE, ItemNames.RUNE_PICKAXE),

		BRONZE_AXE(EquipmentMaterial.BRONZE, ToolType.AXE, ItemNames.BRONZE_AXE),
		IRON_AXE(EquipmentMaterial.IRON, ToolType.AXE, ItemNames.IRON_AXE),
		STEEL_AXE(EquipmentMaterial.STEEL, ToolType.AXE, ItemNames.STEEL_AXE),
		MITHRIL_AXE(EquipmentMaterial.MITHRIL, ToolType.AXE, ItemNames.MITHRIL_AXE),
		ADAMANT_AXE(EquipmentMaterial.ADAMANT, ToolType.AXE, ItemNames.ADAMANT_AXE),
		RUNE_AXE(EquipmentMaterial.RUNE, ToolType.AXE, ItemNames.RUNE_AXE),

		WOODEN_SHIELD(EquipmentMaterial.WOODEN, ToolType.SHIELD, ItemNames.WOODEN_SHIELD),
		
		BRONZE_SQ_SHIELD(EquipmentMaterial.BRONZE, ToolType.SHIELD, ItemNames.BRONZE_SQ_SHIELD),
		IRON_SQ_SHIELD(EquipmentMaterial.IRON, ToolType.SHIELD, ItemNames.IRON_SQ_SHIELD),
		STEEL_SQ_SHIELD(EquipmentMaterial.STEEL, ToolType.SHIELD, ItemNames.STEEL_SQ_SHIELD),
		MITHRIL_SQ_SHIELD(EquipmentMaterial.MITHRIL, ToolType.SHIELD, ItemNames.MITHRIL_SQ_SHIELD),
		ADAMANT_SQ_SHIELD(EquipmentMaterial.ADAMANT, ToolType.SHIELD, ItemNames.ADAMANT_SQ_SHIELD),
		RUNE_SQ_SHIELD(EquipmentMaterial.RUNE, ToolType.SHIELD, ItemNames.RUNE_SQ_SHIELD),

		BRONZE_KITESHIELD(EquipmentMaterial.BRONZE, ToolType.SHIELD, ItemNames.BRONZE_KITESHIELD),
		IRON_KITESHIELD(EquipmentMaterial.IRON, ToolType.SHIELD, ItemNames.IRON_KITESHIELD),
		STEEL_KITESHIELD(EquipmentMaterial.STEEL, ToolType.SHIELD, ItemNames.STEEL_KITESHIELD),
		MITHRIL_KITESHIELD(EquipmentMaterial.MITHRIL, ToolType.SHIELD, ItemNames.MITHRIL_KITESHIELD),
		ADAMANT_KITESHIELD(EquipmentMaterial.ADAMANT, ToolType.SHIELD, ItemNames.ADAMANT_KITESHIELD),
		RUNE_KITESHIELD(EquipmentMaterial.RUNE, ToolType.SHIELD, ItemNames.RUNE_KITESHIELD);
		
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
		AXE,
		SHIELD,
	}
	
	public static enum EquipmentMaterial {
		ALL(Integer.MIN_VALUE),
		WOODEN(0),
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
		BRONZE_CHAINBODY(EquipmentMaterial.BRONZE, ArmorType.PLATEBODY, ItemNames.BRONZE_CHAINBODY),
		IRON_PLATEBODY(EquipmentMaterial.IRON, ArmorType.PLATEBODY, ItemNames.IRON_PLATEBODY),
		IRON_CHAINBODY(EquipmentMaterial.IRON, ArmorType.PLATEBODY, ItemNames.IRON_CHAINBODY),
		STEEL_PLATEBODY(EquipmentMaterial.STEEL, ArmorType.PLATEBODY, ItemNames.STEEL_PLATEBODY),
		STEEL_CHAINBODY(EquipmentMaterial.STEEL, ArmorType.PLATEBODY, ItemNames.STEEL_CHAINBODY),
		MITHRIL_PLATEBODY(EquipmentMaterial.MITHRIL, ArmorType.PLATEBODY, ItemNames.MITHRIL_PLATEBODY),
		MITHRIL_CHAINBODY(EquipmentMaterial.MITHRIL, ArmorType.PLATEBODY, ItemNames.MITHRIL_CHAINBODY),
		ADAMANT_PLATEBODY(EquipmentMaterial.ADAMANT, ArmorType.PLATEBODY, ItemNames.ADAMANT_PLATEBODY),
		ADAMANT_CHAINBODY(EquipmentMaterial.ADAMANT, ArmorType.PLATEBODY, ItemNames.ADAMANT_CHAINBODY),
		RUNE_PLATEBODY(EquipmentMaterial.RUNE, ArmorType.PLATEBODY, ItemNames.RUNE_PLATEBODY),
		RUNE_CHAINBODY(EquipmentMaterial.RUNE, ArmorType.PLATEBODY, ItemNames.RUNE_CHAINBODY);
		
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
