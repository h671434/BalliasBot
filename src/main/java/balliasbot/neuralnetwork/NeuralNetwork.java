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
	
	public List<Vector> train(List<Vector> inputs, List<Vector> targets, 
			int epochs, double learningRate) {
		List<Vector> allOutputs = new ArrayList<>();
		
		for(int i = 0; i < epochs; i++) {
			for(int j = 0; j < targets.size(); j++) {
				List<Vector> inputsForEachLayer = new ArrayList<>();
				List<Vector> outputsForEachLayer = new ArrayList<>();
				
				Vector output = inputs.get(j);
				for(int k = 0; k < layers.size(); k++) {
					inputsForEachLayer.add(output);
					output = layers.get(k).compute(output);
					outputsForEachLayer.add(output);
				}
				allOutputs.add(output);
				
				Vector error = output.minus(targets.get(j));
				double mse = meanSquaredError(error);	
				
				for(int k = layers.size() - 1; k >= 0; k--) {
					error = layers.get(k).backpropagate(error, inputsForEachLayer.get(k), 
							outputsForEachLayer.get(k), learningRate);
				}
								
				if(i % 10 == 0) {
					System.out.println(mse);
				}
			}
		}
		
		return allOutputs;
	}
	
	public static double meanSquaredError(Vector errors) {
		double sum = 0;
		for(int i = 0; i < errors.size(); i++) {
			sum += Math.pow(errors.get(i), 2);
		}
		
		return sum / errors.size();
	}
	
}
