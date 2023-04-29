package balliasbot.neuralnetwork.weights;

import java.util.Random;

import balliasbot.math.Matrix;

public class WeightInitializer {
	
	public static final Matrix generateNewWeights(int rows, int columns) {
		Random random = new Random();
		
		double[][] data = new double[rows][columns];
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < data[0].length; j++) {
				data[i][j] = random.nextDouble(-1, 1);
			}
		}
		
		return new Matrix(data);
	}
	
}
