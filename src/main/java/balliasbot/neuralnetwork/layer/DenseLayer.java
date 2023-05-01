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
		super(weights.rows, weights.columns);
		this.biases = biases;
		this.weights = weights;
		this.activation = activation;
	}
	
	public DenseLayer(int numberOfInputs, int numberOfOutputs, ActivationFunction activation) {
		super(numberOfInputs, numberOfOutputs);
		this.biases = WeightInitializer.generateNewBiases(numberOfOutputs, false);
		this.weights = WeightInitializer.generateNewWeights(numberOfInputs, numberOfOutputs);
		this.activation = activation;
	}
	
	@Override
	public Vector compute(Vector input) {
		Vector preActivation = weights.dot(input).plus(biases);

		return activation.calculate(preActivation);
	}

}
