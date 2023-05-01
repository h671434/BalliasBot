package balliasbot.math;

import com.google.flatbuffers.FlatBufferBuilder;

/**
 * A 2-dimensional vector or point.
 */
public class Vector2 extends Vector {
	
	public final double x;
	public final double y;
	
	public Vector2(double x, double y) {
		super(new double[] {x, y});
        this.x = x;
        this.y = y;
    }
    
    public Vector2 withX(double x) {
    	return new Vector2(x, y);
    }
    
    public Vector2 withY(double y) {
    	return new Vector2(x, y);
    }
    
    public Vector2 plus(Vector2 other) {
        return new Vector2(x + other.x, y + other.y);
    }

    public Vector2 minus(Vector2 other) {
        return new Vector2(x - other.x, y - other.y);
    }

    public Vector2 scale(double scale) {
        return new Vector2(x * scale, y * scale);
    }
    
    @Override
    public String toString() {
        return String.format("(%s, %s, %s)", x, y);
    }
    
}
