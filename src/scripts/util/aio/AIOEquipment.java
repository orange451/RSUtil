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
		ItemIds[] desiredItems = convertToItems(getToolTypes(tool, minimumMaterial));
		int[] desiredItemIds = get(desiredItems);
		
		// Check if we have it already equipped
		int count = Equipment.getCount(desiredItemIds);
		if ( count > 0 ) {
			General.println("Has equipped: " + tool);
			return true;
		}
		
		// Try to go get the item
		for (int i = 0; i < desiredItems.length; i++) {
			RSItem item = AIOItem.getItem(desiredItems[i]);
			if ( item != null ) {
				AntiBan.sleep(500, 250);
				item.click("wield");
				AntiBan.sleep(1000, 500);
				return equipTool(tool, minimumMaterial);
			}
		}
		
		return false;
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
	
	private static ToolType[] getToolTypes(ToolClass type, EquipmentMaterial minimumMaterial) {
		
		// Get Tools that match type, and minimum quality
		List<ToolType> ret = new ArrayList<>();
		ToolType[] temp = ToolType.values();
		for (ToolType tool : temp) {
			if ( !tool.getType().equals(type) )
				continue;
			
			if ( tool.getMaterial().getQuality() < minimumMaterial.getQuality() )
				continue;
			
			ret.add(tool);
		}
		
		// Sort based on quality
		Collections.sort(ret, new Comparator<ToolType>() {
			@Override
			public int compare(ToolType arg0, ToolType arg1) {
				return compareQuality(arg0.getMaterial().getQuality(), arg1.getMaterial().getQuality());
			}
		});
		
		return ret.toArray(new ToolType[ret.size()]);
	}
	
	private static ArmorType[] getArmorClassifications(ArmorClass type, EquipmentMaterial minimumMaterial) {
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
	
	private static ItemIds[] convertToItems(ToolType[] toolTypes) {
		// Turn into list of ItemIds
		ItemIds[] ret = new ItemIds[toolTypes.length];
		for (int i = 0; i < toolTypes.length; i++) {
			ret[i] = toolTypes[i].getItem();
		}
		
		return ret;
	}
	
	private static ItemIds[] convertToItems(ArmorType[] ArmorClassifications) {
		// Turn into list of ItemIds
		ItemIds[] ret = new ItemIds[ArmorClassifications.length];
		for (int i = 0; i < ArmorClassifications.length; i++) {
			ret[i] = ArmorClassifications[i].getItem();
		}
		
		return ret;
	}
}
