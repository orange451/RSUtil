package scripts.util.misc;

public class BreakHelper {
	private static long BREAK_TIME_CURRENT = 0;
	private static long BREAK_TIME_TOTAL = 0;
	private static long BREAK_START_TIME = -1;
	
	public static void updateOnBreak() {
		if (BREAK_START_TIME == -1)
			BREAK_START_TIME = System.currentTimeMillis();
		
		BREAK_TIME_CURRENT = System.currentTimeMillis() - BREAK_START_TIME;
	}

	public static void cancelBreak() {
		BREAK_TIME_TOTAL += BREAK_TIME_CURRENT;
		BREAK_TIME_CURRENT = 0;
		BREAK_START_TIME = -1;
	}
	
	public static long getTotalBreakTime() {
		return BREAK_TIME_TOTAL;
	}
	
	public static long getCurrentBreakTime() {
		return BREAK_TIME_CURRENT;
	}
}
