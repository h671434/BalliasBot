package balliasbot.math;

public class Vector {

	private double[] data;
	
	public Vector(double[] data) {
		this.data = data;
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
    
}
