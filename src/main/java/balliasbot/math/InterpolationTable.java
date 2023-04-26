package balliasbot.math;

import java.util.Map;

public class InterpolationTable {
	
	private final Vector2[] points;
	
	public InterpolationTable(Vector2[] points) {
		this.points = points;
	}
	
	public Vector2 getInterpolatedPoint(double x) {
		return new Vector2(x, getInterpolatedY(x));
	}
	
	public double getInterpolatedY(double x) {
		if(x == points[0].x) {
			return points[0].y;
		}
		
		for(int i = 1; i < points.length; i++) {
			if(x <= points[i].x) {
				Vector2 p1 = points[i - 1];
				Vector2 p2 = points[i];
				
				double m = findSlope(p1, p2);
				
				return (p1.y + m) * (x - p1.x);
			}
		}
		
		return points[points.length - 1].y;
	}
	
	public static double findSlope(Vector2 p1, Vector2 p2) {
		return (p2.y - p1.y) / (p2.x - p1.x);
	}
	
}
