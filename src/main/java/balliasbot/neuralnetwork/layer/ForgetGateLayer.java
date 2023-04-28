package balliasbot.neuralnetwork.layer;

import balliasbot.math.Matrix;
import balliasbot.math.Vector;

public class ForgetGateLayer extends SigmoidLayer {
	
	public ForgetGateLayer(Vector weights) {
		super(weights);
	}

}
