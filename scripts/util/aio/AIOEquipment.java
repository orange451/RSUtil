package scripts.util.aio;

import org.tribot.api2007.Equipment;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSItem;

import scripts.util.PlayerUtil;
import scripts.util.misc.AntiBan;
import scripts.util.names.ItemIds;
import scripts.util.names.ItemNames;
import scripts.util.names.type.ArmorClass;
import scripts.util.names.type.EquipmentMaterial;
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
		return getToolAndEquipIfPossible(tool, minimumMaterial) != null;
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
		ItemIds[] desiredItems = ItemNames.get(ToolType.getToolTypes(tool, minimumMaterial));
		
		// Check if item is in inventory or equipment
		RSItem inventoryItem = getFirstTool(tool, minimumMaterial);
		if ( inventoryItem != null )
			return inventoryItem;
		
		// Try to go get the item
		for (int i = 0; i < desiredItems.length; i++) {
			RSItem item = AIOItem.getItem(desiredItems[i]);
			if ( item != null ) {
				if ( SKILLS.ATTACK.getActualLevel() >= ToolType.match(ItemNames.get(item.getID())).getMaterial().getMinimumEquipLevel()) {
					AntiBan.sleep(500, 250);
					item.click("wield");
					AntiBan.sleep(1250, 500);
					RSItem equipped = getToolAndEquipIfPossible(tool, minimumMaterial);
					if ( equipped != null )
						return equipped;
				}
				
				return item;
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
		ItemIds[] desiredItems = ItemNames.get(ToolType.getToolTypes(tool, minimumMaterial));
		int[] desiredItemIds = ItemNames.get(desiredItems);
		
		// Check if we have it already equipped
		int count = Equipment.getCount(desiredItemIds);
		if ( count > 0 ) {
			for (RSItem item : Equipment.getItems()) {
				for (int id : desiredItemIds) {
					if ( id == item.getID() )
						return item;
				}
			}
		}
		
		// Check for item in inventory
		RSItem inventoryItem = PlayerUtil.getFirstItemInInventory(desiredItems);
		if ( inventoryItem != null )
			return inventoryItem;
		
		return null;
	}

	public static RSItem equipArmor(ArmorClass armor) {
		return equipArmor( armor, EquipmentMaterial.BRONZE );
	}

	public static RSItem equipArmor(ArmorClass armor, EquipmentMaterial minimumMaterial) {
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
}
