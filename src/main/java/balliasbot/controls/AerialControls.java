package balliasbot.controls;

import balliasbot.data.DataPacket;
import balliasbot.math.Mat3x3;
import balliasbot.math.Vec3;

public class AerialControls extends ControlsOutput {

	public static final double MAX_ANGULAR_VELOCITY = 5.5;
	public static final Vec3 ANGULAR_ACCELERATION = new Vec3(9.11, 12.46, 37.34);
	public static final Vec3 ANGULAR_TORQUE = new Vec3(-12.15, 8.92, -36.08);
	public static final Vec3 ANGULAR_DRAG = new Vec3(-2.80, -1.89, -4.47); 
	
	private DataPacket data;
	private Vec3 localTarget;
	private Vec3 localUp;
	
	public AerialControls(DataPacket data,  Vec3 localTarget, Vec3 localUp) {
		this.data = data;
		this.localTarget = localTarget;
		this.localUp = localUp;
		initControls();
	}
	
	private void initControls() {
		align();
	}
	
	private void align() {
		Mat3x3 orientation = data.car.orientation;
		Vec3 angularVelocity = data.car.angularVelocity;
		Vec3 localAngVel = orientation.dotProduct(angularVelocity);
		
		Vec3 targetAngles = new Vec3(
				Math.atan2(localTarget.z, localTarget.x),
				Math.atan2(localTarget.y, localTarget.x),
				Math.atan2(localUp.x, localUp.z));
			
		double steer = pitchYawPD(targetAngles.y , 0); 
		double pitch = pitchYawPD(targetAngles.x, localAngVel.y * 0.2);
		double yaw = pitchYawPD(targetAngles.y, -localAngVel.z * 0.15);
		double roll = rollPD(targetAngles.z, localAngVel.x);
		
		withThrottle(1);
		withSteer(steer);
		withPitch(pitch);
		withYaw(-yaw);
		withRoll(roll);
	}
	
	private static double pitchYawPD(double angle, double rate) {
		return (Math.pow(35*(angle+rate), 3)) / 10;
	}
	
	private static double rollPD(double angle, double angVelx) {
		double angVelNorm = angVelx / 5.5;
		double angleNorm = angle / (Math.PI);
		double deltaTime = DataPacket.DELTA_TIME;
		
		double Dr = ANGULAR_DRAG.z;
		double Tr = ANGULAR_TORQUE.z;
		
		double roll = Math.pow(
				angleNorm + 
				(Math.signum(angleNorm - angVelNorm) * Tr + Dr) 
				* angVelNorm * deltaTime , 3) * 10;
		
		return roll;
	}
	
}
