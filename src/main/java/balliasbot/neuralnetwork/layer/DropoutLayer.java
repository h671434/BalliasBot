package balliasbot.neuralnetwork.layer;

import java.util.Random;

import balliasbot.math.Vector;

public class DropoutLayer extends NeuralLayer {

	private final Random random;
	private double rate;
	
	public DropoutLayer(int numberOfInputs, double rate) {
		super(numberOfInputs, numberOfInputs);
		this.random = new Random();
		this.rate = rate;
	}
	
	public DropoutLayer(int numberOfInputs) {
		this(numberOfInputs, 0.07);
	}

	@Override
	public Vector compute(Vector input) {
		double[] data = input.asArray();
		
		for(int i = 0; i < data.length; i++) {
			if(random.nextDouble(0, 1) < rate) {
				data[i] = 0;
			} else {
				data[i] *= 1 / (1 - rate);
			}
		}
		
		return new Vector(data);
	}

	@Override
	public Vector backpropagate(Vector error, Vector output, double learningRate) {
		return error;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}
	
}
