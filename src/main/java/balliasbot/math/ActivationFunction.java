package balliasbot.math;

import java.util.function.Function;

public interface ActivationFunction {

	public static final ActivationFunction SIGMOID = new ActivationFunction() {
		@Override
		public Vector calculate(Vector input) {
			return input.applyFunction(x -> 1 / (1 + Math.exp(-x)));
		}

		@Override
		public Vector derivative(Vector input) {
			return input.applyFunction(x -> x * (1 - x));
		}
	};
	
	public static final ActivationFunction TANH = new ActivationFunction() {
		@Override
		public Vector calculate(Vector input) {
			return input.applyFunction(x -> Math.tanh(x));
		}

		@Override
		public Vector derivative(Vector input) {
			return input.applyFunction(x -> {
				double tanhOutput = Math.tanh(x);
				
				return 1 - (tanhOutput * tanhOutput);
			});
		}
	};
	
	public static final ActivationFunction BINARY = new ActivationFunction() {
		@Override
		public Vector calculate(Vector input) {
			return input.applyFunction(x -> (double)(x > 0 ? 1 : 0));
		}

		@Override
		public Vector derivative(Vector input) {
			return calculate(input);
		}
	};
	
	public static final ActivationFunction RELU = new ActivationFunction() {
		@Override
		public Vector calculate(Vector input) {
			return input.applyFunction(x -> (double)(x > 0 ? x : 0));
		}

		@Override
		public Vector derivative(Vector input) {
			return calculate(input);
		}
	};
	
	public static final ActivationFunction SOFTMAX = new ActivationFunction() {
		@Override
		public Vector calculate(Vector input) {
			double sum = 0;
			for(int i = 0; i < input.size(); i++) {
				sum += Math.exp(input.get(i));
			}
			
			final double totalSum = sum; // final to allow usage with applyFunction
			
			return input.applyFunction(x -> x / totalSum);
		}

		@Override
		public Vector derivative(Vector input) {
			return input; // TODO
		}
	};
	
	Vector calculate(Vector input);
	
	Vector derivative(Vector input);
	
}
