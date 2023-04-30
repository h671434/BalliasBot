package balliasbot.neuralnetwork.layer;

import java.util.Random;

import balliasbot.math.ActivationFunction;
import balliasbot.math.Matrix;
import balliasbot.math.Vector;

/**
 * Long Short Term Memory used in recurrent neural network
 */
public class LSTMCell extends NeuralLayer {
	
	private final DenseLayer forgetGate;
	private final DenseLayer inputGateSigmoid;
	private final DenseLayer inputGateTanh;
	private final DenseLayer outputGate; 
	private final ResizeLayer resizeLayer;
	
	private Vector previousCellState; 
	private Vector previousCellOutput; 
	
	public LSTMCell(DenseLayer forgetGate, DenseLayer inputGateSigmoid, 
			DenseLayer inputGateTanh, DenseLayer outputGate,
			Vector previousCellState, Vector previousCellOutput) {
		this.forgetGate = forgetGate;
		this.inputGateSigmoid = inputGateSigmoid;
		this.inputGateTanh = inputGateTanh;
		this.outputGate = outputGate;
		this.previousCellState = previousCellState;
		this.previousCellOutput = previousCellOutput;
		this.resizeLayer = new ResizeLayer(previousCellOutput.size(), previousCellState.size());
	}
	
	public LSTMCell(int numberOfInputs, int weightsPerInput) {
		this.forgetGate = new DenseLayer(
				numberOfInputs * 2, weightsPerInput, ActivationFunction.SIGMOID);
		this.inputGateSigmoid = new DenseLayer(
				numberOfInputs * 2, weightsPerInput, ActivationFunction.SIGMOID);
		this.inputGateTanh = new DenseLayer(
				numberOfInputs * 2, weightsPerInput, ActivationFunction.TANH);
		this.outputGate = new DenseLayer(
				numberOfInputs * 2, weightsPerInput, ActivationFunction.SIGMOID);
		this.resizeLayer = new ResizeLayer(numberOfInputs * 2, numberOfInputs);
		
		initializePreviousCellData(numberOfInputs);
	}
	
	private void initializePreviousCellData(int numberOfInputs) {
		double[] previousCellStateData = new double[numberOfInputs * 2];
		double[] previousCellOutputData = new double[numberOfInputs];
		
		Random random = new Random();
		
		for(int i = 0; i < numberOfInputs * 2; i++) {
			previousCellStateData[i] = random.nextDouble(0.4, 0.7);
			
			if(i < numberOfInputs) {
				previousCellOutputData[i] = random.nextDouble(0.4, 0.7);	
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

		Vector newCellOutput = resizeLayer.compute(
				outputGateResult.multiplyPointwise(
				newCellState.applyFunction(ActivationFunction.TANH)));

		previousCellState = newCellState;
		previousCellOutput = newCellOutput;
		
		return newCellOutput;
	}
	
	public static final class Builder {
		
		private DenseLayer forgetGate;
		private DenseLayer inputGateSigmoid;
		private DenseLayer inputGateTanh;
		private DenseLayer outputGate; 
		private Vector previousCellState; 
		private Vector previousCellOutput; 
		
		public Builder setForgetGate(DenseLayer forgetGate) {
			this.forgetGate = forgetGate;
			
			return this;
		}
		
		public Builder setInputGateSigmoid(DenseLayer inputGateSigmoid) {
			this.inputGateSigmoid = inputGateSigmoid;
			
			return this;
		}
		
		public Builder setInputGateTanh(DenseLayer inputGateTanh) {
			this.inputGateTanh = inputGateTanh;
			
			return this;
		}
		
		public Builder setOutputGate(DenseLayer outputGate) {
			this.outputGate = outputGate;
			
			return this;
		}
		
		public Builder setPreviousCellState(Vector previousCellState) {
			this.previousCellState = previousCellState;
			
			return this;
		}
		
		public Builder setPreviousCellOutput(Vector previousCellOutput) {
			this.previousCellOutput = previousCellOutput;
			
			return this;
		}
		
		public LSTMCell build() {
			return new LSTMCell(forgetGate, inputGateSigmoid, inputGateTanh,
					outputGate, previousCellState, previousCellOutput);
		}
		
	}
	
}
