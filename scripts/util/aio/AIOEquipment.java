package scripts.util.aio;

import org.tribot.api.General;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSItem;

import scripts.util.PlayerUtil;
import scripts.util.misc.AntiBan;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;
import scripts.util.names.type.ArmorClass;
import scripts.util.names.type.ArmorType;
import scripts.util.names.type.EquipmentMaterial;
import scripts.util.names.type.EquippableItem;
import scripts.util.names.type.ToolClass;
import scripts.util.names.type.ToolType;

public class AIOEquipment {
	
	/**
	 * Equip a tool type with a minimum material type.<br>
	 * Instantly returns true if tool is currently equipped.<br>
	 * If not equipped, first checks inventory to see if it is currently in inventory but not equipped.<br>
	 * If not in inventory, it checks the bank for the item.<br>
	 * TODO Make it go to GE and buy item.
	 * @return Whether or not the tool was/is equipped.
	 */
	public static boolean equipTool(ToolClass tool) {
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
	public static boolean equipTool(ToolClass tool, EquipmentMaterial minimumMaterial) {
		RSItem item = getToolAndEquipIfPossible(tool, minimumMaterial);
		if ( item == null )
			return false;
		
		return getFirstEquipped(tool, minimumMaterial) != null;
	}
	
	/**
	 * Get a tool type with a minimum material type.<br>
	 * Instantly returns true if tool is currently equipped.<br>
	 * If not equipped, first checks inventory to see if it is currently in inventory but not equipped.<br>
	 * If not in inventory, it checks the bank for the item.<br>
	 * TODO Make it go to GE and buy item.
	 * @return Whether or not the tool was/is equipped.
	 */
	public static RSItem getToolAndEquipIfPossible(ToolClass tool) {
		return getToolAndEquipIfPossible(tool, EquipmentMaterial.BRONZE);
	}
	
	/**
	 * Get a tool type with a minimum material type.<br>
	 * Instantly returns true if tool is currently equipped.<br>
	 * If not equipped, first checks inventory to see if it is currently in inventory but not equipped.<br>
	 * If not in inventory, it checks the bank for the item.<br>
	 * TODO Make it go to GE and buy item.
	 * @return Whether or not the tool was/is equipped.
	 */
	public static RSItem getToolAndEquipIfPossible(ToolClass tool, EquipmentMaterial minimumMaterial) {
		EquippableItem[] tools = ToolType.getToolTypes(tool, minimumMaterial);
		
		// Check if item is in inventory or equipment
		RSItem inventoryItem = getFirstTool(tool, minimumMaterial);
		if ( inventoryItem != null )
			return inventoryItem;
		
		// Try to go get the item (WITHOUT BUYING)
		EquippableItem bestTool = null;
		boolean SHOULD_USE_GE = AIOItem.CAN_USE_GE_TO_BUY_ITEMS;
		AIOItem.CAN_USE_GE_TO_BUY_ITEMS = false;
		for (EquippableItem toolObject : tools) {
			
			// Dont consider items we cant even use!
			if ( !canUseEquippableItem(toolObject) )
				continue;
			
			// Mark the best usable tool
			if ( bestTool == null )
				bestTool = toolObject;

			// Attempt to get and equip item
			RSItem item = AIOItem.getItem(toolObject.getItem(), false);
			if ( item != null ) {
				General.sleep(800);
				if ( attemptEquip(toolObject, item) ) {
					RSItem equipped = getToolAndEquipIfPossible(tool, minimumMaterial);
					if ( equipped != null )
						return equipped;
				}
				
				return item;
			}
		}
		
		// RESET GE VARIABLE TO USER DEFINED
		AIOItem.CAN_USE_GE_TO_BUY_ITEMS = SHOULD_USE_GE;
		
		// REDO LOGIC BUT WITH BUYING INSTEAD!
		// TODO Think of some way to not duplicate this whole chunk of code.........
		if ( SHOULD_USE_GE ) {
			for (EquippableItem toolObject : tools) {
				
				// Dont consider items we cant even use!
				if ( !canUseEquippableItem(toolObject) )
					continue;
				
				// Attempt to get and equip item
				RSItem item = AIOItem.getItem(toolObject.getItem(), false);
				if ( item != null ) {
					if ( attemptEquip(toolObject, item) ) {
						RSItem equipped = getToolAndEquipIfPossible(tool, minimumMaterial);
						if ( equipped != null )
							return equipped;
					}
					
					return item;
				}
			}
		}
		
		return null;
	}
	
	private static boolean canUseEquippableItem(EquippableItem tool) {
		int off = 0;
		if ( tool.getMaterial().getMinimumEquipLevel() > 1 )
			off = tool.getType().getSkillOffset();
		
		return tool.getType().getPrimarySkill().getActualLevel() >= tool.getMaterial().getMinimumEquipLevel() + off;
	}
	
	private static boolean attemptEquip(EquippableItem tool, RSItem item) {
		if ( SKILLS.ATTACK.getActualLevel() >= tool.getMaterial().getMinimumEquipLevel()) {
			AntiBan.sleep(500, 250);
			item.click("wear", "wield");
			AntiBan.sleep(1250, 500);
			return true;
		}
		
		return false;
	}
	
	private static RSItem getFirstEquipped(ToolClass tool, EquipmentMaterial minimumMaterial) {
		ItemIds[] desiredItems = ItemNames.get(ToolType.getToolTypes(tool, minimumMaterial));
		return getFirstEquipped(desiredItems);
	}
	
	private static RSItem getFirstEquipped(ArmorClass armor, EquipmentMaterial minimumMaterial) {
		ItemIds[] desiredItems = ItemNames.get(ArmorType.getArmorTypes(armor, minimumMaterial));
		return getFirstEquipped(desiredItems);
	}
	
	private static RSItem getFirstEquipped(ItemIds[] desiredItems) {
		int[] desiredItemIds = ItemNames.get(desiredItems);
		
		// Check if we have it already equipped
		int count = Equipment.getCount(desiredItemIds);
		if ( count > 0 ) {
			for (RSItem item : Equipment.getItems()) {
				for (int id : desiredItemIds) {
					/*ToolType toolType = ToolType.match(ItemNames.get(id));
					if ( toolType == null )
						continue;
					
					if ( !canUseEquippableItem(toolType) )
						continue;*/
					
					if ( id == item.getID() )
						return item;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Returns the first tool matching the provided toolclass and minimum material type.
	 * The tool can be either equipped or in inventory..
	 * @param tool
	 * @param minimumMaterial
	 * @return
	 */
	public static RSItem getFirstTool(ToolClass tool, EquipmentMaterial minimumMaterial) {

		// Check for item in equipment
		RSItem equipped = getFirstEquipped(tool, minimumMaterial);
		if ( equipped != null )
			return equipped;
		
		// Check for item in inventory
		RSItem inventoryItem = PlayerUtil.getFirstItemInInventory(ItemNames.get(ToolType.getToolTypes(tool, minimumMaterial)));
		if ( inventoryItem != null )
			return inventoryItem;
		
		return null;
	}
	
	/**
	 * Returns the first armor matching the provided armorclass and minimum material type.
	 * The armor can be either equipped or in inventory..
	 * @param armor
	 * @param minimumMaterial
	 * @return
	 */
	public static RSItem getFirstArmor(ArmorClass armor, EquipmentMaterial minimumMaterial) {

		// Check for item in equipment
		RSItem equipped = getFirstEquipped(armor, minimumMaterial);
		if ( equipped != null )
			return equipped;
		
		// Check for item in inventory
		RSItem inventoryItem = PlayerUtil.getFirstItemInInventory(ItemNames.get(ArmorType.getArmorTypes(armor, minimumMaterial)));
		if ( inventoryItem != null )
			return inventoryItem;
		
		return null;
	}

	public static boolean equipArmor(ArmorClass armor) {
		return equipArmor( armor, EquipmentMaterial.BRONZE );
	}

	public static boolean equipArmor(ArmorClass armor, EquipmentMaterial minimumMaterial) {
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
		
		RSItem item = getArmorAndEquipIfPossible(armor, minimumMaterial);
		if ( item == null )
			return false;
		
		return getFirstEquipped(armor, minimumMaterial) != null;
	}
	
	/**
	 * Get a armor type with a minimum material type.<br>
	 * Instantly returns true if armor is currently equipped.<br>
	 * If not equipped, first checks inventory to see if it is currently in inventory but not equipped.<br>
	 * If not in inventory, it checks the bank for the item.<br>
	 * @return Whether or not the armor was/is equipped.
	 */
	public static RSItem getArmorAndEquipIfPossible(ArmorClass armor) {
		return getArmorAndEquipIfPossible(armor, EquipmentMaterial.BRONZE);
	}
	
	/**
	 * Get a armor type with a minimum material type.<br>
	 * Instantly returns true if tool is currently equipped.<br>
	 * If not equipped, first checks inventory to see if it is currently in inventory but not equipped.<br>
	 * If not in inventory, it checks the bank for the item.<br>
	 * TODO Make it go to GE and buy item.
	 * @return Whether or not the armor was/is equipped.
	 */
	public static RSItem getArmorAndEquipIfPossible(ArmorClass armor, EquipmentMaterial minimumMaterial) {
		EquippableItem[] armorTypes = ArmorType.getArmorTypes(armor, minimumMaterial);
		
		// Check if item is in inventory or equipment
		RSItem inventoryItem = getFirstArmor(armor, minimumMaterial);
		if ( inventoryItem != null )
			return inventoryItem;
		
		// Try to go get the item (WITHOUT BUYING)
		EquippableItem bestTool = null;
		boolean SHOULD_USE_GE = AIOItem.CAN_USE_GE_TO_BUY_ITEMS;
		AIOItem.CAN_USE_GE_TO_BUY_ITEMS = false;
		for (EquippableItem armorObject : armorTypes) {
			
			// Dont consider items we cant even use!
			if ( !canUseEquippableItem(armorObject) )
				continue;
			
			// Mark the best usable tool
			if ( bestTool == null )
				bestTool = armorObject;

			// Attempt to get and equip item
			RSItem item = AIOItem.getItem(armorObject.getItem(), false);
			if ( item != null ) {
				General.sleep(800);
				if ( attemptEquip(armorObject, item) ) {
					RSItem equipped = getArmorAndEquipIfPossible(armor, minimumMaterial);
					if ( equipped != null )
						return equipped;
				}
				
				return item;
			}
		}
		
		// RESET GE VARIABLE TO USER DEFINED
		AIOItem.CAN_USE_GE_TO_BUY_ITEMS = SHOULD_USE_GE;
		
		// REDO LOGIC BUT WITH BUYING INSTEAD!
		// TODO Think of some way to not duplicate this whole chunk of code.........
		if ( SHOULD_USE_GE ) {
			for (EquippableItem armorObject : armorTypes) {
				
				// Dont consider items we cant even use!
				if ( !canUseEquippableItem(armorObject) )
					continue;
				
				// Attempt to get and equip item
				RSItem item = AIOItem.getItem(armorObject.getItem(), false);
				if ( item != null ) {
					if ( attemptEquip(armorObject, item) ) {
						RSItem equipped = getArmorAndEquipIfPossible(armor, minimumMaterial);
						if ( equipped != null )
							return equipped;
					}
					
					return item;
				}
			}
		}
		
		return null;
	}
}
