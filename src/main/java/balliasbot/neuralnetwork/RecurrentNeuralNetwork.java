package balliasbot.neuralnetwork;

import java.util.List;

import balliasbot.math.ActivationFunction;
import balliasbot.math.Matrix;
import balliasbot.math.Vector;
import balliasbot.neuralnetwork.layer.DenseLayer;
import balliasbot.neuralnetwork.layer.LSTMLayer;
import balliasbot.neuralnetwork.weights.WeightInitializer;

public class RecurrentNeuralNetwork implements NeuralNetwork {

	private final DenseLayer inputLayer;
	private final LSTMLayer ltsmLayer;
	private final DenseLayer outputLayer;
	
	public RecurrentNeuralNetwork(int numberOfInputs, int numberOfLTSMNeurons, 
			int numberOfOutputs, WeightInitializer weights) {
		this.inputLayer = new DenseLayer(
				weights.get("input"), ActivationFunction.SIGMOID);
		this.ltsmLayer = new LSTMLayer(numberOfInputs,
				weights.get("ltsm_forget_gate"),
				weights.get("ltsm_input_gate_sigmoid"),
				weights.get("ltsm_input_gate_tanh"),
				weights.get("ltsm_output_gate"));
		this.outputLayer = new DenseLayer(
				weights.get("output"), ActivationFunction.SIGMOID);
	}

	@Override
	public Vector predict(Vector inputs) {
		// TODO Auto-generated method stub
		return null;
	}

}
