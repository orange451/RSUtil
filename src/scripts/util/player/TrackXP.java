package scripts.util.player;

import org.tribot.api2007.Skills;

public class TrackXP {
	private Skills.SKILLS skill;
	private int startXP;
	private int currentXP;
	private int hourlyXP;
	private long startTime;

	protected TrackXP(Skills.SKILLS skill) {
		this.skill = skill;
		this.startXP = org.tribot.api2007.Skills.getXP(skill);
		this.currentXP = this.startXP;
		this.hourlyXP = 0;
		this.startTime = System.currentTimeMillis();
	}

	protected void update() {
		this.currentXP = org.tribot.api2007.Skills.getXP(this.skill);

		float elapsedTime = (float)(System.currentTimeMillis() - this.startTime) / 1000.0F;
		this.hourlyXP = ((int)(getGainedXP() / elapsedTime * 3600.0F));
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
