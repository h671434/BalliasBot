package balliasbot.data;

import balliasbot.math.Vector3;

public class CarData {
    public final Vector3 position;
    public final Vector3 velocity;
    public final CarOrientation orientation;
    public final Vector3 angularVelocity;
    public final double boost;
    public final boolean hasWheelContact;
    public final boolean isUpright;
    public final boolean isSupersonic;
    public final int team; // 0 for blue team, 1 for orange team.

    public CarData(rlbot.flat.PlayerInfo playerInfo) {
        this.position = new Vector3(playerInfo.physics().location());
        this.velocity = new Vector3(playerInfo.physics().velocity());
        this.orientation = CarOrientation.fromFlatbuffer(playerInfo);
        this.angularVelocity = new Vector3(playerInfo.physics().angularVelocity());
        this.boost = playerInfo.boost();
        this.isSupersonic = playerInfo.isSupersonic();
        this.team = playerInfo.team();
        this.hasWheelContact = playerInfo.hasWheelContact();
        this.isUpright = orientation.up.dotProduct(Vector3.UP) > 0.5;
    }
    
    /**
     * Returns vector pointing from car to target.
     */
    public Vector3 carTo(Vector3 target) {
    	return target.minus(position);
    }
    
    /**
     * Returns the target position relative to the local orientation.
     */
    public Vector3 local(Vector3 from, Vector3 target) {
    	Vector3 carToTarget = target.minus(from);
    	
    	return new Vector3(
    			orientation.forward.dotProduct(carToTarget),
    			orientation.right.dotProduct(carToTarget),
    			orientation.up.dotProduct(carToTarget));
    }
    
    public Vector3 local(Vector3 target) {
    	return local(position, target);
    }
    
}
