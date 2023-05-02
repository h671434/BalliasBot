package balliasbot.neuralnetwork.layer;

import balliasbot.math.Vector;

public class NormalizationLayer extends NeuralLayer {

	private double lowerRange;
	private double upperRange;
	
	public NormalizationLayer(int numberOfInputs, double lowerRange, double upperRange) {
		super(numberOfInputs, numberOfInputs);
		this.lowerRange = lowerRange;
		this.upperRange = upperRange;
	}
	
	public NormalizationLayer(int numberOfInputs, double upperRange) {
		this(numberOfInputs, -1, upperRange);
	}
	
	public NormalizationLayer(int numberOfInputs) {
		this(numberOfInputs, -1, 1);
	}
	
	@Override
	public Vector compute(Vector input) {
		return minMaxNormalize(input);
	}

	private Vector minMaxNormalize(Vector input) {
		double xmin = input.get(0);
		double xmax = input.get(0);
		for(double x : input) {
			if(x < xmin) {
				xmin = x;
			} 
			if(x > xmax) {
				xmax = x;
			}
		}
		
		double[] normalizedWithinRange = new double[input.size()];	
		for(int i = 0; i < normalizedWithinRange.length; i++) {
			double xnorm = (input.get(i) - xmin) / (xmax - xmin);
			normalizedWithinRange[i] = (xnorm * (upperRange - lowerRange)) + lowerRange;
		}
		
		return new Vector(normalizedWithinRange);
	}

	@Override
	public Vector backpropagate(Vector error, Vector output, double learningRate) {
		// TODO Auto-generated method stub
		return error;
	}
	
}
