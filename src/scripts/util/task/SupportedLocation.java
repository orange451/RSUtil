package scripts.util.task;

import scripts.util.names.Locations;

public interface SupportedLocation {
	public String getName();
	public Locations getLocation();
	public boolean isMembers();
}
