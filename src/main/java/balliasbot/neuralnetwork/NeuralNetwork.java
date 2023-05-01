package balliasbot.neuralnetwork;

import java.util.ArrayList;
import java.util.List;

import balliasbot.math.ActivationFunction;
import balliasbot.math.CostFunction;
import balliasbot.math.Matrix;
import balliasbot.math.Vector;
import balliasbot.neuralnetwork.layer.NeuralLayer;

public abstract class NeuralNetwork {

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
		List<Vector> outputs = new ArrayList<>();
		
		for(int i = 0; i < epochs; i++) {
			for(int j = 0; j < targets.size(); j++) {
				outputs.add(predict(inputs.get(j)));
				
				double error = CostFunction.meanSquaredError(outputs.get(i), targets.get(j));
				// TODO
			}
		}
		
		return outputs;
	}
	

	
}
