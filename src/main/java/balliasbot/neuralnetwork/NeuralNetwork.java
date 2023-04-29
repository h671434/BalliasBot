package balliasbot.neuralnetwork;

import java.util.List;

import balliasbot.math.Vector;

public interface NeuralNetwork {

	Vector predict(Vector inputs);
	
}
