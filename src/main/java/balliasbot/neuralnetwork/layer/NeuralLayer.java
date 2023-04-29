package balliasbot.neuralnetwork.layer;

import balliasbot.math.Vector;

public abstract class NeuralLayer {

	protected int numberOfInputs;
	
	public NeuralLayer(int numberOfInputs) {
		this.numberOfInputs = numberOfInputs;
	}
	
	public abstract Vector compute(Vector input);
	
}
