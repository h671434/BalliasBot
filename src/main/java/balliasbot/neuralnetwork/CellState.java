package balliasbot.neuralnetwork;

import balliasbot.math.Matrix;
import balliasbot.math.Vector;

public class CellState extends Vector {

	public CellState(double[] data) {
		super(data);
	}

	public CellState(Vector vector) {
		super(vector);
	}
}
