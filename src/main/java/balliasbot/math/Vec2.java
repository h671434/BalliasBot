package balliasbot.math;

import com.google.flatbuffers.FlatBufferBuilder;

/**
 * A 2-dimensional vector or point.
 */
public class Vec2 {
	
	public final double x;
	public final double y;
	
	public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public Vec2 withX(double x) {
    	return new Vec2(x, y);
    }
    
    public Vec2 withY(double y) {
    	return new Vec2(x, y);
    }
    
    public Vec2 plus(Vec2 other) {
        return new Vec2(x + other.x, y + other.y);
    }

    public Vec2 minus(Vec2 other) {
        return new Vec2(x - other.x, y - other.y);
    }

    public Vec2 scaled(double scale) {
        return new Vec2(x * scale, y * scale);
    }

    public boolean isZero() {
        return x == 0 && y == 0;
    }

    
    
    @Override
    public String toString() {
        return String.format("(%s, %s, %s)", x, y);
    }
    
}
