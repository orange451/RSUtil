package scripts.util.names;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

import scripts.util.misc.NameFormatter;

public enum Locations {
	LUMBRIDGE(new RSTile(3201, 3233), new RSTile(3226, 3205), 0), 
	LUMBRIDGE_CHURCH(new RSTile(3240, 3215), new RSTile(3247, 3204), 0), 
	LUMBRIDGE_SHOP_AXE(new RSTile(3228, 3205), new RSTile(3233, 3201), 0), 
	LUMBRIDGE_KITCHEN(new RSTile(3205, 3217), new RSTile(3211, 3212), 0),
	LUMBRIDGE_RANGE(new RSTile(3209, 3217), new RSTile(3212, 3212), 0),
	LUMBRIDGE_SPINNING(new RSTile(3208, 3217), new RSTile(3212, 3212), 1), 
	LUMBRIDGE_BANK(new RSTile(3207, 3220), new RSTile(3210, 3217), 2), 
	LUMBRIDGE_SWAMP(new RSTile(3194, 3185), new RSTile(3204, 3171), 0),
	LUMBRIDGE_DAIRY_COWS(new RSTile(3253, 3274), new RSTile(3256, 3276), 0),
	LUMBRIDGE_COWS_EAST(new RSTile(3252, 3291), new RSTile(3264, 3257), 0), 
	LUMBRIDGE_COWS_WEST(new RSTile(3194, 3300), new RSTile(3210, 3285), 0), 
	LUMBRIDGE_CHICKENS_EAST(new RSTile(3225, 3300), new RSTile(3236, 3289), 0), 
	LUMBRIDGE_CHICKENS_WEST(new RSTile(3174, 3300), new RSTile(3182, 3291), 0), 
	LUMBRIDGE_CHICKENS_WEST_SMALL(new RSTile(3185, 3278), new RSTile(3191, 3276), 0), 
	LUMBRIDGE_FRED_HOUSE(new RSTile(3188, 3274), new RSTile(3191, 3271), 0), 
	LUMBRIDGE_GOBLINS_EAST(new RSTile(3244, 3254), new RSTile(3264, 3218), 0), 
	LUMBRIDGE_GOBLINS_WEST(new RSTile(3139, 3305), new RSTile(3151, 3300), 0), 
	LUMBRIDGE_SHEEP(new RSTile(3193, 3275), new RSTile(3211, 3258), 0), 
	LUMBRIDGE_BASEMENT(new RSTile(3208, 9620), new RSTile(3210, 9615), 0), 
	LUMBRIDGE_WHEAT(new RSTile(3154, 3303), new RSTile(3160, 3296), 0), 
	LUMBRIDGE_MILL_GROUND(new RSTile(3165, 3308), new RSTile(3168, 3304), 0), 
	LUMBRIDGE_MILL_TOP(new RSTile(3165, 3308), new RSTile(3168, 3304), 2), 
	LUMBRIDGE_COOKING_TUTOR(new RSTile(3232, 3198), new RSTile(3236, 3196), 0), 
	LUMBRIDGE_FATHER_URHNEY(new RSTile(3144, 3177), new RSTile(3151, 3173), 0),
	LUMBRIDGE_GRAVE_YARD(new RSTile(3247, 3195), new RSTile(3252, 3190), 0),
	LUMBRIDGE_GAY_MANS_ROOM(new RSTile(3209, 3219), new RSTile(3212, 3224), 1),
	LUMBRIDGE_FISHING_AREA(new RSTile(3246, 3160), new RSTile(3238, 3142), 0),
	LUMBRIDGE_WILLOWS(new RSTile(3233, 3246), new RSTile(3235, 3235), 0),
	LUMBRIDGE_MINE(new RSTile(3222, 3149), new RSTile(3231, 3144), 0),
	LUMBRIDGE_MINE_WEST(new RSTile(3145, 3152, 0), new RSTile(3149, 3145, 0)),
	LUMBRIDGE_WOODS_YEWS(new RSTile(3151, 3231), new RSTile(3186, 3219), 0),
	LUMBRIDGE_FURNACE(new RSTile(3228, 3252), new RSTile(3223, 3257), 0),

	ALKHARID(new RSTile(3278, 3246), new RSTile(3322, 3177), 0), 
	ALKHARID_MINE(new RSTile(3296, 3289), new RSTile(3301, 3284), 0), 
	ALKHARID_MINE_DEEP(new RSTile(3298, 3313), new RSTile(3302, 3305), 0), 
	ALKHARID_FURNACE(new RSTile(3274, 3187, 0), new RSTile(3279, 3184, 0)), 
	ALKHARID_BANK(new RSTile(3269, 3169), new RSTile(3271, 3164), 0), 
	ALKHARID_TANNER(new RSTile(3271, 3189), new RSTile(3277, 3193), 0), 
	ALKHARID_PALACE(new RSTile(3287, 3171), new RSTile(3298, 3167), 0), 
	ALKHARID_RANGE(new RSTile(3271, 3183), new RSTile(3275, 3179), 0),
	ALKHARID_FISHING_AREA(new RSTile(3266, 3149), new RSTile(3269, 3147), 0),
	ALKHARID_AGILITY_START(new RSTile(3272, 3195), new RSTile(3274, 3196)),
	
	VARROK(new RSTile(3182, 3450), new RSTile(3257, 3400), 0),
	VARROK_AGILITY_START(new RSTile(3221, 3418, 0), new RSTile(3222, 3411, 0)),
	VARROK_BANK_EAST(new RSTile(3250, 3423), new RSTile(3257, 3420), 0), 
	VARROK_BANK_WEST(new RSTile(3181, 3445), new RSTile(3185, 3435), 0), 
	VARROK_MINE_WEST(new RSTile(3172, 3379), new RSTile(3184, 3364), 0), 
	VARROK_MINE_EAST(new RSTile(3280, 3370), new RSTile(3290, 3360), 0), 
	VARROK_SMITH_EAST(new RSTile(3185, 3427), new RSTile(3190, 3420), 0), 
	VARROK_SMITH_WEST(new RSTile(3246, 3409), new RSTile(3251, 3403), 0), 
	VARROK_TAVERN(new RSTile(3218, 3402), new RSTile(3226, 3393), 0), 
	VARROK_TAVERN_CHEST(new RSTile(3218, 3396), new RSTile(3222, 3394), 1), 
	VARROK_WOODS_WEST(new RSTile(3160, 3425), new RSTile(3169, 3411), 0),
	VARROK_SHOP_SWORD(new RSTile(3202, 3403), new RSTile(3208, 3395), 0), 
	VARROK_SHOP_GENERAL(new RSTile(3214, 3419), new RSTile(3220, 3411), 0), 
	VARROK_SHOP_ARMOR(new RSTile(3227, 3441), new RSTile(3232, 3433), 0), 
	VARROK_SHOP_ARROWS(new RSTile(3230, 3426), new RSTile(3235, 3421), 0), 
	VARROK_SHOP_STAFF(new RSTile(3201, 3436), new RSTile(3204, 3431), 0), 
	VARROK_SHOP_RUNES(new RSTile(3252, 3399), new RSTile(3254, 3403), 0),
	VARROK_CHURCH(new RSTile(3252, 3487), new RSTile(3259, 3476), 0),
	VARROK_CHURCH_YEW(new RSTile(3247, 3474), new RSTile(3251, 3471), 0),
	VARROK_CASTLE_KINGS_ROOM(new RSTile(3220, 3475, 0), new RSTile(3224, 3470, 0)),
	VARROK_CASTLE_LOWER(new RSTile(3201, 3498), new RSTile(3224, 3471), 0), 
	VARROK_CASTLE_NW_STAIR(new RSTile(3200, 3500), new RSTile(3206, 3494), 2), 
	VARROK_CASTLE_LIBRARY(new RSTile(3207, 3497), new RSTile(3214, 3490), 0),
	VARROK_CASTLE_YEWS(new RSTile(3203, 3505), new RSTile(3224, 3501), 0),
	VARROK_SQUARE(new RSTile(3206, 3437), new RSTile(3221, 3422), 0), 
	VARROK_WILDERNESS(new RSTile(3238, 3520), new RSTile(3254, 3514), 0), 
	VARROK_SEWER(new RSTile(3230, 9869), new RSTile(3249, 9864), 0), 
	VARROK_SEWER_DEEP(new RSTile(3247, 9918), new RSTile(3253, 9915), 0), 
	VARROK_ENTRANCE_WEST(new RSTile(3172, 3432), new RSTile(3179, 3424), 0), 
	VARROK_WIZARD(new RSTile(3219, 3376), new RSTile(3234, 3362), 0), 
	VARROK_WOODS_EAST(new RSTile(3275, 3432), new RSTile(3285, 3414), 0),
	VARROK_WOODS_YEWS(new RSTile(3265, 3495), new RSTile(3271, 3469), 0),
	VARROK_RANGE_WEST(new RSTile(3156, 3431), new RSTile(3164, 3427), 0),
	VARROK_RANGE_EAST(new RSTile(3240, 3409), new RSTile(3236, 3415), 0),
	
	DRAYNOR(new RSTile(3083, 3279), new RSTile(3105, 3249), 0), 
	DRAYNOR_BANK(new RSTile(3092, 3246), new RSTile(3095, 3241), 0), 
	DRAYNOR_SEWER(new RSTile(3088, 9674), new RSTile(3120, 9670), 0), 
	DRAYNOR_AGGIE(new RSTile(3087, 3259), new RSTile(3088, 3258), 0), 
	DRAYNOR_MANOR(new RSTile(3092, 3373), new RSTile(3123, 3354), 0), 
	DRAYNOR_MANOR_START(new RSTile(3108, 3330), new RSTile(3113, 3327), 0), 
	DRAYNOR_MANOR_ERNEST(new RSTile(3108, 3369), new RSTile(3112, 3363), 2), 
	DRAYNOR_MANOR_BASEMENT(new RSTile(3100, 9757), new RSTile(3117, 9750), 0), 
	DRAYNOR_MANOR_STUDY(new RSTile(3098, 3362), new RSTile(3101, 3356), 0), 
	DRAYNOR_MANOR_FISH(new RSTile(3107, 3360), new RSTile(3110, 3355), 1), 
	DRAYNOR_MANOR_BACK(new RSTile(3121, 3359), new RSTile(3124, 3355), 0), 
	DRAYNOR_MANOR_FARM(new RSTile(3084, 3362), new RSTile(3088, 3359), 0), 
	DRAYNOR_MANOR_CLOSET(new RSTile(3108, 3366), new RSTile(3111, 3368), 0),
	DRAYNOR_OAKS(new RSTile(3120, 3288), new RSTile(3113, 3213), 0),
	DRAYNOR_LOW_LVL_TREES(new RSTile(3074, 3264), new RSTile(3085,3274), 0),
	DRAYNOR_MANOR_FOUNTAIN(new RSTile(3085, 3336), new RSTile(3090, 3332), 0), 
	DRAYNOR_KITCHEN(new RSTile(3097, 3366), new RSTile(3100, 3365), 0), 
	DRAYNOR_MANOR_LOBBY(new RSTile(3106, 3359), new RSTile(3112, 3364), 0),
	DRAYNOR_WILLOW(new RSTile(3093, 3226), new RSTile(3085, 3239), 0),
	DRAYNOR_FISHING_AREA(new RSTile(3085, 3233), new RSTile(3089, 3223), 0),
	DRAYNOR_ROOFTOP_AGILITY_START(new RSTile(3103, 3280), new RSTile(3104, 3278)),

	WIZARDS_TOWER(new RSTile(3103, 3166), new RSTile(3114, 3157), 0), 
	WIZARDS_TOWER_TRAIBORN(new RSTile(3111, 3164), new RSTile(3113, 3160), 1), 
	WIZARDS_TOWER_MIZGOG(new RSTile(3103, 3165), new RSTile(3105, 3162), 2), 
	WIZARDS_TOWER_GRAYZAG(new RSTile(3109, 3162), new RSTile(3111, 3159), 2), 
	WIZARDS_TOWER_SEDRIDOR(new RSTile(3109, 9573), new RSTile(3107, 9568), 0), 
	WIZARDS_TOWER_BASEMENT(new RSTile(3108, 9577), new RSTile(3110, 9570), 0),
	WIZARDS_TOWER_ALTAR(new RSTile(3116, 9568), new RSTile(3121, 9565), 0),

	PORT_SARIM(new RSTile(3010, 3262), new RSTile(3026, 3241), 0), 
	PORT_SARIM_JAIL(new RSTile(3010, 3192), new RSTile(3012, 3187), 0), 
	PORT_SARIM_REDBEARD(new RSTile(3050, 3253), new RSTile(3055, 3248), 0), 
	PORT_SARIM_MAGIC(new RSTile(3012, 3260), new RSTile(3015, 3257), 0), 

	FALADOR(new RSTile(2939, 3392), new RSTile(3056, 3331), 0), 
	FALADOR_SQUARE(new RSTile(2961, 3386), new RSTile(2969, 3375), 0), 
	FALADOR_BANK_EAST(new RSTile(3009, 3358), new RSTile(3018, 3355), 0), 
	FALADOR_BANK_WEST(new RSTile(2943, 3371), new RSTile(2947, 3368), 0), 
	FALADOR_SHOP_SHIELD(new RSTile(2972, 3384), new RSTile(2979, 3382), 0), 
	FALADOR_FURNACE(new RSTile(2971, 3373), new RSTile(2975, 3368), 0), 
	FALADOR_CASTLE(new RSTile(2961, 3349), new RSTile(2970, 3337), 0), 
	FALADOR_CASTLE_EAST(new RSTile(2979, 3349), new RSTile(2982, 3342), 0), 
	FALADOR_CASTLE_SIR_VYVIN(new RSTile(2981, 3341), new RSTile(2984, 3334), 2), 
	FALADOR_CASTLE_SIR_AMIK(new RSTile(2958, 3341), new RSTile(2963, 3336), 2), 
	FALADOR_GARDEN(new RSTile(3007, 3387), new RSTile(3012, 3375), 0), 
	FALADOR_GARDEN_SHED(new RSTile(3027, 3380), new RSTile(3028, 3378), 0),
	FALADOR_CHICKENS(new RSTile(3015, 3287), new RSTile(3019, 3293), 0),
	FALDOR_YEWS(new RSTile(3044, 3321), new RSTile(3001, 3315), 0),
	FALDOR_RANGE(new RSTile(2991, 3367), new RSTile(2988, 3365), 0),
	FALDOR_WATER_SPICKET(new RSTile(2951, 3380, 0), new RSTile(2949, 3383, 0)),
	
	MOTHERLOAD_MINE(new RSTile(3714, 5692,0), new RSTile(3773, 5635,0)),
	MOTHERLOAD_MINE_WATER(new RSTile(3749, 5672), new RSTile(3751, 5660), 0),
	MOTHERLOAD_MINE_BANK(new RSTile(3757, 5661), new RSTile(3760, 5670), 0),
	MOTHERLOAD_MINE_ORE_AREA_1(new RSTile(3732, 5690), new RSTile(3763, 5687), 0),
	MOTHERLOAD_MINE_ORE_AREA_2(new RSTile(3726, 5654), new RSTile(3721, 5689), 0),
	MOTHERLOAD_MINE_ORE_AREA_3(new RSTile(3769, 5638), new RSTile(3718, 5643), 0),
	MOTHERLOAD_MINE_ORE_AREA_4(new RSTile(3769, 5641), new RSTile(3765, 5688), 0),
	MOTHERLOAD_MINE_STRUT(new RSTile(3741, 5672, 0), new RSTile(3741, 5660, 0)),
	MOTHERLOAD_MINE_HOPPER(new RSTile(3749, 5673, 0), new RSTile(3751, 5671, 0)),
	MOTHERLOAD_MINE_SACK(new RSTile(3749, 5660, 0), new RSTile(3751, 5658, 0)),
	
	THURGOS_HOUSE(new RSTile(3001, 3145), new RSTile(2999, 3143), 0),

	BARBARIAN_VILLAGE(new RSTile(3072, 3426), new RSTile(3089, 3412), 0), 
	BARBARIAN_VILLAGE_SMELT(new RSTile(3083, 3411), new RSTile(3086, 3407), 0), 
	BARBARIAN_VILLAGE_SHOP_HELMET(new RSTile(3074, 3431), new RSTile(3077, 3427), 0), 
	BARBARIAN_VILLAGE_FISH(new RSTile(3100, 3434), new RSTile(3103, 3424), 0), 

	RIMMINGTON(new RSTile(2953, 3217), new RSTile(2962, 3207), 0), 
	RIMMINGTON_WITCH(new RSTile(2966, 3207), new RSTile(2969, 3204), 0), 
	RIMMINGTON_RATS(new RSTile(2953, 3205), new RSTile(2960, 3202), 0), 
	RIMMINGTON_CHEMIST(new RSTile(2930, 3212), new RSTile(2935, 3208), 0), 
	RIMMINGTON_FARM(new RSTile(2945, 3260), new RSTile(2955, 3247), 0), 
	RIMMINGTON_SHOP_GENERAL(new RSTile(2947, 3217), new RSTile(2949, 3212), 0),
	RIMMINGTON_MINE(new RSTile(2970, 3248), new RSTile(2987, 3232), 0),

	DWARVEN_MINE(new RSTile(3011, 3451), new RSTile(3019, 3446), 0),
	DWARVEN_MINE_NORTH(new RSTile(3033, 9830), new RSTile(3030, 9821), 0),
	DWARVEN_MINE_SOUTH(new RSTile(3035, 9782), new RSTile(3056, 9762), 0),

	BLACK_KNIGHT_FORTRESS(new RSTile(3024, 3503), new RSTile(3030, 3498), 0), 

	EDGEVILLE(new RSTile(3080, 3468), new RSTile(3110, 3521), 0),  
	EDGEVILLE_BANK(new RSTile(3093, 3497), new RSTile(3096, 3496), 0),
	EDGEVILLE_WILDERNESS(new RSTile(3091, 3498), new RSTile(3098, 3488), 0), 
	EDGEVILLE_JAIL(new RSTile(3107, 3517), new RSTile(3111, 3511), 0), 
	EDGEVILLE_MAN_HOUSE(new RSTile(3092, 3512), new RSTile(3099, 3508), 0), 
	EDGEVILLE_FURNACE(new RSTile(3105, 3501), new RSTile(3110, 3497), 0), 
	EDGEVILLE_HILL_GIANT_PIT(new RSTile(3098, 9828), new RSTile(3119, 9844), 0),
	EDGEVILLE_KEY_AREA(new RSTile(3124, 9861), new RSTile(3132, 9863), 0),
	EDGEVILLE_CHAOS_DRUIDS(new RSTile(3108, 9934), new RSTile(3114, 9930), 0),
	EDGEVILLE_RANGE(new RSTile(3077, 3492), new RSTile(3081, 3496), 0),
	
	GRAND_EXCHANGE(new RSTile(3161, 3487), new RSTile(3168, 3483), 0),

	TUTORIAL_ISLAND_START( new RSTile(3090, 3100), new RSTile(3098, 3112), 0 ),
	TUTORIAL_ISLAND_SURVIVAL( new RSTile(3097, 3093), new RSTile(3106, 3100), 0 ),
	TUTORIAL_ISLAND_COOK( new RSTile(3075, 3083), new RSTile(3077, 3085), 0 ),
	TUTORIAL_ISLAND_QUEST( new RSTile(3084, 3120), new RSTile(3087, 3124), 0 ),
	TUTORIAL_ISLAND_MINE( new RSTile(3078, 9499), new RSTile(3084, 9506), 0 ),
	TUTORIAL_ISLAND_COMBAT( new RSTile(3109, 9509), new RSTile(3111, 9512), 0 ),
	TUTORIAL_ISLAND_RAT_CAGE( new RSTile(3102, 9514), new RSTile(3108, 9521), 0 ),
	TUTORIAL_ISLAND_BANK( new RSTile(3120, 3120), new RSTile(3123, 3123), 0 ),
	TUTORIAL_ISLAND_CHURCH( new RSTile(3122, 3104), new RSTile(3124, 3109), 0),
	TUTORIAL_ISLAND_MAGIC( new RSTile(3140, 3088), new RSTile(3142, 3090), 0),
	
	TEMPLE_PASS(new RSTile(3405, 3490), new RSTile(3408, 3487), 0),
	TEMPLE_TRAPDOOR(new RSTile(3407, 3504, 0), new RSTile(3404, 3507, 0)),
	TEMPLE_DUNGEON(new RSTile(3407, 9906, 0), new RSTile(3403, 9907, 0)),
	TEMPLE_DUNGEON_WELL(new RSTile(0, 0), new RSTile(0, 0), 0),
	TEMPLE_DUNGEON_DREZELS_ROOM(new RSTile(0, 0), new RSTile(0, 0), 0),
	TEMPLE_ALTAR(new RSTile(0, 0), new RSTile(0, 0), 0),
	TEMPLE_ALTAR_2ND_FLOOR(new RSTile(0, 0), new RSTile(0, 0), 0),
	TEMPLE_ALTAR_3RD_FLOOR(new RSTile(0, 0), new RSTile(0, 0), 0),
	
	KARAMJA_DOCK(new RSTile(2924, 3180), new RSTile(2925, 3175), 0), 
	
	CATHERBY_BANK(new RSTile(2806, 3441), new RSTile(2812, 3438), 0),
	CATHERBY_FARMING_AREA(new RSTile(2814, 3459), new RSTile(2805, 3468), 0),
	CATHERBY_FISHING_SHOP(new RSTile(2837, 3440), new RSTile(2830, 3446), 0),
	CATHERBY_FISHING_AREA(new RSTile(2835, 3432), new RSTile(2864, 3428), 0),
	CATHERBY_RANGE(new RSTile(2818, 3439), new RSTile(2815, 3444), 0),
	
	SEERS_VILLAGE(new RSTile(2712, 3489), new RSTile(2739, 3483), 0),
	SEERS_VILLAGE_MAPLE_TREES(new RSTile(2734, 3498), new RSTile(2719, 3504), 0),
	
	FISHING_GUILD_SOUTH_DOCK(new RSTile(2611, 3411), new RSTile(2602, 3416), 0),
	FISHING_GUILD_NORTH_DOCK(new RSTile(2604, 3420), new RSTile(2595, 3425), 0),
	FISHING_GUILD_MINNOW_DOCK(new RSTile(2622, 3441), new RSTile(2607, 3446), 0),
	
	OTTOS_FISHING_GROTTO(new RSTile(2505, 3493), new RSTile(2499, 3518), 0),
	
	GRAND_TREE_AGILITY_ARENA(new RSTile(2477, 3436, 0), new RSTile(2472, 3440, 0)),
	
	TZ_HARR_CITY_FURNACE(new RSTile(2445, 5152, 0), new RSTile(2449, 5150, 0)),
	;

	public static int BASEMENT_OFFSET = 6400;
	private static ArrayList<Locations> locs = new ArrayList<Locations>();
	static {
		for (Locations loc : values()) {
			locs.add(loc);
		}
	}

	private Rectangle location;
	private int floor;
	private Locations( RSTile northWest, RSTile southEast, int floor ) {
		int x = Math.min( northWest.getX(), southEast.getX() );
		int y = Math.min( northWest.getY(), southEast.getY() );
		int w = Math.abs( northWest.getX() - southEast.getX() );
		int h = Math.abs( northWest.getY() - southEast.getY() );
		this.location = new Rectangle( x, y, w, h );
		this.floor = floor;
	}
	
	private Locations( RSTile corner1, RSTile corner2 ) {
		this(corner1, corner2, corner1.getPlane());
	}

	/**
	 * Returns the center-point of this location.
	 * @return
	 */
	public RSTile getCenter() {
		return new RSTile(this.location.x + this.location.width / 2, this.location.y + this.location.height / 2, floor);
	}
	
	/**
	 * Returns the area covered by this location.
	 * @return
	 */
	public RSArea getArea() {
		return new RSArea(
				new RSTile(this.location.x, this.location.y, floor),
				new RSTile(this.location.x + this.location.width, this.location.y + this.location.height, floor)
		);
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
	 * Returns whether or not a specific tile is located within this location.
	 * @param tile
	 * @return
	 */
	public boolean contains(Positionable tile) {
		return this.location.contains(tile.getPosition().getX(), tile.getPosition().getY()) && tile.getPosition().getPlane() == this.floor;
	}

	/**
	 * Returns the name of this enum with Title Casing.
	 * <br>
	 * Additionally the _ characters are replaced with a blank space.
	 * @return
	 */
	public String getName() {
		return NameFormatter.formatName(toString());
	}

	/**
	 * Returns the height of this tile.
	 * <br>
	 * 0 = Ground Level.
	 * <br>
	 * 1 = First Floor.
	 * <br>
	 * 2 = Second Floor.
	 * <br>
	 * <br>
	 * Additionally If the location is on the ground level, you should check if it is underground.
	 * <br>
	 * Underground locations and ground floor locations share the same height.
	 * @param loc
	 * @return
	 */
	public int getFloor() {
		return this.floor;
	}

	/**
	 * Returns a rectangle that represents the bounds of this location.
	 * @return
	 */
	public Rectangle getBounds() {
		return this.location;
	}

	/**
	 * Returns the cooresponding location with this RSTile.
	 * @param position
	 * @return
	 */
	public static Locations get(RSTile position) {
		for (int i = 0; i < locs.size(); i++) {
			if ( locs.get(i).contains(position) ) {
				return locs.get(i);
			}
		}
		return null;
	}

	/**
	 * Returns whether or not a location is underground.
	 * @param loc
	 * @return
	 */
	public static boolean isUnderGround( Locations loc ) {
		return isUnderGround( loc.getCenter() );
	}

	/**
	 * Returns whether or not this tile is "underground".
	 * @param tile
	 * @return
	 */
	public static boolean isUnderGround( RSTile tile ) {
		return tile.getPlane() == 0 && tile.getY() > BASEMENT_OFFSET;
	}

	/**
	 * Returns the basement location for this location.
	 * @param loc
	 * @return
	 */
	public static Locations getBasementLocation( Locations loc ) {
		RSTile c = loc.getCenter();
		RSTile newLoc = new RSTile( c.getX(), c.getY() + BASEMENT_OFFSET, 0 );

		// Find the non-basement location
		for (int i = 0; i < locs.size(); i++) {
			if ( locs.get(i).contains(newLoc) ) {
				return locs.get(i);
			}
		}

		return null;
	}

	/**
	 * Returns the ground floor location for this location.
	 * @param loc
	 * @return
	 */
	public static Locations getGroundFloorLocation( Locations loc ) {
		RSTile c = loc.getCenter();
		RSTile newLoc = new RSTile( c.getX(), c.getY() - BASEMENT_OFFSET, 0 );

		// Find the non-basement location
		for (int i = 0; i < locs.size(); i++) {
			if ( locs.get(i).contains(newLoc) ) {
				return locs.get(i);
			}
		}

		return null;
	}

	/**
	 * Returns the closest location with the matching word in its name.
	 * @param match
	 * @param from
	 * @return
	 */
	public static Locations closestLocation(String match, Locations from) {
		return closestLocation( match, from.getCenter() );
	}

	/**
	 * Returns the closest location.
	 * @param match
	 * @param from
	 * @return
	 */
	public static Locations closestLocation( String match, RSTile tile ) {
		Locations ret = null;
		int dist = 99999;
		for (int i = 0; i < locs.size(); i++) {
			Locations loc = locs.get(i);
			if ( match == null || loc.toString().toLowerCase().contains(match.toLowerCase())) {
				if ( loc.getFloor() != tile.getPlane() )
					continue;

				int d = loc.getCenter().distanceTo(tile);
				if ( d < dist ) {
					dist = d;
					ret = loc;
				}
			}
		}

		return ret;
	}

	/**
	 * Returns if the player is near the desired location. Being near means you are less than 8 tiles away, and on the same plane.
	 * @param desiredLocation
	 * @return
	 */
	public static boolean isNear(RSTile desiredLocation) {
		double dist = Player.getPosition().distanceToDouble(desiredLocation);
		if ( dist > 8 )
			return false;

		if ( desiredLocation.getPlane() != Player.getPosition().getPlane() )
			return false;

		return true;
	}
	
	private static double dist(double p, double lower, double upper) {
		if ( p < lower )
			return lower-p;
		if ( p > upper )
			return p-upper;
		return Math.min(p-lower,upper-p);
	}

	/**
	 * Returns if the player is near the desired location. Being near means you are less than 10 tiles away FROM THE NEAREST SIDE, and on the same plane.
	 * @param draynorManorFarm
	 * @return
	 */
	public static boolean isNear(Locations loc) {
		if ( loc == null )
			return false;
		
		if ( loc.contains(Player.getPosition()) )
			return true;

		// Get distance to nearest SIDE
		double px = Player.getPosition().getX();
		double py = Player.getPosition().getY();
		Rectangle box = loc.getBounds();
		double dx = dist( px, box.x, box.x+box.width );
		double dy = dist( py, box.y, box.y+box.height );
		double d = Math.sqrt(dx*dx+dy*dy);
		
		// Get min dist
		double wid = loc.getBounds().getWidth();
		double hei = loc.getBounds().getHeight();
		double area = Math.sqrt(wid*wid+hei*hei);
		double minDist = area * 0.8;
		minDist = Math.min(minDist, 24);
		minDist = Math.max(minDist, 8);
		
		// If we're more than 10 tiles away from the nearest side, not near it.
		if ( d > minDist )
			return false;
		
		// plane check
		if ( loc.getFloor() != Player.getPosition().getPlane() )
			return false;
		
		return true;
	}
}
