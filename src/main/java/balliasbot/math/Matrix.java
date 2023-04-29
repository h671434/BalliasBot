package balliasbot.math;

import java.util.Random;

public class Matrix {
	
	public final int rows;
	public final int columns;
	private double[][] data;
	
	public Matrix(double[][] data) {
		this.rows = data.length;
		this.columns = data[0].length;
		this.data = data;
	}
	
	public Vector rowVector(int row) {
		return new Vector(data[row]);
	}
	
	public Vector columnVector(int column) {
		double[] columnArray = new double[data[0].length];
		for(int i = 0; i < data[0].length; i++) {
			columnArray[i] = data[i][column];
		}
		
		return new Vector(columnArray);
	}
	
	public Matrix addColumn(Vector column) {
		double[][] arr = new double[rows][columns + 1]; 
		
		return new Matrix(arr);
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
				entries[row][col] = rowVector(row).dot(b.columnVector(col));         
			}      
		}    
		
		return new Matrix(entries);
	}
	
	public Matrix applyFunction(ActivationFunction function) {
		double[][] activated = new double[rows][columns];
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				activated[i][j] = function.calculate(data[i][j]);
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
		double[] outputData = new double[input.size()];
		for(int i = 0; i < outputData.length; i++) {
			outputData[i] = input.dot(rowVector(i));
		}
		
		return new Vector(outputData);
	}
	
}
