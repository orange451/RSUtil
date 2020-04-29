package scripts.util;

public class Condition extends org.tribot.api.types.generic.Condition {
	@FunctionalInterface
	public interface ConditionLambda {
		boolean active();
	}

	private ConditionLambda lambda;

	public Condition(ConditionLambda lambda) {
		super();
		this.lambda = lambda;
	}

	@Override
	public boolean active() {
		return lambda.active();
	}
}