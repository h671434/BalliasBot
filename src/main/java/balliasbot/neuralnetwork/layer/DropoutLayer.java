package balliasbot.neuralnetwork.layer;

import java.util.Random;

import balliasbot.math.Vector;

public class DropoutLayer extends NeuralLayer {

	private static final Random RANDOM = new Random();
	
	private double rate;
	private boolean enabled;
	
	public DropoutLayer(int numberOfInputs, double rate) {
		super(numberOfInputs, numberOfInputs);
		this.rate = rate;
		this.enabled = true;
	}
	
	public DropoutLayer(int numberOfInputs) {
		this(numberOfInputs, 0.07);
	}

	@Override
	public Vector compute(Vector input) {
		if(!enabled) {
			return input;
		}
		
		double[] data = input.asArray();
		
		for(int i = 0; i < data.length; i++) {
			if(RANDOM.nextDouble(0, 1) < rate) {
				data[i] = 0;
			} else {
				data[i] *= 1 / (1 - rate);
			}
		}
		
		return new Vector(data);
	}

	@Override
	public Vector backpropagate(Vector error, Vector input, Vector output, double learningRate) {
		return error;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
