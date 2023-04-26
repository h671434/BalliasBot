package balliasbot.math;

import java.util.Random;

public class Matrix {
	
	private static final Random RANDOM = new Random();
	
	public final int rows;
	public final int columns;
	private double[][] data;
	
	public Matrix(double[][] data) {
		this.rows = data.length;
		this.columns = data[0].length;
		this.data = data;
	}
	
	public Matrix(int rows, int columns) {
		this.data = initMatrixArray(rows, columns);
		this.rows = rows;
		this.columns = columns;
	}
	
	private static double[][] initMatrixArray(int rows, int columns) {
		double[][] data = new double[rows][columns];
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				data[i][j] = RANDOM.nextDouble(-1, 1);
			}
		}
		
		return data;
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
	
	public static Matrix identity(int n) {
		double[][] data = new double[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				data[i][j] = i == j ? 1 : 0;
			}
		}
		
		return new Matrix(data);
	}
	
}
