package balliasbot.neuralnetwork.layer;

import org.json.simple.JSONObject;

import balliasbot.math.ActivationFunction;
import balliasbot.math.Matrix;
import balliasbot.math.Vector;
import balliasbot.neuralnetwork.data.WeightInitializer;

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
	
	@Override
	public Vector backpropagate(Vector error, Vector input, Vector output, double learningRate) {
		Vector derivativeOutput = activation.derivative(output);
		
		error = weights.transform().dot(error.multiplyPointwise(derivativeOutput));
		
		double[][] newWeightData = new double[weights.rows][weights.columns];
		for(int i = 0; i < weights.rows; i++) {
			for(int j = 0; j < weights.columns; j++) {
				newWeightData[i][j] = weights.get(i, j) - (learningRate * error.get(j) * input.get(j));
			}
		}
		
		weights = new Matrix(newWeightData);
		
		return error;
	}
	
}
