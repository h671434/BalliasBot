package balliasbot.neuralnetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import balliasbot.math.ActivationFunction;
import balliasbot.math.Vector;
import balliasbot.neuralnetwork.layer.DenseLayer;
import balliasbot.neuralnetwork.layer.DropoutLayer;
import balliasbot.neuralnetwork.layer.LSTMCell;
import balliasbot.neuralnetwork.layer.ResizeLayer;
import balliasbot.neuralnetwork.weights.WeightInitializer;

public class LSTMNetwork extends NeuralNetwork {
	
	public LSTMNetwork(int numberOfInputs, int numberOfLayers,
			int weightsPerLayer, int numberOfOutputs) {
		for(int i = 0; i < numberOfLayers; i++) {
			layers.add(new LSTMCell(numberOfInputs, weightsPerLayer));
			layers.add(new DropoutLayer(0.04));
		}
		layers.add(new ResizeLayer(numberOfInputs, numberOfOutputs));
	}
	

	
	public static void main(String[] args) {
		List<Vector> inputs = new ArrayList<>();
		List<Vector> targets =  new ArrayList<>();
		
		Random random = new Random();
		
		for(int i = 0; i < 200; i++) {
			inputs.add(new Vector(new double[] {
					random.nextDouble(1),
					random.nextDouble(1),
					random.nextDouble(1),
					random.nextDouble(1),
					random.nextDouble(1),
					random.nextDouble(1),
					random.nextDouble(1),
					random.nextDouble(1)
			}));
			targets.add(new Vector(new double[] {
					random.nextDouble(0, 1),
					random.nextDouble(0, 1),
					random.nextDouble(0, 1),
					random.nextDouble(0, 1),
					random.nextDouble(0, 1),
					random.nextDouble(0, 1),
					random.nextDouble(0, 1),
					random.nextDouble(0, 1)
			}));
		}
		
		LSTMNetwork network = new LSTMNetwork(8, 1, 16, 4);
		
		List<Vector> outputs = network.train(inputs, targets, 1, 0.07);
		
		for(int i = 0; i < inputs.size(); i++) {
			System.out.println("input " + inputs.get(i).toString());				
			System.out.println("target" + targets.get(i).toString());
			System.out.println("output" + outputs.get(i).toString());
		}
		
	}
	
}
