package balliasbot.math;

@FunctionalInterface
public interface ActivationFunction {

	double calculate(double x);
	
	public class Sigmoid implements ActivationFunction {

		@Override
		public double calculate(double x) {
			return 1 / (1 + Math.exp(-x));
		}
		
	}
	
	public class HyperbolicTangent implements ActivationFunction {
		
		@Override
		public double calculate(double x) {
			return (Math.exp(x) - Math.exp(-x)) / (Math.exp(x) + Math.exp(-x));
		}
		
	}
	
	public class BinaryStep implements ActivationFunction {

		double threshold;
		
		public BinaryStep() {
			this.threshold = 0;
		}
		
		public BinaryStep(double threshold) {
			this.threshold = threshold;
		}
		
		@Override
		public double calculate(double x) {
			return x > threshold ? 1 : 0;
		}
		
	}
	
}
