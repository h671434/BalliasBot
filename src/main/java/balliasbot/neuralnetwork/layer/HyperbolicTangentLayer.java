package balliasbot.neuralnetwork.layer;

import balliasbot.math.ActivationFunction;
import balliasbot.math.Matrix;
import balliasbot.math.Vector;

public class HyperbolicTangentLayer extends NeuralLayer {

	public HyperbolicTangentLayer(Vector weights) {
		super(weights, new ActivationFunction.HyperbolicTangent());
	}

}
