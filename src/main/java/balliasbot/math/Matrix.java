package balliasbot.math;

import java.util.Random;
import java.util.function.Function;

public class Matrix {
	
	public final int rows;
	public final int columns;
	private double[][] data;
	
	public Matrix(double[][] data) {
		this.rows = data.length;
		this.columns = data[0].length;
		this.data = data;
	}
	
	public double get(int row, int column) {
		return data[row][column];
	}
	
	public void set(int row, int column, double value) {
		data[row][column] = value;
	}
	
	public Vector getRowVector(int row) {
		return new Vector(data[row]);
	}
	
	public void setRowVector(int rowIndex, Vector newRow) {
		data[rowIndex] = newRow.asArray();
	}
	
	public Vector getColumnVector(int column) {
		double[] columnArray = new double[data[0].length];
		for(int i = 0; i < data[0].length; i++) {
			columnArray[i] = data[i][column];
		}
		
		return new Vector(columnArray);
	}
	
	/**
	 * Returns a new Matrix where each rows is equal to the current row minus
	 * the vector.
	 */
	public Matrix minus(Vector vector) {
		double[][] newData = new double[rows][columns];
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				newData[i][j] = data[i][j] - vector.get(i);
			}
		}
		
		return new Matrix(newData);
	}
	
	public Matrix plus(double x) {
		double[][] sum = new double[rows][columns];
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				sum[i][j] = data[i][j] + x;
			}
		}
		
		return new Matrix(sum);
	}
	
	public Matrix transform() {
		double[][] transformed = new double[columns][rows];
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				transformed[j][i] = data[i][j];
			}
		}
		
		return new Matrix(transformed);
	}
	
	public Matrix dot(Matrix b) {
		if (columns != b.rows) {         
			throw new IllegalArgumentException("Matrix dimensions are incompatible.");      
		}
		  
		double[][] entries = new double[rows][b.columns];  
		for (int row = 0; row < rows; row++) {         
			for (int col = 0; col < b.columns; col++) {              
				entries[row][col] = getRowVector(row).dot(b.getColumnVector(col));         
			}      
		}    
		
		return new Matrix(entries);
	}
	
	public Matrix applyFunction(Function<Double, Double> function) {
		double[][] activated = new double[rows][columns];
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				activated[i][j] = function.apply(data[i][j]);
			}
		}
		
		return new Matrix(activated);
	}
	
	public static Matrix identity(int n) {
		double[][] data = new double[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				data[i][j] = i == j ? 1 : 0;
			}
		}
		
		return new Matrix(data);
	}

	public Vector dot(Vector input) {
		double[] outputData = new double[rows];
		for(int i = 0; i < rows; i++) {
			outputData[i] = input.dot(getRowVector(i));
		}
		
		
		return new Vector(outputData);
	}
	
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		
		for(int i = 0; i < rows; i++) {
			out.append(getRowVector(i).toString() + "\n");
		}
		
		return out.toString();
	}
	
}
