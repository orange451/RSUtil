package scripts.util;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSPlayer;
import org.tribot.api2007.types.RSTile;

public enum Locations {
	LUMBRIDGE(          new Point(3201, 3233), new Point(3226, 3205), 0 ),
	LUMBRIDGE_CHURCH(   new Point(3240, 3215), new Point(3247, 3204), 0 ),
	LUMBRIDGE_SHOP_AXE( new Point(3228, 3205), new Point(3233, 3201), 0 ),
	LUMBRIDGE_KITCHEN(  new Point(3205, 3217), new Point(3211, 3212), 0 ),
	LUMBRIDGE_SPINNING( new Point(3208, 3217), new Point(3212, 3212), 1 ),
	LUMBRIDGE_BANK(     new Point(3207, 3220), new Point(3210, 3217), 2 ),

	ALKHARID(           new Point(3294, 3214), new Point(3306, 3193), 0 ),
	ALKHARID_MINE(      new Point(3296, 3289), new Point(3301, 3284), 0 ),
	ALKHARID_MINE_DEEP( new Point(3298, 3313), new Point(3302, 3305), 0 ),
	ALKHARID_SMELT(     new Point(3275, 3187), new Point(3278, 3185), 0 ),
	ALKHARID_BANK(      new Point(3269, 3169), new Point(3271, 3164), 0 ),
	ALKHARID_PALACE(    new Point(3287, 3171), new Point(3298, 3167), 0 ),

	VARROK(                 new Point(3182, 3450), new Point(3257, 3400), 0 ),
	VARROK_BANK_EAST(       new Point(3250, 3423), new Point(3257, 3420), 0 ),
	VARROK_BANK_WEST(       new Point(3181, 3445), new Point(3185, 3435), 0 ),
	VARROK_MINE_WEST(       new Point(3172, 3379), new Point(3184, 3364), 0 ),
	VARROK_MINE_EAST(       new Point(3280, 3370), new Point(3290, 3360), 0 ),
	VARROK_SMITH_EAST(      new Point(3185, 3427), new Point(3190, 3420), 0 ),
	VARROK_SMITH_WEST(      new Point(3246, 3409), new Point(3251, 3403), 0 ),
	VARROK_TAVERN(          new Point(3218, 3402), new Point(3226, 3393), 0 ),
	VARROK_TAVERN_CHEST(    new Point(3218, 3396), new Point(3222, 3394), 1 ),
	VARROK_SHOP_SWORD(      new Point(3202, 3403), new Point(3208, 3395), 0 ),
	VARROK_SHOP_GENERAL(    new Point(3214, 3419), new Point(3220, 3411), 0 ),
	VARROK_SHOP_ARMOR(      new Point(3227, 3441), new Point(3232, 3433), 0 ),
	VARROK_SHOP_ARROWS(     new Point(3230, 3426), new Point(3235, 3421), 0 ),
	VARROK_SHOP_STAFF(      new Point(3201, 3436), new Point(3204, 3431), 0 ),
	VARROK_CHURCH(          new Point(3252, 3487), new Point(3259, 3476), 0 ),
	VARROK_CASTLE_LOWER(    new Point(3201, 3498), new Point(3224, 3471), 0 ),
	VARROK_CASTLE_NW_STAIR( new Point(3200, 3500), new Point(3206, 3494), 2 ),
	VARROK_CASTLE_LIBRARY(  new Point(3207, 3497), new Point(3214, 3490), 0 ),
	VARROK_SQUARE(          new Point(3206, 3437), new Point(3221, 3422), 0 ),
	VARROK_WILDERNESS(      new Point(3238, 3520), new Point(3254, 3514), 0 ),

	DRAYNOR(             new Point(3083, 3279), new Point(3105, 3249), 0 ),
	DRAYNOR_BANK(        new Point(3092, 3246), new Point(3095, 3241), 0 ),
	DRAYNOR_MANOR(       new Point(3092, 3373), new Point(3123, 3354), 0 ),
	DRAYNOR_MANOR_START( new Point(3106, 3332), new Point(3114, 3324), 0 ),

	WIZARD_TOWER(          new Point(3103, 3166), new Point(3114, 3157), 0 ),
	WIZARD_TOWER_STAIRS(   new Point(3106, 3161), new Point(3107, 3160), 0 ),
	WIZARD_TOWER_TRAIBORN( new Point(3111, 3164), new Point(3113, 3160), 1 ),
	WIZARD_TOWER_MIZGOG(   new Point(3103, 3165), new Point(3105, 3162), 2 ),
	WIZARD_TOWER_GRAYZAG(  new Point(3109, 3162), new Point(3111, 3159), 2 ),
	WIZARD_TOWER_SEDRIDOR( new Point(3109, 9573), new Point(3107, 9568), 0 ),

	PORT_SARIM(          new Point(3010, 3262), new Point(3026, 3241), 0 ),
	PORT_SARIM_JAIL(     new Point(3010, 3192), new Point(3012, 3187), 0 ),
	PORT_SARIM_REDBEARD( new Point(3050, 3253), new Point(3055, 3248), 0 ),

	FALADOR(                  new Point(2939, 3392), new Point(3056, 3331), 0 ),
	FALADOR_SQUARE(           new Point(2961, 3386), new Point(2969, 3375), 0 ),
	FALADOR_BANK_EAST(        new Point(3009, 3358), new Point(3018, 3355), 0 ),
	FALADOR_BANK_WEST(        new Point(2943, 3371), new Point(2947, 3368), 0 ),
	FALADOR_SHOP_SHIELD(      new Point(2972, 3384), new Point(2979, 3382), 0 ),
	FALADOR_CASTLE(           new Point(2961, 3349), new Point(2970, 3337), 0 ),
	FALADOR_CASTLE_EAST(      new Point(2979, 3349), new Point(2982, 3342), 0 ),
	FALADOR_CASTLE_SIR_VYVIN( new Point(2981, 3341), new Point(2984, 3334), 2 ),
	FALADOR_CASTLE_SIR_AMIK(  new Point(2958, 3341), new Point(2963, 3336), 2 ),

	BARBARIAN_VILLAGE(             new Point(3072, 3426), new Point(3089, 3412), 0 ),
	BARBARIAN_VILLAGE_SMELT(       new Point(3083, 3411), new Point(3086, 3407), 0 ),
	BARBARIAN_VILLAGE_SHOP_HELMET( new Point(3074, 3431), new Point(3077, 3427), 0 ),

	RIMMINGTON(         new Point(2953, 3217), new Point(2962, 3207), 0 ),
	RIMMINGTON_WITCH(   new Point(2966, 3207), new Point(2969, 3204), 0 ),
	RIMMINGTON_RATS(    new Point(2953, 3205), new Point(2960, 3202), 0 ),
	RIMMINGTON_CHEMIST( new Point(2930, 3212), new Point(2935, 3208), 0 ),

	DWARVEN_MINE( new Point(3011, 3451), new Point(3019, 3446), 0 ),

	BLACK_KNIGHTS_FORTRESS( new Point(3024, 3503), new Point(3030, 3498), 0 ),

	EDGEVILLE(            new Point(3080, 3468), new Point(3110, 3521), 0 ),
	EDGEVILLE_WILDERNESS( new Point(3091, 3498), new Point(3098, 3488), 0 ),
	EDGEVILLE_SMELT(      new Point(3105, 3501), new Point(3110, 3497), 0 ),

	GRAND_EXCHANGE(  new Point(3140, 3469), new Point(3192, 3512), 0 );

	private static ArrayList<Locations> locs = new ArrayList<Locations>();
	static {
		for (Locations loc : values()) {
			locs.add(loc);
		}
	}

	private Rectangle location;
	private int floor;
	private Locations( Point northWest, Point southEast, int floor ) {
		int x = Math.min( northWest.x, southEast.x );
		int y = Math.min( northWest.y, southEast.y );
		int w = Math.abs( northWest.x - southEast.x );
		int h = Math.abs( northWest.y - southEast.y );
		this.location = new Rectangle( x, y, w, h);
		this.floor = floor;
	}

	/**
	 * Returns the center-point of this location.
	 * @return
	 */
	public RSTile getCenter() {
		return new RSTile(this.location.x + this.location.width / 2, this.location.y + this.location.height / 2, floor);
	}

	/**
	 * Returns a randomized-point within (distance) of the center of this location.
	 * @return
	 */
	public RSTile getRandomizedCenter( float distance ) {
		float scalex = Math.min( distance, location.width/2 );
		float scaley = Math.min( distance, location.height/2 );
		int xx = (int) ((Math.random() - Math.random()) * scalex);
		int yy = (int) ((Math.random() - Math.random()) * scaley);
		return new RSTile(xx + this.location.x + this.location.width / 2, yy + this.location.y + this.location.height / 2, floor);
	}

	/**
	 * Returns a randomized-point within the location.
	 * @return
	 */
	public RSTile getRandomizedPosition() {
		int xx = (int) (Math.random() * this.location.width);
		int yy = (int) (Math.random() * this.location.height);
		return new RSTile(this.location.x + xx, this.location.y + yy, floor);
	}

	/**
	 * Returns whether or not an RSPlayer object is currently inside this location.
	 * @param player
	 * @return
	 */
	public boolean contains(RSPlayer player) {
		return contains( player.getPosition() );
	}

	/**
	 * Returns whether or not a specific tile is located within this location.
	 * @param tile
	 * @return
	 */
	public boolean contains(RSTile tile) {
		return this.location.contains(tile.getX(), tile.getY()) && tile.getPlane() == this.floor;
	}

	public static Locations get(RSTile position) {
		for (int i = 0; i < locs.size(); i++) {
			if ( locs.get(i).contains(position) ) {
				return locs.get(i);
			}
		}
		return null;
	}

	public static int getFloor( Locations loc ) {
		return loc.floor;
	}

	public static boolean isUnderGround( Locations loc ) {
		return isUnderGround( loc.getCenter() );
	}

	public static boolean isUnderGround( RSTile tile ) {
		return tile.getPlane() == 0 && tile.getY() > 8000;
	}

	public int getFloor() {
		return this.floor;
	}

	public Rectangle getBounds() {
		return this.location;
	}

	public static Locations closestLocation(String match, Locations from) {
		Locations ret = null;
		int dist = 99999;
		for (int i = 0; i < locs.size(); i++) {
			Locations loc = locs.get(i);
			if ( loc.toString().toLowerCase().contains(match.toLowerCase())) {
				int d = loc.getCenter().distanceTo(from.getCenter());
				if ( d < dist ) {
					dist = d;
					ret = loc;
				}
			}
		}

		return ret;
	}

}