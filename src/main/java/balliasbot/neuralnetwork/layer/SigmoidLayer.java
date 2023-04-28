package balliasbot.neuralnetwork.layer;

import balliasbot.math.ActivationFunction;
import balliasbot.math.Matrix;
import balliasbot.math.Vector;

public class SigmoidLayer extends NeuralLayer {

	public SigmoidLayer(Vector weights) {
		super(weights, new ActivationFunction.Sigmoid());
	}
	
}
