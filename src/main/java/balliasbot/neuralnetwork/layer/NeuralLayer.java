package balliasbot.neuralnetwork.layer;

import balliasbot.math.Vector;

public abstract class NeuralLayer {

	public final String label;
	public final int numberOfInputs;
	public final int numberOfOutputs;
	
	public NeuralLayer(String label, int numberOfInputs, int numberOfOutputs) {
		this.label = label;
		this.numberOfInputs = numberOfInputs;
		this.numberOfOutputs = numberOfOutputs;
	}
	
	public NeuralLayer(int numberOfInputs, int numberOfOutputs) {
		this("layer", numberOfInputs, numberOfOutputs);
	}
	public abstract Vector compute(Vector input);
	
	public abstract Vector backpropagate(Vector error, Vector input, Vector output, double learningRate); 
	
}
