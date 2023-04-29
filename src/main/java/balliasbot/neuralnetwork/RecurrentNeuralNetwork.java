package balliasbot.neuralnetwork;

import balliasbot.math.ActivationFunction;
import balliasbot.math.Vector;
import balliasbot.neuralnetwork.layer.DenseLayer;
import balliasbot.neuralnetwork.layer.InputLayer;
import balliasbot.neuralnetwork.layer.LSTMLayer;
import balliasbot.neuralnetwork.layer.OutputLayer;
import balliasbot.neuralnetwork.weights.WeightInitializer;

public class RecurrentNeuralNetwork implements NeuralNetwork {

	private final InputLayer inputLayer;
	private final LSTMLayer ltsmLayer;
	private final OutputLayer outputLayer;
	
	public RecurrentNeuralNetwork(int numberOfInputs, int numberOfLTSMNeurons, 
			int numberOfOutputs, WeightInitializer weights) {
		this.inputLayer = new InputLayer();
		this.ltsmLayer = new LSTMLayer(numberOfLTSMNeurons,
				weights.get("ltsm_forget_gate"),
				weights.get("ltsm_input_gate_sigmoid"),
				weights.get("ltsm_input_gate_tanh"),
				weights.get("ltsm_output_gate"));
		this.outputLayer = new OutputLayer();
	}

	@Override
	public Vector predict(Vector cellInput) {
		// TODO Auto-generated method stub
		return null;
	}

}
