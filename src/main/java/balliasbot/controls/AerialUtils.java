package balliasbot.controls;

import balliasbot.data.Car;
import balliasbot.data.DataPacket;
import balliasbot.math.RotationMatrix;
import balliasbot.math.Vector3;

public class AerialUtils {
	
	public static final double MAX_ANGULAR_VELOCITY = 5.5;
	public static final Vector3 ANGULAR_ACCELERATION = new Vector3(9.11, 12.46, 37.34); // pitch, yaw, roll
	public static final Vector3 ANGULAR_TORQUE = new Vector3(-12.15, 8.92, -36.08);
	public static final Vector3 ANGULAR_DRAG = new Vector3(-2.80, -1.89, -4.47); 
	
	public static ControlsOutput align(Car car, Vector3 localTargetForward, Vector3 localTargetUp) {
		RotationMatrix orientation = car.orientation;
		Vector3 angularVelocity = car.angularVelocity;
		Vector3 localAngVel = orientation.dot(angularVelocity);
		
		Vector3 targetAngles = new Vector3(
				Math.atan2(localTargetForward.z, localTargetForward.x),
				Math.atan2(localTargetForward.y, localTargetForward.x),
				Math.atan2(localTargetUp.x, localTargetUp.z));
		
		ControlsOutput controls = new ControlsOutput();
		
		controls.withThrottle(1);
		controls.withSteer(pitchYawPD(targetAngles.y , 0));
		controls.withPitch(pitchYawPD(targetAngles.x, localAngVel.y * 0.2));
		controls.withYaw(-pitchYawPD(targetAngles.y, -localAngVel.z * 0.15));
		controls.withRoll(rollPD(targetAngles.z, localAngVel.x));
		
		return controls;
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
		
		double roll = Math.pow(angleNorm + (Math.signum(angleNorm - angVelNorm) * Tr + Dr) 
				* angVelNorm * deltaTime , 3) * 10;
		
		return roll;
	}
	
}
