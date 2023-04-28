package balliasbot.neuralnetwork;

import balliasbot.math.Matrix;
import balliasbot.math.Vector;
import balliasbot.neuralnetwork.layer.ForgetGateLayer;
import balliasbot.neuralnetwork.layer.HyperbolicTangentLayer;
import balliasbot.neuralnetwork.layer.InputGateLayer;
import balliasbot.neuralnetwork.layer.OutputGateLayer;

public class RecurrentNeuralNetwork {

	private final ForgetGateLayer forgetGate;
	private final InputGateLayer inputGate;
	private final HyperbolicTangentLayer cellStateUpdateCandidate;
	private final OutputGateLayer outputGate;
	private final HyperbolicTangentLayer cellStateUpdate;
	
	private CellState previousCellState; //ct-1
	private HiddenState previousHiddenState; //ht-1
	
	public Vector compute(Vector x) {
		Vector input = x.concat(previousHiddenState); // xt ht-1	
		
		Vector forgetGateResult = forgetGate.compute(input); // ft
		Vector inputGateResult = inputGate.compute(input); // it
		Vector cellStateCandidateResult = cellStateUpdateCandidate.compute(input); // c~t
		
		Vector newCellState = forgetGateResult.multiplyPointwise(previousCellState) // ct
					.plus(inputGateResult.multiplyPointwise(cellStateCandidateResult));
		
		Vector outputGateResult = outputGate.compute(input); // ot
		Vector cellStateUpdateResult = cellStateUpdate.compute(newCellState);
		
		Vector newHiddenState = outputGateResult.concat(cellStateUpdateResult);
		
		previousCellState = newCellState;
		previousHiddenState = newHiddenState;
		
		
		
		
		Vector output = null;
		return output;
	}
	
	
}
