package balliasbot.math;

public class Mat3x3 {

    public final Vec3 forward;
    public final Vec3 right;
    public final Vec3 up; // (0, 0, 1) means the car is upright.

    public Mat3x3(Vec3 forward, Vec3 right, Vec3 up) {
        this.forward = forward;
        this.right = right;
        this.up = up;
    }
    
    public Mat3x3(Vec3 forward, Vec3 up) {
    	this(forward, forward.crossProduct(up), up);
    }
    
    public Vec3 dotProduct(Vec3 other) {
    	return new Vec3(
    			forward.dotProduct(other),
    			right.dotProduct(other),
    			up.dotProduct(other));
    }
    
    public Vec3 local(Vec3 direction) {
    	return dotProduct(direction);
    }

    public static Mat3x3 eulerToRotation(double pitch, double yaw, double roll) {
        double noseX = -1 * Math.cos(pitch) * Math.cos(yaw);
        double noseY = Math.cos(pitch) * Math.sin(yaw);
        double noseZ = Math.sin(pitch);

        double roofX = Math.cos(roll) * Math.sin(pitch) * Math.cos(yaw) + Math.sin(roll) * Math.sin(yaw);
        double roofY = Math.cos(yaw) * Math.sin(roll) - Math.cos(roll) * Math.sin(pitch) * Math.sin(yaw);
        double roofZ = Math.cos(roll) * Math.cos(pitch);

        return new Mat3x3(new Vec3(noseX, noseY, noseZ), new Vec3(roofX, roofY, roofZ));
    }
    
}
