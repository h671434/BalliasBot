package balliasbot.neuralnetwork;

import balliasbot.math.Matrix;
import balliasbot.math.Vector;

public class HiddenState extends Vector {

	public HiddenState(double[] data) {
		super(data);
	}

	public HiddenState(Vector vector) {
		super(vector);
	}

}
