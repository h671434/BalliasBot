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
	
	public LSTMLayer(int numberOfInputs, Matrix forgetGateWeights, 
			Matrix inputGateSigmoidWeights, Matrix inputGateTanhWeights, 
			Matrix outputGateWeights) {
		super(forgetGateWeights.rows);
		this.forgetGate = new DenseLayer(forgetGateWeights, ActivationFunction.SIGMOID);
		this.inputGateSigmoid = new DenseLayer(inputGateSigmoidWeights, ActivationFunction.SIGMOID);
		this.inputGateTanh = new DenseLayer(inputGateTanhWeights, ActivationFunction.TANH);
		this.outputGate = new DenseLayer( outputGateWeights, ActivationFunction.SIGMOID);
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
	
}
