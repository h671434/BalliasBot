package balliasbot.neuralnetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import balliasbot.math.Vector;

public class LSTMNetworkTest {

	private static final int NUMBER_OF_INPUTS = 8;
	private static final int NUMBER_OF_OUTPUTS = 4;
	private static final int NUMBER_OF_LAYERS = 4;
	private static final int NUMBER_OF_NEURONS = 32;
	
	private static final int EPOCHS = 4;
	private static final double LEARNING_RATE = 0.17;
	
	public static void main(String[] args) {
		List<Vector> inputs = new ArrayList<>();
		List<Vector> targets =  new ArrayList<>();
		
		Random random = new Random();
	
		for(int i = 0; i < 200; i++) {
			double[] inputData = new double[NUMBER_OF_INPUTS];
			for(int j = 0; j < NUMBER_OF_INPUTS; j++) {
				inputData[j] = j + random.nextDouble(1);
			}
			inputs.add(new Vector(inputData));
			
			double[] targetData = new double[NUMBER_OF_OUTPUTS];
			for(int j = 0; j < NUMBER_OF_OUTPUTS; j++) {
				targetData[j] = (double)j / 100 * 2 ;
			}
			targets.add(new Vector(targetData));
		}
		
		int neuronsPerLayer = NUMBER_OF_NEURONS / NUMBER_OF_LAYERS;
		
		LSTMNetwork network = new LSTMNetwork(
				NUMBER_OF_INPUTS, NUMBER_OF_LAYERS, neuronsPerLayer, NUMBER_OF_OUTPUTS);
		
		network.train(inputs, targets, EPOCHS, LEARNING_RATE);
		
	}
	
}
