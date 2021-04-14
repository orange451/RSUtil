package scripts.util.task;

import org.tribot.api.General;
import org.tribot.api2007.Login;
import org.tribot.api2007.Login.LOGIN_MESSAGE;
import org.tribot.api2007.Login.STATE;
import org.tribot.api2007.WorldHopper;

import scripts.util.AccountCredentials;

public abstract class TaskLogin extends BotTask {

	private String username;
	private String password;
	
	private boolean cannotLogIn;
	
	private int world;
	
	public TaskLogin() {
		this.setWorld(-1);
	}
	
	public TaskLogin(String username, String password, int world) {
		this.setCredentials(username, password);
		this.setWorld(world);
	}
	
	public TaskLogin(String username, String password) {
		this(username, password, -1);
	}
	
	public TaskLogin(AccountCredentials credentials) {
		this.setCredentials(credentials.getUsername(), credentials.getPassword());
	}
	
	public TaskLogin(AccountCredentials credentials, int world) {
		this(credentials.getUsername(), credentials.getPassword(), world);
	}
	
	public void setCredentials(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public void setWorld(int world) {
		this.world = world;
	}
	
	public boolean isBanned() {
		return this.cannotLogIn;
	}
	
	@Override
	public void init() {
		//
	}

	@Override
	public String getTaskName() {
		return "Logging in";
	}
	
	@Override
	public boolean isTaskComplete() {
		if ( Login.getLoginState() == STATE.WELCOMESCREEN )
			return false;
		
		// Logout if we're logged in
		if ( Login.getLoginState() == STATE.INGAME ) {
			Login.logout();
			General.sleep(1000);
			return false;
		}
		
		// Swap worlds
		if ( world >= 300 )
			WorldHopper.changeWorld(world);
		
		// Log in
		boolean loggedIn;
		if ( username != null && password != null )
			loggedIn = Login.login(username, password);
		else
			loggedIn = Login.login();
		
		// oof
		cannotLogIn = Login.getLoginMessage() == LOGIN_MESSAGE.BANNED || Login.getLoginMessage() == LOGIN_MESSAGE.LOCKED;
		if ( !loggedIn )
			return false;
		
		return true;
	}

}
