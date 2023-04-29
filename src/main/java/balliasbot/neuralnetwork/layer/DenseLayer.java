package balliasbot.neuralnetwork.layer;

import balliasbot.math.ActivationFunction;
import balliasbot.math.Matrix;
import balliasbot.math.Vector;

public class DenseLayer extends NeuralLayer {

	protected double bias;
	protected Matrix weights;
	private ActivationFunction activation;
	
	public DenseLayer(Matrix weights, ActivationFunction activation) {
		super(weights.rows);
		this.bias = 0;
		this.weights = weights;
		this.activation = activation;
	}
	
	public Vector compute(Vector input) {
		Vector preActivation = weights.dot(input).plus(bias);
		
		return activate(preActivation);
	}
	
	protected Vector activate(Vector preActivation) {
		return preActivation.applyFunction(activation);
	}
	
}
