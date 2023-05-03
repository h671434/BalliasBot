package balliasbot.neuralnetwork;

import java.util.ArrayList;
import java.util.List;

import balliasbot.math.ActivationFunction;
import balliasbot.math.Matrix;
import balliasbot.math.Vector;
import balliasbot.neuralnetwork.layer.NeuralLayer;

public class NeuralNetwork {

	protected final List<NeuralLayer> layers;

	protected NeuralNetwork() {
		this.layers = new ArrayList<>();
	}
	
	public NeuralNetwork(List<NeuralLayer> layers) {
		this.layers = layers;
	}
	
	public Vector predict(Vector inputs) {
		Vector output = inputs;
		for(int i = 0; i < layers.size(); i++) {
			output = layers.get(i).compute(output);
		}
		
		return output;
	}
	
	private Vector predict(Vector input, List<Vector> layerInputs, List<Vector> layerOutputs) {
		Vector output = null;
		for(int k = 0; k < layers.size(); k++) {
			output = layers.get(k).compute(input);
			
			layerInputs.add(input);
			layerOutputs.add(output);
			
			input = output;
		}
		
		return output;
	}
	
	public List<Vector> train(List<Vector> inputs, List<Vector> targets, 
			int numberOfEpochs, double learningRate) {
		List<Vector> allOutputs = new ArrayList<>();
		
		for(int i = 0; i < numberOfEpochs; i++) {
			for(int j = 0; j < targets.size(); j++) {
				List<Vector> layerInputs = new ArrayList<>();
				List<Vector> layerOutputs = new ArrayList<>();
				
				Vector output = predict(inputs.get(j), layerInputs, layerOutputs);
				
				Vector outputError =  output.minus(targets.get(j));
				double mse = meanSquaredError(outputError);	
				
				backpropogate(outputError, layerInputs, layerOutputs, learningRate);

				if(i % 10 == 0) {
					System.out.format("Epoch: %4d | MSE: %.6f | Error %s%n",
							i, mse, outputError.toStringFormated(5));
				}
			}
		}
		
		return allOutputs;
	}
	
	private void backpropogate(Vector outputError, List<Vector> layerInputs, 
			List<Vector> layerOutputs, double learningRate) {
		Vector error = outputError;
		for(int i = layers.size() - 1; i >= 0; i--) {
			error = layers.get(i).backpropagate(error, layerInputs.get(i), 
					layerOutputs.get(i), learningRate);
		}
	}
	
	private static double meanSquaredError(Vector errors) {
		double sum = 0;
		for(int i = 0; i < errors.size(); i++) {
			sum += Math.pow(errors.get(i), 2);
		}
		
		return sum / errors.size();
	}
	
	private Vector meanSquaredError(List<Vector> errors) {
		int amountOfVectors = errors.size();
		int vectorSize = errors.get(0).size();
		
		double[] meanSquaredErrorOfAllVectors = new double[vectorSize];
		for(int i = 0; i < vectorSize; i++) {
			double[] errorsAtCurrentIndex = new double[amountOfVectors];
			for(int j = 0; j < amountOfVectors; j++) {
				errorsAtCurrentIndex[j] = errors.get(j).get(i);
			}
			
			meanSquaredErrorOfAllVectors[i] = meanSquaredError(new Vector(errorsAtCurrentIndex));
		}
		
		return new Vector(meanSquaredErrorOfAllVectors);
	}
	
}
