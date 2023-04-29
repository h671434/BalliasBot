package balliasbot.neuralnetwork.layer;

import balliasbot.math.ActivationFunction;
import balliasbot.math.Matrix;
import balliasbot.math.Vector;

public class DenseLayer extends NeuralLayer {

	protected Vector biases;
	protected Matrix weights;
	private ActivationFunction activation;
	
	public DenseLayer(Vector biases, Matrix weights, ActivationFunction activation) {
		this.biases = biases;
		this.weights = weights;
		this.activation = activation;
	}
	
	public DenseLayer(ActivationFunction activation) {
		this.activation = activation;
	}
	
	public Vector compute(Vector input) {
		Vector preActivation = weights.dot(input).plus(biases);
		
		return activate(preActivation);
	}
	
	protected Vector activate(Vector preActivation) {
		return preActivation.applyFunction(activation);
	}
	
	public Vector getBiases() {
		return biases;
	}
	
	public void setBiases(Vector biases) {
		this.biases = biases;
	}
	
	public double getBias(int index) {
		return biases.get(index);
	}
	
	public void setBias(int index, double bias) {
		biases.set(index, bias);
	}
	
	public Matrix getWeights() {
		return weights;
	}
	
	public void setWeights(Matrix weights) {
		this.weights = weights;
	}
	
	public Vector getWeightRow(int index) {
		return weights.getRowVector(index);
	}
	
	public void setWeightRow(int index, Vector row) {
		weights.setRowVector(index, row);
	}
	
	public double getWeight(int row, int column) {
		return weights.get(row, column);
	}
	
	public void setWeight(int row, int column, double weight) {
		weights.set(row, column, weight);
	}
	
	public static final class Builder {
		
		private Vector biases;
		private Matrix weights;
		private ActivationFunction activation;
		
		public Builder setBiases(Vector biases) {
			this.biases = biases;
			
			return this;
		}
		
		public Builder setWeights(Matrix weights) {
			this.weights = weights;
			
			return this;
		}
		
		public Builder setActivation(ActivationFunction activation) {
			this.activation = activation;
			
			return this;
		}
		
		public DenseLayer build() {
			return new DenseLayer(biases, weights, activation);
		}
		
	}
	
}
