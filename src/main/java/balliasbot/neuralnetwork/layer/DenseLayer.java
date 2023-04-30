package balliasbot.neuralnetwork.layer;

import balliasbot.math.ActivationFunction;
import balliasbot.math.Matrix;
import balliasbot.math.Vector;
import balliasbot.neuralnetwork.weights.WeightInitializer;

public class DenseLayer extends NeuralLayer {

	protected Vector biases;
	protected Matrix weights;
	private ActivationFunction activation;
	
	public DenseLayer(Vector biases, Matrix weights, ActivationFunction activation) {
		this.biases = biases;
		this.weights = weights;
		this.activation = activation;
	}
	
	public DenseLayer(int numberOfInputs, int weightsPerInput, ActivationFunction activation) {
		this.biases = WeightInitializer.generateNewBiases(numberOfInputs, false);
		this.weights = WeightInitializer.generateNewWeights(numberOfInputs, weightsPerInput);
		this.activation = activation;
	}
	
	public DenseLayer(ActivationFunction activation) {
		this.activation = activation;
	}
	
	@Override
	public Vector compute(Vector input) {
		
		Vector preActivation = weights.dot(input).plus(biases);
		
		return activate(preActivation);
	}
	
	protected Vector activate(Vector preActivation) {
		return preActivation.applyFunction(activation);
	}


}
