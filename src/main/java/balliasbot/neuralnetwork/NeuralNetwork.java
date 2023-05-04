package balliasbot.neuralnetwork;

import java.util.ArrayList;
import java.util.List;

import balliasbot.math.ActivationFunction;
import balliasbot.math.Matrix;
import balliasbot.math.Vector;
import balliasbot.neuralnetwork.layer.NeuralLayer;

public class NeuralNetwork {

	public final String networkLabel;
	protected final List<NeuralLayer> layers;
	
	public NeuralNetwork(String networkLabel, List<NeuralLayer> layers) {
		this.networkLabel = networkLabel;
		this.layers = layers;
	}
	
	protected NeuralNetwork(String networkLabel) {
		this.networkLabel = networkLabel;
		this.layers = new ArrayList<>();
	}
	
	public Vector predict(Vector inputs) {
		Vector output = inputs;
		for(int i = 0; i < layers.size(); i++) {
			output = layers.get(i).compute(output);
		}
		
		return output;
	}
	
	/**
	 * Adds the inputs and outputs of each layer into lists. Used when training.
	 */
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
	
	public void train(List<Vector> inputs, List<Vector> targets, 
			int numberOfEpochs, double learningRate) {
		for(int i = 0; i < numberOfEpochs; i++) {
			for(int j = 0; j < targets.size(); j++) {
				List<Vector> layerInputs = new ArrayList<>();
				List<Vector> layerOutputs = new ArrayList<>();
				
				Vector output = predict(inputs.get(j), layerInputs, layerOutputs);
				
				Vector outputError =  output.minus(targets.get(j));
				double mse = meanSquaredError(outputError);	
				
				backpropagateAllLayers(outputError, layerInputs, layerOutputs, learningRate);

				if(i % 10 == 0) {
					System.out.format("Epoch: %4d | MSE: %.6f | Error %s%n",
							i, mse, outputError.toStringFormated(5));
				}
			}
		}
	}
	
	private void backpropagateAllLayers(Vector outputError, List<Vector> layerInputs, 
			List<Vector> layerOutputs, double learningRate) {
		Vector error = outputError;
		
		for(int i = layers.size() - 1; i >= 0; i--) {
			NeuralLayer layer = layers.get(i);
			Vector input = layerInputs.get(i);
			Vector output = layerOutputs.get(i);
			
			error = layer.backpropagate(error, input, output, learningRate);
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
		double[] meanSquaredErrors = new double[errors.get(0).size()];
		
		for(int i = 0; i < errors.get(0).size(); i++) {
			meanSquaredErrors[i] = 0;
			
			for(int j = 0; j < errors.size(); j++) {
				meanSquaredErrors[i] += Math.pow(errors.get(j).get(i), 2);
			}
		}
		
		return new Vector(meanSquaredErrors);
	}
	
	public void save() {
		// TODO
	}
	
}
