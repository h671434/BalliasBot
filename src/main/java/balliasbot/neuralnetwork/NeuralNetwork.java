package balliasbot.neuralnetwork;

import java.util.ArrayList;
import java.util.List;

import balliasbot.math.ActivationFunction;
import balliasbot.math.Matrix;
import balliasbot.math.Vector;
import balliasbot.neuralnetwork.layer.NeuralLayer;

public class NeuralNetwork {

	private final List<NeuralLayer> layers;
	
	protected NeuralNetwork(List<NeuralLayer> layers) {
		this.layers = layers;
	}
	
	Vector predict(Vector inputs) {
		Vector output = inputs;
		for(int i = 0; i < layers.size(); i++) {
			output = layers.get(i).compute(output);
		}
		
		return output;
	}
	
	public static final class Builder {
		
		private List<NeuralLayer> layers;
		
		public Builder() {
			this.layers = new ArrayList<>();
		}
		
		public Builder addLayer(NeuralLayer layer) {
			layers.add(layer);
			
			return this;
		}
		
		public NeuralNetwork build() {
			return new NeuralNetwork(layers);
		}
	}
	
}
