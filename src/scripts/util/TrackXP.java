package scripts.util;

import org.tribot.api2007.Skills;
import org.tribot.api2007.Skills.SKILLS;

public 	class TrackXP {
	private SKILLS skill;
	private int startXP;
	private int currentXP;
	private int hourlyXP;
	private long startTime;

	TrackXP( SKILLS skill ) {
		this.skill = skill;
		this.startXP   = Skills.getXP(skill);
		this.currentXP = startXP;
		this.hourlyXP  = 0;
		this.startTime = System.currentTimeMillis();
	}

	public void update() {
		this.currentXP = Skills.getXP(skill);

		float elapsedTime = (System.currentTimeMillis() - startTime) / 1000f; // get elapsed time in Seconds
		this.hourlyXP = (int) ((getGainedXP() / elapsedTime) * 3600);
	}

	public int getStartingXP() {
		return this.startXP;
	}

	public int getCurrentXP() {
		return this.currentXP;
	}

	public int getHourlyXP() {
		return this.hourlyXP;
	}

	public int getGainedXP() {
		return this.currentXP - this.startXP;
	}
}
