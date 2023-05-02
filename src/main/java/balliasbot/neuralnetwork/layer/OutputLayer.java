package balliasbot.neuralnetwork.layer;

import balliasbot.math.ActivationFunction;
import balliasbot.math.Matrix;
import balliasbot.math.Vector;
import balliasbot.neuralnetwork.weights.WeightInitializer;

public class OutputLayer extends DenseLayer {
	
	public OutputLayer(Vector biases, Matrix weights, ActivationFunction activation) {
		super(biases, weights, activation);
	}
	
	public OutputLayer(int numberOfInputs, int numberOfOutputs, ActivationFunction activation) {
		super(numberOfInputs, numberOfOutputs, activation);
	}
	
	/**
	 * Takes in the total error, so it does not need to calculate error itself.
	 */
	@Override
	public Vector backpropagate(Vector error, Vector input, Vector output, double learningRate) {
		double[][] newWeightData = new double[weights.rows][weights.columns];
		for(int i = 0; i < weights.rows; i++) {
			for(int j = 0; j < weights.columns; j++) {
				newWeightData[i][j] = weights.get(i, j) - (learningRate * error.get(i) * input.get(j));
			}
		}
		
		weights = new Matrix(newWeightData);
		
		return error;
	}	

}
