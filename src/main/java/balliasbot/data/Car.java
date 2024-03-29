package balliasbot.data;

import java.util.Map;

import balliasbot.math.InterpolationTable;
import balliasbot.math.RotationMatrix;
import balliasbot.math.Vector2;
import balliasbot.math.Vector3;
import rlbot.flat.PlayerInfo;

public class Car extends Kinematics {
	
    /** 
     * Used to find the curvature of the cars trajectory when turning.
     * Curvature is dependent on cars current speed, and can be found
     * by interpolation with the values below. 
     * Each Vector2 represents a point where 'x = velocity' and 'y = curvature'.
     * @see https://samuelpmish.github.io/notes/RocketLeague/ground_control/
     */
	private static final InterpolationTable CURVATURE_BY_VELOCITY =
			new InterpolationTable(new Vector2[] {
					new Vector2(0, 0.0069),
					new Vector2(500, 0.00398),
					new Vector2(1000, 0.02235),
					new Vector2(1500, 0.001375),
					new Vector2(1750, 0.0011),
					new Vector2(2300, 0.00088)
					}
			);
	
    /** 
     * Used to find the cars acceleration from its current velocity.
     * Each Vector2 represents a point where 'x = velocity' and 'y = acceleration'.
     * @see https://samuelpmish.github.io/notes/RocketLeague/ground_control/
     */ 
	public static final InterpolationTable THROTTLE_ACCELERATION_BY_VELOCITY =
			new InterpolationTable(new Vector2[] {
					new Vector2(0, 1600.0),
					new Vector2(1400, 160.0),
					new Vector2(1410, 0.0),
					new Vector2(2300, 0.0)
					}
			);
	
	public final Hitbox hitbox = Hitbox.OCTANE;
    public final double boost;
    public final boolean hasWheelContact;
    public final boolean isUpright;
    public final boolean isSupersonic;
    public final int team; // 0 for blue team, 1 for orange team.

    public Car(PlayerInfo playerInfo, double time) {
    	super(playerInfo.physics(), time);
        this.boost = playerInfo.boost();
        this.isSupersonic = playerInfo.isSupersonic();
        this.team = playerInfo.team();
        this.hasWheelContact = playerInfo.hasWheelContact();
        this.isUpright = orientation.up.dot(Vector3.UP) > 0.5;
    }
    
    public Car(Kinematics instant, double boost, boolean hasWheelContact, 
    		boolean isUpright, boolean isSupersonic, int team) {
    	super(instant);
		this.boost = boost;
		this.hasWheelContact = hasWheelContact;
		this.isUpright = isUpright;
		this.isSupersonic = isSupersonic;
		this.team = team;
	}

    public double turnRadius() {
    	return 1 / turningCurvature();
    } 
    
    public double turningCurvature() {
    	return CURVATURE_BY_VELOCITY.getInterpolatedY(speed());
    }
    
    public double throttleAcceleration() {
    	return THROTTLE_ACCELERATION_BY_VELOCITY.getInterpolatedY(speed());
    }
    
    public boolean isOnWallOrCeiling() {
    	return hasWheelContact && !isUpright;
    }
    
}
