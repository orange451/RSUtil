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
import scripts.util.names.type.ArmorClassification;
import scripts.util.names.type.ArmorType;
import scripts.util.names.type.EquipmentMaterial;
import scripts.util.names.type.ToolType;

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

	public static RSItem equipArmor(ArmorClassification armor) {
		return equipArmor( armor, EquipmentMaterial.BRONZE );
	}

	public static RSItem equipArmor(ArmorClassification armor, EquipmentMaterial minimumMaterial) {
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
	
	private static ToolType[] getToolTypes(ToolType type, EquipmentMaterial minimumMaterial) {
		
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
	
	private static ArmorType[] getArmorClassifications(ArmorClassification type) {
		ArmorType[] armorArray = ArmorType.values();
		
		// Get armors that match this type
		List<ArmorType> temp = new ArrayList<>();
		for (ArmorType armor : armorArray) {
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
