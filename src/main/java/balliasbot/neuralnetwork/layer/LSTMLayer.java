package balliasbot.neuralnetwork.layer;

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
	
	public LSTMLayer() {
		this.forgetGate = new DenseLayer(ActivationFunction.SIGMOID);
		this.inputGateSigmoid = new DenseLayer(ActivationFunction.SIGMOID);
		this.inputGateTanh = new DenseLayer(ActivationFunction.TANH);
		this.outputGate = new DenseLayer(ActivationFunction.SIGMOID);
	}
	
	public LSTMLayer(DenseLayer forgetGate, DenseLayer inputGateSigmoid, 
			DenseLayer inputGateTanh, DenseLayer outputGate,
			Vector previousCellState, Vector previousCellOutput) {
		this.forgetGate = forgetGate;
		this.inputGateSigmoid = inputGateSigmoid;
		this.inputGateTanh = inputGateTanh;
		this.outputGate = outputGate;
		this.previousCellState = previousCellState;
		this.previousCellOutput = previousCellOutput;
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
				newCellState.applyFunction(ActivationFunction.TANH));
		
		previousCellState = newCellState;
		previousCellOutput = newCellOutput;
		
		return newCellOutput;
	}
	
	public void setForgetGateBiases(Vector biases) {
		forgetGate.setBiases(biases);
	}
	
	public void setForgetGateWeights(Matrix weights) {
		forgetGate.setWeights(weights);
	}
	
	public void setInputGateSigmoidBiases(Vector biases) {
		inputGateSigmoid.setBiases(biases);
	}
	
	public void setInputGateSigmoidWeights(Matrix weights) {
		inputGateSigmoid.setWeights(weights);
	}
	
	public void setInputGateTanhBiases(Vector biases) {
		inputGateTanh.setBiases(biases);
	}
	
	public void setInputGateTanhWeights(Matrix weights) {
		inputGateTanh.setWeights(weights);
	}
	
	
	public void setOutputGateBiases(Vector biases) {
		outputGate.setBiases(biases);
	}
	
	public void setOutputGateWeights(Matrix weights) {
		outputGate.setWeights(weights);
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
		
		public LSTMLayer build() {
			return new LSTMLayer(forgetGate, inputGateSigmoid, inputGateTanh,
					outputGate, previousCellState, previousCellOutput);
		}
		
	}
	
}
