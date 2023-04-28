package balliasbot.neuralnetwork.layer;

import balliasbot.math.ActivationFunction;
import balliasbot.math.Matrix;
import balliasbot.math.Vector;

public abstract class NeuralLayer {

	protected double bias;
	protected Vector weights;
	private ActivationFunction activation;
	
	public NeuralLayer(Vector weights, ActivationFunction activation) {
		this.bias = 1;
		this.weights = weights;
		this.activation = activation;
	}
	
	public Vector compute(Vector input) {
		Matrix preActivation = input.dot(weights).plus(bias);
		
		return preActivation.applyFunction(activation);
	}
	
}
