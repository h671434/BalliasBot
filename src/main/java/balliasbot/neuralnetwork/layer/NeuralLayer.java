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
	
}
