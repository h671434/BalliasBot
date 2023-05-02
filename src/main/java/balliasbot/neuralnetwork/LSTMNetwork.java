package balliasbot.neuralnetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import balliasbot.math.ActivationFunction;
import balliasbot.math.Vector;
import balliasbot.neuralnetwork.layer.DenseLayer;
import balliasbot.neuralnetwork.layer.DropoutLayer;
import balliasbot.neuralnetwork.layer.LSTMLayer;
import balliasbot.neuralnetwork.layer.NormalizationLayer;
import balliasbot.neuralnetwork.layer.OutputLayer;
import balliasbot.neuralnetwork.weights.WeightInitializer;

public class LSTMNetwork extends NeuralNetwork {
	
	public LSTMNetwork(int numberOfInputs, int numberOfLayers,
			int weightsPerLayer, int numberOfOutputs) {
		layers.add(new NormalizationLayer(-1, 1));
		layers.add(new LSTMLayer(numberOfInputs, weightsPerLayer));
		layers.add(new DropoutLayer(weightsPerLayer, 0.04));
		
		for(int i = 1; i < numberOfLayers; i++) {
			layers.add(new LSTMLayer(weightsPerLayer, weightsPerLayer));
			layers.add(new DropoutLayer(weightsPerLayer, 0.04));
		}
		
		layers.add(new OutputLayer(weightsPerLayer, numberOfOutputs, ActivationFunction.TANH));
	}

	
}
