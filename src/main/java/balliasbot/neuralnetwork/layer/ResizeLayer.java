package balliasbot.neuralnetwork.layer;

import balliasbot.math.ActivationFunction;
import balliasbot.math.Matrix;
import balliasbot.math.Vector;
import balliasbot.neuralnetwork.weights.WeightInitializer;

public class ResizeLayer extends NeuralLayer {
	
	private int desiredVectorSize;
	private Matrix weights;
	private ActivationFunction activation;
	
	public ResizeLayer(int numberOfInputs, int desiredVectorSize) {
		super(numberOfInputs, desiredVectorSize);
		this.desiredVectorSize = desiredVectorSize;
		this.weights = WeightInitializer.generateNewWeights(numberOfInputs, desiredVectorSize);
		this.activation = ActivationFunction.TANH;
	}

	@Override
	public Vector compute(Vector input) {
		double[] data = new double[desiredVectorSize];
		for(int i = 0; i < desiredVectorSize; i++) {
			data[i] = input.dot(weights.getRowVector(i));
		}

		return activation.calculate(new Vector(data));
	}
	
//	/**
//	 * Converts the vector into a matrix where the amount of rows is equal to 
//	 * the desired size of the output vector. Vector is "wrapped" and repeated 
//	 * through each column. Each element in the output vector is found from 
//	 * the dot product a row vector from the matrix and its corresponing weights.
//	 */
//	@Override
//	public Vector compute(Vector input) {
//		double[][] matrix = new double[desiredVectorSize][input.size()];
//		for(int i = 0; i < matrix.length; i++) {
//			for(int j = 0; j < matrix[0].length; j++) {
//				int index = (i + j) % input.size();
//				
//				matrix[i][j] = input.get(index);
//			}
//		}
//		
//		double[] vectorData = new double[desiredVectorSize];
//		for(int i = 0; i < desiredVectorSize; i++) {
//			double preActive = new Vector(matrix[i]).dot(weights.getRowVector(i));
//			vectorData[i] = activation.calculate(preActive);
//		}
//		
//		return new Vector(vectorData).applyFunction(activation);
//	}

}
