package balliasbot.neuralnetwork.layer;

import balliasbot.math.Vector;

public abstract class NeuralLayer {
	
	protected int numberOfInputs;
	protected int numberOfOutputs;
	
	public NeuralLayer() {
		
	}
	
	public NeuralLayer(int numberOfInputs) {
		this.numberOfInputs = numberOfInputs;
	}
	
	public NeuralLayer(int numberOfInputs, int numberOfOutputs) {
		this.numberOfInputs = numberOfInputs;
		this.numberOfOutputs = numberOfInputs;
	}
	
	public abstract Vector compute(Vector input);
	
	public int getNumberOfInputs() {
		return numberOfInputs;
	}
	
	public void setNumberOfInputs(int numberOfInputs) {
		this.numberOfInputs = numberOfInputs;
	}
	
	public int getNumberOfOutputs() {
		return numberOfOutputs;
	}
	
	public void setNumberOfOutputs(int numberOfOutputs) {
		this.numberOfOutputs = numberOfOutputs;
	}
	
}
