package balliasbot.math;

import com.google.flatbuffers.FlatBufferBuilder;

/**
 * A 3-dimensional vector or point.
 */
public class Vector3 extends Vector {
	
	public static final Vector3 ZERO = new Vector3(0, 0, 0);
	public static final Vector3 UP = new Vector3(0, 0, 1);
	
	public final double x;
	public final double y;
	public final double z;
	
	public Vector3(double x, double y, double z) {
        super(new double[] {x, y, z});
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(rlbot.flat.Vector3 vec) {
        // Invert the X value so that the axes make more sense.
        this(-vec.x(), vec.y(), vec.z());
    }
    
    public rlbot.vector.Vector3 toRLBotVector() {
    	return new rlbot.vector.Vector3((float) -x, (float) y, (float) z);
    }

	public int toFlatbuffer(FlatBufferBuilder builder) {
        // Invert the X value again so that rlbot sees the format it expects.
        return rlbot.flat.Vector3.createVector3(builder, (float) -x, (float) y, (float) z);
    }
	
    public Vector3 withX(double x) {
    	return new Vector3(x, y, z);
    }
    
    public Vector3 withY(double y) {
    	return new Vector3(x, y, z);
    }
    
    public Vector3 withZ(double z) {
    	return new Vector3(x, y, z);
    }

    public Vector3 plus(Vector3 other) {
        return new Vector3(x + other.x, y + other.y, z + other.z);
    }

    public Vector3 minus(Vector3 other) {
        return new Vector3(x - other.x, y - other.y, z - other.z);
    }

    public Vector3 scale(double scale) {
        return new Vector3(x * scale, y * scale, z * scale);
    }

    public Vector3 scaleToMagnitude(double magnitude) {
        if (isZero()) {
            throw new IllegalStateException("Cannot scale up a vector with length zero!");
        }
        
        double scaleRequired = magnitude / magnitude();
        
        return scale(scaleRequired);
    }

    public Vector3 normalized() {
        if (isZero()) {
            throw new IllegalStateException("Cannot normalize a vector with length zero!");
        }
        
        return this.scale(1 / magnitude());
    }

    public double distance(Vector3 other) {
        double xDiff = x - other.x;
        double yDiff = y - other.y;
        double zDiff = z - other.z;
        
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
    }

    public Vector3 flatten() {
        return new Vector3(x, y, 0);
    }
    
	public Vector3 flatten(Vector3 up) {
		up = up.normalized();
		
		return minus(up.scale(dot(up)));
	}

    public double angle(Vector3 v) {
        double mag2 = magnitudeSquared();
        double vmag2 = v.magnitudeSquared();
        double dot = dot(v);
        return Math.acos(dot / Math.sqrt(mag2 * vmag2));
    }

    public Vector3 crossProduct(Vector3 v) {
        double tx = y * v.z - z * v.y;
        double ty = z * v.x - x * v.z;
        double tz = x * v.y - y * v.x;
        return new Vector3(tx, ty, tz);
    }
    
    public Vector3 offset(Vector3 direction, double offsetvalue) {
    	return minus(direction.scale(offsetvalue));
    }

	public Vector3 clamp(Vector3 start, Vector3 end) {
		Vector3 direction = this;
		
		Vector3 down = new Vector3(0, 0, -1);
		boolean isRight = direction.dot(end.crossProduct(down)) < 0;
		boolean isLeft = direction.dot(start.crossProduct(down)) > 0;
		
		if((end.dot(start.crossProduct(down)) < 0) 
				? (isRight && isLeft) 
				: (isRight || isLeft)) {
			return direction;
		}
		
		if(start.dot(direction) < end.dot(direction)) {
			return end;
		}
		
		return start;
	}
    
    @Override
    public String toString() {
        return String.format("(%s, %s, %s)", x, y, z);
    }
    
}
