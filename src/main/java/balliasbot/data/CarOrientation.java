package balliasbot.data;


import balliasbot.math.Vector3;
import rlbot.flat.PlayerInfo;

public class CarOrientation {

    public final Vector3 forward;
    public final Vector3 up; // (0, 0, 1) means the car is upright.
    public final Vector3 right;

    public CarOrientation(Vector3 noseVector, Vector3 roofVector) {
        this.forward = noseVector;
        this.up = roofVector;
        this.right = noseVector.crossProduct(roofVector);
    }
    
    public Vector3 dotProduct(Vector3 other) {
    	return new Vector3(
    			forward.dotProduct(other),
    			up.dotProduct(other),
    			right.dotProduct(other));
    }

    public static CarOrientation fromFlatbuffer(PlayerInfo playerInfo) {
        return convert(
                playerInfo.physics().rotation().pitch(),
                playerInfo.physics().rotation().yaw(),
                playerInfo.physics().rotation().roll());
    }

    /**
     * All params are in radians.
     */
    private static CarOrientation convert(double pitch, double yaw, double roll) {

        double noseX = -1 * Math.cos(pitch) * Math.cos(yaw);
        double noseY = Math.cos(pitch) * Math.sin(yaw);
        double noseZ = Math.sin(pitch);

        double roofX = Math.cos(roll) * Math.sin(pitch) * Math.cos(yaw) + Math.sin(roll) * Math.sin(yaw);
        double roofY = Math.cos(yaw) * Math.sin(roll) - Math.cos(roll) * Math.sin(pitch) * Math.sin(yaw);
        double roofZ = Math.cos(roll) * Math.cos(pitch);

        return new CarOrientation(new Vector3(noseX, noseY, noseZ), new Vector3(roofX, roofY, roofZ));
    }
    
}
