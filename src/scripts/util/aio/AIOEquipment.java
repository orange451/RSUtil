package scripts.util.aio;

import static scripts.util.names.internal.ItemNamesData.get;

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
import scripts.util.names.type.ArmorClass;
import scripts.util.names.type.ArmorType;
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
		int[] desiredItemIds = ItemNames.get(desiredItems);
		
		// Check if we have it already equipped
		int count = Equipment.getCount(desiredItemIds);
		if ( count > 0 ) {
			for (RSItem item : Equipment.getItems()) {
				for (Integer id : desiredItemIds) {
					if ( id == item.getID() )
						return item;
				}
			}
		}
		
		// Try to go get the item
		for (int i = 0; i < desiredItems.length; i++) {
			RSItem item = AIOItem.getItem(desiredItems[i]);
			if ( item != null ) {
				AntiBan.sleep(500, 250);
				item.click("wield");
				AntiBan.sleep(1250, 500);
				RSItem equipped = getToolAndEquipIfPossible(tool, minimumMaterial);
				if ( equipped != null )
					return equipped;
				return item;
			}
		}
		
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
