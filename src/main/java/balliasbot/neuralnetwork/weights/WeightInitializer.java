package balliasbot.neuralnetwork.weights;

import java.util.Random;

import balliasbot.math.Matrix;
import balliasbot.math.Vector;

public class WeightInitializer {
	
	public Matrix getWeights(String layerLabel) {
		return null;
	}
	
	public Matrix getWeights(String layerLabel, String sublayerLabel) {
		return null;
	}
	
	public Vector getBiases(String layerLabel) {
		return null;
	}
	
	public Vector getBiases(String layerLabel, String sublayerLabel) {
		return null;
	}
	
	public static final Vector generateNewBiases(int size, boolean biasIsActive) {
		double[] data = new double[size];
		for(int i = 0; i < size; i++) {
			data[i] = biasIsActive ? 1 : 0;
		}
		
		return new Vector(data);
	}
	
	public static final Matrix generateNewWeights(int inputs, int weights) {
		Random random = new Random();
		
		double[][] data = new double[weights][inputs];
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < data[0].length; j++) {
				data[i][j] = random.nextDouble(0.3, 0.7);
			}
		}
		
		return new Matrix(data);
	}
	
}
