package scripts.util.names.type;

public enum EquipmentMaterial {
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