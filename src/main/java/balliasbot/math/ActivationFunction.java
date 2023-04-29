package balliasbot.math;

@FunctionalInterface
public interface ActivationFunction {

	public static final ActivationFunction SIGMOID = x -> {
		return 1 / (1 + Math.exp(-x));
	};
	
	public static final ActivationFunction TANH = x -> {
		return (Math.exp(x) - Math.exp(-x)) / (Math.exp(x) + Math.exp(-x));
	};
	
	public static final ActivationFunction BINARY = x -> {
		return x > 0 ? 1 : 0;
	};
	
	double calculate(double x);
	
	
}
