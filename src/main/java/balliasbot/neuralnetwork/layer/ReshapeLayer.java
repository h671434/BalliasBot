package balliasbot.neuralnetwork.layer;

import balliasbot.math.Vector;

public class ReshapeLayer extends NeuralLayer {

	protected int numberOfOutputs;
	
	public ReshapeLayer(int numberOfInputs, int numberOfOutputs) {
		super(numberOfInputs);
		this.numberOfOutputs = numberOfOutputs;
	}

	@Override
	public Vector compute(Vector input) {
		return null;
	}
	
}
