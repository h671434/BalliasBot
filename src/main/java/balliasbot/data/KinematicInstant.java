package balliasbot.data;

import balliasbot.math.Mat3x3;
import balliasbot.math.Vec3;
import rlbot.flat.Physics;

public class KinematicInstant {

	public final Vec3 position;
	public final Mat3x3 orientation;
	public final Vec3 velocity;
	public final Vec3 angularVelocity;
	public final double time;
	
	public KinematicInstant(Physics physics, double time) {
        this.position = new Vec3(physics.location());
        this.velocity = new Vec3(physics.velocity());
        this.orientation = Mat3x3.eulerToRotation(
                physics.rotation().pitch(),
                physics.rotation().yaw(),
                physics.rotation().roll());
        this.angularVelocity = new Vec3(physics.angularVelocity());
        this.time = time;
	}
	
	public KinematicInstant(Vec3 position, Mat3x3 orientation, Vec3 velocity, 
			Vec3 angularVelocity, double time) {
		this.position = position;
		this.orientation = orientation;
		this.velocity = velocity;
		this.angularVelocity = angularVelocity;
		this.time = time;
	}
	
	public KinematicInstant(KinematicInstant instant) {
		this(instant.position, 
				instant.orientation,
				instant.velocity, 
				instant.angularVelocity,
				instant.time);
	}
	
	public Vec3 positionToPoint(Vec3 vec) {
    	return vec.minus(position);
    }
    
    public Vec3 inLocalCoordinates(Vec3 vec) {
    	Vec3 direction = positionToPoint(vec);
    	
    	return orientation.local(direction);
    }
    
    public double speed() {
    	return velocity.magnitude();
    }
	
}
