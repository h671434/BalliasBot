package balliasbot.neuralnetwork.layer;

import balliasbot.math.Vector;

public abstract class NeuralLayer {

	public final int numberOfInputs;
	public final int numberOfOutputs;
	
	public NeuralLayer(int numberOfInputs, int numberOfOutputs) {
		this.numberOfInputs = numberOfInputs;
		this.numberOfOutputs = numberOfOutputs;
	}
	
	public abstract Vector compute(Vector input);
	
	public abstract Vector backpropagate(Vector error, Vector input, Vector output, double learningRate); 
	
}
