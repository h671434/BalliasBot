package balliasbot.math;

import com.google.flatbuffers.FlatBufferBuilder;

public class Vec3 extends rlbot.vector.Vector3 {

	public static final Vec3 ZERO = new Vec3(0, 0, 0);
	public static final Vec3 UP = new Vec3(0, 0, 1);
	
	public Vec3(double x, double y, double z) {
        super((float) x, (float) y, (float) z);
    }

    public Vec3() {
        this(0, 0, 0);
    }

    public Vec3(rlbot.flat.Vector3 vec) {
        // Invert the X value so that the axes make more sense.
        this(-vec.x(), vec.y(), vec.z());
    }
    
    public Vec3 withX(double x) {
    	return new Vec3(x, y, z);
    }
    
    public Vec3 withY(double y) {
    	return new Vec3(x, y, z);
    }
    
    public Vec3 withZ(double z) {
    	return new Vec3(x, y, z);
    }

    @Override
	public int toFlatbuffer(FlatBufferBuilder builder) {
        // Invert the X value again so that rlbot sees the format it expects.
        return rlbot.flat.Vector3.createVector3(builder, -x, y, z);
    }

    public Vec3 plus(Vec3 other) {
        return new Vec3(x + other.x, y + other.y, z + other.z);
    }

    public Vec3 minus(Vec3 other) {
        return new Vec3(x - other.x, y - other.y, z - other.z);
    }

    public Vec3 scaled(double scale) {
        return new Vec3(x * scale, y * scale, z * scale);
    }

    /**
     * If magnitude is negative, we will return a vector facing the opposite direction.
     */
    public Vec3 scaledToMagnitude(double magnitude) {
        if (isZero()) {
            throw new IllegalStateException("Cannot scale up a vector with length zero!");
        }
        
        double scaleRequired = magnitude / magnitude();
        
        return scaled(scaleRequired);
    }

    public double distance(Vec3 other) {
        double xDiff = x - other.x;
        double yDiff = y - other.y;
        double zDiff = z - other.z;
        
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
    }

    public double magnitude() {
        return Math.sqrt(magnitudeSquared());
    }

    public double magnitudeSquared() {
        return x * x + y * y + z * z;
    }

    public Vec3 normalized() {

        if (isZero()) {
            throw new IllegalStateException("Cannot normalize a vector with length zero!");
        }
        return this.scaled(1 / magnitude());
    }

    public double dotProduct(Vec3 other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public boolean isZero() {
        return x == 0 && y == 0 && z == 0;
    }

    public Vec3 flatten() {
        return new Vec3(x, y, 0);
    }
    
	public Vec3 flatten(Vec3 up) {
		up = up.normalized();
		
		return minus(up.scaled(dotProduct(up)));
	}

    public double angle(Vec3 v) {
        double mag2 = magnitudeSquared();
        double vmag2 = v.magnitudeSquared();
        double dot = dotProduct(v);
        return Math.acos(dot / Math.sqrt(mag2 * vmag2));
    }

    public Vec3 crossProduct(Vec3 v) {
        double tx = y * v.z - z * v.y;
        double ty = z * v.x - x * v.z;
        double tz = x * v.y - y * v.x;
        return new Vec3(tx, ty, tz);
    }
    
    public Vec3 offset(Vec3 direction, double offsetvalue) {
    	return minus(direction.scaled(offsetvalue));
    }

	public Vec3 clamp(Vec3 start, Vec3 end) {
		Vec3 direction = this;
		
		Vec3 down = new Vec3(0, 0, -1);
		boolean isRight = direction.dotProduct(end.crossProduct(down)) < 0;
		boolean isLeft = direction.dotProduct(start.crossProduct(down)) > 0;
		
		if((end.dotProduct(start.crossProduct(down)) < 0) 
				? (isRight && isLeft) 
				: (isRight || isLeft)) {
			return direction;
		}
		
		if(start.dotProduct(direction) < end.dotProduct(direction)) {
			return end;
		}
		
		return start;
	}
    
    
    /**
     * The correction angle is how many radians you need to rotate this vector to make it line up with the "ideal"
     * vector. This is very useful for deciding which direction to steer.
     */
    public double correctionAngle(Vec3 ideal) {
        double currentRad = Math.atan2(y, x);
        double idealRad = Math.atan2(ideal.y, ideal.x);

        if (Math.abs(currentRad - idealRad) > Math.PI) {
            if (currentRad < 0) {
                currentRad += Math.PI * 2;
            }
            if (idealRad < 0) {
                idealRad += Math.PI * 2;
            }
        }

        return idealRad - currentRad;
    }

    /**
     * Will always return a positive value <= Math.PI
     */
    public static double angle(Vec3 a, Vec3 b) {
        return Math.abs(a.correctionAngle(b));
    }
    
    @Override
    public String toString() {
        return String.format("(%s, %s, %s)", x, y, z);
    }
    
}
