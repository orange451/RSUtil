package scripts.util.names.type;

import java.util.Arrays;
import java.util.Comparator;

public enum EquipmentMaterial {
	ALL(Integer.MIN_VALUE, 0),
	WOODEN(0, 1),
	BRONZE(1, 1),
	IRON(2, 1),
	STEEL(3, 5),
	BLACK(4, 10),
	MITHRIL(5, 20),
	ADAMANT(6, 30),
	RUNE(7, 40),
	DRAGON(8, 60);
	
	private int quality;
	private int minimumEquipLevel;
	
	public int getQuality() {
		return this.quality;
	}
	
	public int getMinimumEquipLevel() {
		return this.minimumEquipLevel;
	}
	
	EquipmentMaterial(int quality, int minimumEquipLevel) {
		this.quality = quality;
		this.minimumEquipLevel = minimumEquipLevel;
	}
	
	public static EquipmentMaterial getBest(int equipLevel) {
		// Get all materials in order of quality
		EquipmentMaterial[] materials = EquipmentMaterial.values();
		Arrays.sort(materials, new Comparator<EquipmentMaterial>() {
			@Override
			public int compare(EquipmentMaterial a, EquipmentMaterial b) {
				return a.getQuality() - b.getQuality();
			}
		});
		
		// Get highest material based on equip level
		EquipmentMaterial bestMaterial = materials[0];
		for (EquipmentMaterial material : materials) {
			if ( material == ALL )
				continue;
			if ( material.getMinimumEquipLevel() <= equipLevel ) {
				bestMaterial = material;
			}
		}
		
		// Return
		return bestMaterial;
	}
}