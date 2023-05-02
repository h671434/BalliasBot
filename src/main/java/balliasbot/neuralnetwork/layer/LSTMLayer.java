package balliasbot.neuralnetwork.layer;

import java.util.Random;

import balliasbot.math.ActivationFunction;
import balliasbot.math.Matrix;
import balliasbot.math.Vector;

/**
 * Long Short Term Memory used in recurrent neural network
 */
public class LSTMLayer extends NeuralLayer {
	
	private final DenseLayer forgetGate;
	private final DenseLayer inputGateSigmoid;
	private final DenseLayer inputGateTanh;
	private final DenseLayer outputGate; 
	
	private Vector previousCellState; 
	private Vector previousCellOutput; 
	
	public LSTMLayer(int numberOfInputs, int numberOfOutputs) {
		super(numberOfInputs, numberOfOutputs);
		this.forgetGate = new DenseLayer(
				numberOfInputs + numberOfOutputs, numberOfOutputs, ActivationFunction.SIGMOID);
		this.inputGateSigmoid = new DenseLayer(
				numberOfInputs + numberOfOutputs, numberOfOutputs, ActivationFunction.SIGMOID);
		this.inputGateTanh = new DenseLayer(
				numberOfInputs + numberOfOutputs, numberOfOutputs, ActivationFunction.TANH);
		this.outputGate = new DenseLayer(
				numberOfInputs + numberOfOutputs, numberOfOutputs, ActivationFunction.SIGMOID);
		
		initializePreviousCellData();
	}
	
	private void initializePreviousCellData() {
		double[] previousCellStateData = new double[numberOfOutputs];
		double[] previousCellOutputData = new double[numberOfOutputs];
		
		for(int i = 0; i < numberOfOutputs; i++) {
			previousCellStateData[i] = 0;
			
			if(i < numberOfOutputs) {
				previousCellOutputData[i] = 0;	
			}
		}
		
		previousCellState = new Vector(previousCellStateData);
		previousCellOutput = new Vector(previousCellOutputData);
	}
	
	@Override
	public Vector compute(Vector cellInput) { 
		Vector input = cellInput.concat(previousCellOutput); 
		
		Vector forgetGateResult = forgetGate.compute(input); 
		
		Vector inputGateSigmoidResult = inputGateSigmoid.compute(input); 
		Vector inputGateTanhResult = inputGateTanh.compute(input);
		Vector inputGateResult = inputGateSigmoidResult.multiplyPointwise(inputGateTanhResult);
		
		Vector newCellState = forgetGateResult.multiplyPointwise(previousCellState) 
				.plus(inputGateResult);
		
		Vector outputGateResult = outputGate.compute(input); 

		Vector newCellOutput = outputGateResult.multiplyPointwise(
				ActivationFunction.TANH.calculate(newCellState));

		previousCellState = newCellState;
		previousCellOutput = newCellOutput;
		
		return newCellOutput;
	}

	@Override
	public Vector backpropagate(Vector error, Vector output, double learningRate) {
		// TODO Auto-generated method stub
		return error;
	}
	
}
