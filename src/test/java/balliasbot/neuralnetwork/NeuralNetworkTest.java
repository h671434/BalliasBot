package balliasbot.neuralnetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import balliasbot.math.ActivationFunction;
import balliasbot.math.Vector;
import balliasbot.neuralnetwork.layer.DenseLayer;
import balliasbot.neuralnetwork.layer.NeuralLayer;
import balliasbot.neuralnetwork.layer.NormalizationLayer;

public class NeuralNetworkTest {

	private static final int NUMBER_OF_INPUTS = 6;
	private static final int NUMBER_OF_OUTPUTS = 4;
	private static final int NUMBER_OF_LAYERS = 4;
	private static final int NUMBER_OF_NEURONS = 128;
	
	private static final int DATASET_SIZE = 50;
	
	private static final int EPOCHS = 500;
	private static final double LEARNING_RATE = 0.007;
	
	public static void main(String[] args) {
		int neuronsPerLayer = NUMBER_OF_NEURONS / NUMBER_OF_LAYERS;
		
		List<NeuralLayer> layers = new ArrayList<>(NUMBER_OF_LAYERS);
		layers.add(new NormalizationLayer(0, 1));
		layers.add(new DenseLayer(NUMBER_OF_INPUTS, neuronsPerLayer, ActivationFunction.SIGMOID));
		for(int i = 2; i < NUMBER_OF_LAYERS; i++) {
			layers.add(new DenseLayer(neuronsPerLayer, neuronsPerLayer, ActivationFunction.SIGMOID));
		}
		layers.add(new DenseLayer(neuronsPerLayer, NUMBER_OF_OUTPUTS, ActivationFunction.SIGMOID));
			
		NeuralNetwork network = new NeuralNetwork(layers);
		
		List<Vector> inputs = new ArrayList<>();
		List<Vector> targets =  new ArrayList<>();
		Random random = new Random();
		for(int i = 0; i < DATASET_SIZE; i++) {
			double[] inputData = new double[NUMBER_OF_INPUTS];
			for(int j = 0; j < NUMBER_OF_INPUTS; j++) {
				inputData[j] = (j+1) * 3;
			}
			inputs.add(new Vector(inputData));
			
			double[] targetData = new double[NUMBER_OF_OUTPUTS];
			for(int j = 0; j < NUMBER_OF_OUTPUTS; j++) {
				targetData[j] = 1 / (double)(j+1) * 0.5;
			}
			targets.add(new Vector(targetData));
		}			
				
		List<Vector> outputs = network.train(inputs, targets, EPOCHS, LEARNING_RATE);
		
	}
	
}
