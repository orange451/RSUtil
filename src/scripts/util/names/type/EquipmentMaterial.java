package scripts.util.names.type;

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
}