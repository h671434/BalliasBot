package balliasbot.data;

import balliasbot.math.RotationMatrix;
import balliasbot.math.Vector3;
import rlbot.flat.Physics;

public class Kinematics {

	public final Vector3 position;
	public final RotationMatrix orientation;
	public final Vector3 velocity;
	public final Vector3 angularVelocity;
	public final double time;
	
	public Kinematics(Physics physics, double time) {
        this.position = new Vector3(physics.location());
        this.velocity = new Vector3(physics.velocity());
        this.orientation = RotationMatrix.eulerToRotation(
                physics.rotation().pitch(),
                physics.rotation().yaw(),
                physics.rotation().roll());
        this.angularVelocity = new Vector3(physics.angularVelocity());
        this.time = time;
	}
	
	public Kinematics(Vector3 position, RotationMatrix orientation, Vector3 velocity, 
			Vector3 angularVelocity, double time) {
		this.position = position;
		this.orientation = orientation;
		this.velocity = velocity;
		this.angularVelocity = angularVelocity;
		this.time = time;
	}
	
	public Kinematics(Kinematics instant) {
		this(instant.position, 
				instant.orientation,
				instant.velocity, 
				instant.angularVelocity,
				instant.time);
	}
	
	public Vector3 pointingTo(Vector3 vec) {
    	return vec.minus(position);
    }
    
	public Vector3 pointingTo(Kinematics other) {
    	return pointingTo(other.position);
    }
	
    public Vector3 inLocalCoordinates(Vector3 vec) {
    	Vector3 direction = pointingTo(vec);
    	
    	return orientation.local(direction);
    }
    
    public Vector3 inLocalCoordinates(Kinematics other) {
    	return inLocalCoordinates(other.position);
    }
    
    public double speed() {
    	return velocity.magnitude();
    }
	
}
