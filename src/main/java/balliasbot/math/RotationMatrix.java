package balliasbot.math;

/**
 * A 3x3 rotation matrix.
 */
public class RotationMatrix extends Matrix {

    public final Vector3 forward;
    public final Vector3 right;
    public final Vector3 up; // (0, 0, 1) means the car is upright.

    public RotationMatrix(Vector3 forward, Vector3 right, Vector3 up) {
    	super(new double[][] {
    		{forward.x, forward.y, forward.z},
    		{right.x, right.y, right.z},
    		{up.x, right.y, right.z}});
        this.forward = forward;
        this.right = right;
        this.up = up;
    }
    
    public RotationMatrix(Vector3 forward, Vector3 up) {
    	this(forward, forward.crossProduct(up), up);
    }
    
    public Vector3 dot(Vector3 other) {
    	return new Vector3(
    			forward.dot(other),
    			right.dot(other),
    			up.dot(other));
    }
    
    public RotationMatrix transpose(){
		return new RotationMatrix(
			new Vector3(forward.x, right.x, up.x),
			new Vector3(forward.y, right.y, up.y),
			new Vector3(forward.z, right.z, up.z));
	}
    
    public Vector3 local(Vector3 direction) {
    	return dot(direction);
    }

    public static RotationMatrix eulerToRotation(double pitch, double yaw, double roll) {
        double noseX = -1 * Math.cos(pitch) * Math.cos(yaw);
        double noseY = Math.cos(pitch) * Math.sin(yaw);
        double noseZ = Math.sin(pitch);

        double roofX = Math.cos(roll) * Math.sin(pitch) * Math.cos(yaw) + Math.sin(roll) * Math.sin(yaw);
        double roofY = Math.cos(yaw) * Math.sin(roll) - Math.cos(roll) * Math.sin(pitch) * Math.sin(yaw);
        double roofZ = Math.cos(roll) * Math.cos(pitch);

        return new RotationMatrix(new Vector3(noseX, noseY, noseZ), new Vector3(roofX, roofY, roofZ));
    }
    
}
