package scripts.util.names;

public enum GeneralStores implements LocationCollection {
	VARROK(Locations.VARROK_SHOP_GENERAL),
	EDGEVILLE(Locations.EDGEVILLE_SHOP_GENERAL),
	RIMMINGTON(Locations.RIMMINGTON_SHOP_GENERAL),
	LUMBRIDGE(Locations.LUMBRIDGE_SHOP_GENERAL),
	FALADOR(Locations.FALADOR_SHOP_GENERAL),
	ALKHARID(Locations.ALKHARID_SHOP_GENERAL);
	
	private Locations location;
	private boolean members;
	
	GeneralStores(Locations location) {
		this(location, false);
	}
	
	GeneralStores(Locations location, boolean members) {
		this.location = location;
		this.members = members;
	}

	@Override
	public boolean isMembers() {
		return this.members;
	}

	@Override
	public Locations getLocation() {
		return this.location;
	}
}
