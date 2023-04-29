package balliasbot.neuralnetwork;

import balliasbot.math.Vector;

public interface NeuralNetwork {

	Vector predict(Vector cellInput);
	
}
