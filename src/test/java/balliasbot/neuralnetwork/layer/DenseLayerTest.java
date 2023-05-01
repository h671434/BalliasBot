package balliasbot.neuralnetwork.layer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import balliasbot.math.ActivationFunction;
import balliasbot.math.Vector;

public class DenseLayerTest {

	private static final int NUMBER_OF_INPUTS = 6;
	private static final int NUMBER_OF_OUTPUTS = 4;
	private static final int NEURONS_PER_LAYER = 8;
	
	public static void main(String[] args) {
		testMultipleSigmoid(5);
	}
	
	private static void testMultipleSigmoid(int numberOfLayers) {
		Vector input = initializeInputVector();
		
		List<DenseLayer> layers = new ArrayList<>();
		
		for(int i = 0; i < numberOfLayers; i++) {
			if(i == 0) {
				layers.add(new DenseLayer(
						NUMBER_OF_INPUTS, NEURONS_PER_LAYER, ActivationFunction.SIGMOID));
			} else if(i == numberOfLayers - 1) {
				layers.add(new DenseLayer(
						NEURONS_PER_LAYER, NUMBER_OF_OUTPUTS, ActivationFunction.SIGMOID));
			} else {
				layers.add(new DenseLayer(
						NEURONS_PER_LAYER, NEURONS_PER_LAYER, ActivationFunction.SIGMOID));
			}
		}
		
		Vector output = input;
		for(int i = 0; i < layers.size(); i++) {
			output = layers.get(i).compute(output);
		}
		
		System.out.println("input: " + input);
		System.out.println("output: " + output);
	}
	
	private static Vector initializeInputVector() {
		Random random = new Random();
		
		double[] inputData = new double[NUMBER_OF_INPUTS];
		for(int i = 0; i < NUMBER_OF_INPUTS; i++) {
			inputData[i] = random.nextDouble(-1, 1);
		}
		
		return new Vector(inputData);
	}
	
	private static void testEachLayer() {
		Vector input = initializeInputVector();
		
		DenseLayer sigmoidLayer = new DenseLayer(
				NUMBER_OF_INPUTS, NUMBER_OF_OUTPUTS, ActivationFunction.SIGMOID);
		DenseLayer tanhLayer = new DenseLayer(
				NUMBER_OF_INPUTS, NUMBER_OF_OUTPUTS, ActivationFunction.TANH);
		DenseLayer binaryLayer = new DenseLayer(
				NUMBER_OF_INPUTS, NUMBER_OF_OUTPUTS, ActivationFunction.BINARY);
		
		System.out.println("input: " + input);
		System.out.println("sigmoidLayer: " + sigmoidLayer.compute(input));
		System.out.println("tanhLayer: " + tanhLayer.compute(input));
		System.out.println("binaryLayer: " + binaryLayer.compute(input));
	}
	
}
