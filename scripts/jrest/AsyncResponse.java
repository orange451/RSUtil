package scripts.jrest;

@FunctionalInterface
public interface AsyncResponse<Q> {
	public void response(ResponseEntity<Q> response);
}
