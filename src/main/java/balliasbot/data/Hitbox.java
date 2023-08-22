package balliasbot.data;

import balliasbot.math.Vector3;

public class Hitbox {

	public static final Hitbox OCTANE = new Hitbox(new Vector3(118.01, 84.20, 36.16), -0.55, 55.13, 56.27);			
	public static final Hitbox DOMINUS = new Hitbox(new Vector3(127.93, 83.28, 31.30), -0.96, 47.22, 49.37);	
	public static final Hitbox BATMOBILE = new Hitbox(new Vector3(128.93, 84.67, 29.9), -0.34, 45.00, 45.77);	
	public static final Hitbox BREAKOUT = new Hitbox(new Vector3(131.49, 80.52, 30.30), -0.98, 43.89, 46.14);	
	public static final Hitbox HYBRID = new Hitbox(new Vector3(127.02, 82.19, 34.16), -0.55, 54.10, 55.32);	
	public static final Hitbox MERC = new Hitbox(new Vector3(120.72, 76.71, 41.66), 0.28, 61.46, 60.87);	
	
	public final Vector3 dimensions;
	public final double angle;
	public final double heightFront;
	public final double heightBack;
	
	private Hitbox(Vector3 dimensions, double angle, double heightFront, double heightBack) {
		this.dimensions = dimensions;
		this.angle = angle;
		this.heightFront = heightFront;
		this.heightBack = heightBack;
	}
}
