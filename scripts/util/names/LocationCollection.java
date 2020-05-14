package scripts.util.names;

public interface LocationCollection {

	
	/**
	 * Returns whether this bank is for members only.
	 * @return
	 */
	public boolean isMembers();

	/**
	 * Returns the Locations object for this bank.
	 * @return
	 */
	public Locations getLocation();
}
