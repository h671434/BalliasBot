package balliasbot.math;

public class Vector {

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

    public Vector scaled(double scale) {
    	double[] newArray = new double[data.length];
        for(int i = 0; i < data.length; i++) {
        	newArray[i] = data[i] * scale;
        }
        
        return new Vector(newArray);
    }

    public Vector scaledToMagnitude(double magnitude) {
        if (isZero()) {
            throw new IllegalStateException("Cannot scale up a vector with length zero!");
        }
        
        double scaleRequired = magnitude / magnitude();
        
        return scaled(scaleRequired);
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
        
        return this.scaled(1 / magnitude());
    }
    
    public double dot(Vector other) {
    	if(this.size() != other.size()) {
    		throw new IllegalArgumentException("Vectors are of different sizes");
    	}
    	
        int sum = 0;
        for(int i = 0; i < data.length; i++) {
        	sum += data[i] * other.data[i];
        }
        
        return sum;
    }
    
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
    
    public Vector applyFunction(ActivationFunction function) {
    	double[] newData = new double[data.length];
    	for(int i = 0; i < data.length; i++) {
    		newData[i] = function.calculate(data[i]);
    	}
    	
    	return new Vector(newData);
    }
    
}