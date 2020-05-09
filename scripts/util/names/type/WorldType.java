package scripts.util.names.type;

public enum WorldType {
	FREE,
    MEMBER;
	
	public static WorldType getWorldType(int world) {
		for (int t : f2pWorld) {
			if ( t == world )
				return FREE;
		}
		
		return MEMBER;
	}

	/**
	 * Open world menu on title screen
	 * Sort by Type Descending.
	 * Write all the f2p worlds in this array.
	 */
	private static final int[] f2pWorld = new int[] {
		301,
		308,
		316,
		326,
		335,
		371,
		372,
		379,
		380,
		381,
		382,
		383,
		384,
		385,
		393,
		394,
		397,
		398,
		399,
		413,
		414,
		418,
		419,
		425,
		426,
		427,
		430,
		431,
		432,
		433,
		434,
		435,
		436,
		437,
		438,
		439,
		440,
		451,
		452,
		452,
		453,
		454,
		455,
		456,
		457,
		458,
		459,
		468,
		469,
		470,
		471,
		472,
		473,
		474,
		475,
		476,
		483,
		497,
		498,
		499,
		500,
		501,
		502,
		503,
		504,
		530
	};
}
