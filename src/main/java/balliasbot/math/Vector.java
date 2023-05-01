package balliasbot.math;

import java.util.Iterator;
import java.util.function.Function;

public class Vector implements Iterable<Double> {

	private double[] data;
	
	public Vector(double[] data) {
		this.data = data;
	}
	
	public Vector(Vector vector) {
		data = new double[vector.data.length];
		for(int i = 0; i < data.length; i++) {
			data[i] = vector.data.length;
		}
	}
	
	public double get(int index) {
		return data[index];
	}
	
	public double[] asArray() {
		double[] copy = new double[data.length];
		for(int i = 0; i < data.length; i++) {
			copy[i] = data[i];
		}
		
		return copy;
	}
	
	public int size() {
		return data.length;
	}
	
    public Vector plus(Vector other) {
    	if(this.size() != other.size()) {
    		throw new IllegalArgumentException("Vectors are of different sizes");
    	}
    	
    	double[] newArray = new double[data.length];
        for(int i = 0; i < data.length; i++) {
        	newArray[i] = data[i] + other.data[i];
        }
        
        return new Vector(newArray);
    }
    
    public Vector plus(double x) {
    	double[] newData = new double[data.length];
    	for(int i = 0; i < data.length; i++) {
    		newData[i] = data[i] + x;
    	}
    	
    	return new Vector(newData);
    }

    public Vector minus(Vector other) {
    	if(this.size() != other.size()) {
    		throw new IllegalArgumentException("Vectors are of different sizes");
    	}
    	
    	double[] newArray = new double[data.length];
        for(int i = 0; i < data.length; i++) {
        	newArray[i] = data[i] - other.data[i];
        }
        
        return new Vector(newArray);
    }

    public Vector scale(double scale) {
    	double[] newArray = new double[data.length];
        for(int i = 0; i < data.length; i++) {
        	newArray[i] = data[i] * scale;
        }
        
        return new Vector(newArray);
    }

    public Vector scaleToMagnitude(double magnitude) {
        if (isZero()) {
            throw new IllegalStateException("Cannot scale up a vector with length zero!");
        }
        
        double scaleRequired = magnitude / magnitude();
        
        return scale(scaleRequired);
    }
	
    public double magnitude() {
        return Math.sqrt(magnitudeSquared());
    }
    
    public double magnitudeSquared() {
    	double sum = 0;
    	for(double d : data) {
    		sum += d * d;
    	}
    	
        return sum;
    }
    
    public boolean isZero() {
    	for(double d : data) {
    		if(d != 0) {
    			return false;
    		}
    	}
    	
    	return true;
    }

    public Vector normalized() {
        if (isZero()) {
            throw new IllegalStateException("Cannot normalize a vector with length zero!");
        }
        
        return this.scale(1 / magnitude());
    }
    
    public double dot(Vector other) {
    	if(this.size() != other.size()) {
    		throw new IllegalArgumentException("Vectors are of different sizes");
    	}
    	
        double sum = 0;
        for(int i = 0; i < data.length; i++) {
        	sum += data[i] * other.get(i);
        }
        
        return sum;
    }
    
    /**
     * Concatation of this and the other vector.
     * Returns a new vector where all values from both vectors are included.
     */
    public Vector concat(Vector vector) {
    	int size = data.length + vector.data.length;
    	double[] newData = new double[size];
    	
    	for(int i = 0; i < size; i++) {
    		if(i < data.length) {
    			newData[i] = data[i];
    		} else {
    			newData[i] = vector.data[i - data.length]; 
    		}
    	}
    	
    	return new Vector(newData);
    }
    
    public Vector concat(double value) {
    	double[] newData = new double[data.length + 1];
    	
    	for(int i = 0; i < data.length; i++) {
    		newData[i] = data[i];
    	}
    	
    	newData[data.length] = value;
    	
    	return new Vector(newData);
    }
    
    /**
     * Multiplies each value with the value at the same index of the other
     * vector. Also known as the hamard product.
     */
    public Vector multiplyPointwise(Vector vector) {
    	if(vector.data.length != data.length) {
    		throw new IllegalArgumentException("Vectors of different size");
    	}
    	
    	double[] newData = new double[data.length];
    	for(int i = 0; i < data.length; i++) {
    		newData[i] = data[i] * vector.data[i];
    	}
    	
    	return new Vector(newData);
    }
    
    /**
     * Returns a new vector where the function is applied to each value in the 
     * vector.
     */
    public Vector applyFunction(Function<Double, Double> function) {
    	double[] newData = new double[data.length];
    	for(int i = 0; i < data.length; i++) {
    		newData[i] = function.apply(data[i]);
    	}
    	
    	return new Vector(newData);
    }

	@Override
	public Iterator<Double> iterator() {
		return new Iterator<Double>() {
			int count = 0;

			@Override
			public boolean hasNext() {
				return (count < data.length);
			}

			@Override
			public Double next() {
		    	double result = data[count];
		    	count++;
		    	
				return result;
			}
		};
	}
	
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		
		out.append("(");
		for(int i = 0; i < data.length; i++) {
			out.append(data[i] + ",");
		}
		out.append(")");
		
		return out.toString();
	}
	
}
