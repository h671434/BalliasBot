package balliasbot.neuralnetwork;

import java.util.Random;

public class Neuron {

	protected static final Random RANDOM = new Random();
	
	protected Double bias = RANDOM.nextDouble(-1, 1);
	public Double weight1 = RANDOM.nextDouble(-1, 1);
	protected Double weight2 = RANDOM.nextDouble(-1, 1);
	
	protected Double oldBias = bias;
	public Double oldWeight1 = weight1;
	protected Double oldWeight2 = weight2;
	
	public double compute(double input1, double input2) {
		double preActivation = (weight1 * input1) + (weight2 * input2);
		
		return sigmoid(preActivation);
	}
	
	protected static double sigmoid(double in) {
		return 1 / (1 + Math.exp(-in));
	}
	
	public void mutate() {
		int propertyToChange = RANDOM.nextInt(0, 3);
		Double changeFactor = RANDOM.nextDouble(-1, 1);
		
		if(propertyToChange == 0) {
			bias += changeFactor;
		} else if(propertyToChange == 1) {
			weight1 += changeFactor;
		} else {
			weight2 += changeFactor;
		}
	}
	
	public void forget() {
		bias = oldBias;
		weight1 = oldWeight1;
		weight2 = oldWeight2;
	}
	
	public void remember() {
		oldBias = bias;
		oldWeight1 = weight1;
		oldWeight2 = weight2;
	}
	
}
