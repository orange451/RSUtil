package scripts.util.aio;

public class AIOStatus {
	private String status;
	private StatusType type;
	private boolean interrupted;
	
	public AIOStatus() {
		this.type = StatusType.IN_PROGRESS;
	}
	
	protected void setType(StatusType type) {
		this.type = type;
	}
	
	public StatusType getType() {
		return this.type;
	}
	
	protected void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void interrupt() {
		this.setType(StatusType.CANCELLED);
		interrupted = true;
	}
	
	public boolean isInterrupted() {
		return interrupted;
	}
}
