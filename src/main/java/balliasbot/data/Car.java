package balliasbot.data;

import balliasbot.math.Mat3x3;
import balliasbot.math.Vec3;
import rlbot.flat.PlayerInfo;

public class Car {
    public final Vec3 position;
    public final Vec3 velocity;
    public final Mat3x3 orientation;
    public final Vec3 angularVelocity;
    public final double boost;
    public final boolean hasWheelContact;
    public final boolean isUpright;
    public final boolean isSupersonic;
    public final int team; // 0 for blue team, 1 for orange team.
    public final double time;

    public Car(PlayerInfo playerInfo, double time) {
        this.position = new Vec3(playerInfo.physics().location());
        this.velocity = new Vec3(playerInfo.physics().velocity());
        this.orientation = Mat3x3.eulerToRotation(
                playerInfo.physics().rotation().pitch(),
                playerInfo.physics().rotation().yaw(),
                playerInfo.physics().rotation().roll());;
        this.angularVelocity = new Vec3(playerInfo.physics().angularVelocity());
        this.boost = playerInfo.boost();
        this.isSupersonic = playerInfo.isSupersonic();
        this.team = playerInfo.team();
        this.hasWheelContact = playerInfo.hasWheelContact();
        this.isUpright = orientation.up.dotProduct(Vec3.UP) > 0.5;
        this.time = time;
    }
    
    public Car(Vec3 position, Vec3 velocity, Mat3x3 orientation, 
    		Vec3 angularVelocity, double boost, boolean hasWheelContact, 
    		boolean isUpright, boolean isSupersonic, int team, double time) {
		this.position = position;
		this.velocity = velocity;
		this.orientation = orientation;
		this.angularVelocity = angularVelocity;
		this.boost = boost;
		this.hasWheelContact = hasWheelContact;
		this.isUpright = isUpright;
		this.isSupersonic = isSupersonic;
		this.team = team;
		this.time = time;
	}

	public Vec3 getVectorTo(Vec3 vec) {
    	return vec.minus(position);
    }
    
    public Vec3 getLocalCoordinates(Vec3 vec) {
    	Vec3 direction = getVectorTo(vec);
    	
    	return orientation.local(direction);
    }
    
}
