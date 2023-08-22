package balliasbot.controls;

import balliasbot.data.Car;
import balliasbot.math.Vector3;

public class DriveUtils {

	public static void controlSpeed(ControlsOutput controls, Car car, Vector3 targetPosition, double targetSpeed) {
		Vector3 carVelocity =  car.velocity;
        Vector3 carToTarget = car.pointingTo(targetPosition);
        Vector3 direction = carToTarget.normalized();
        
        double currentSpeed = carVelocity.dot(direction);
        
        if (shouldBoost(currentSpeed, targetSpeed)) {
            controls.withBoost(true);
         }
        
        if (currentSpeed < targetSpeed) {
            controls.withThrottle(1.0f);
            
            return;
        } 
        
    	double extraSpeed = currentSpeed - targetSpeed;
    	
    	controls.withThrottle(0.3 - extraSpeed / 500);
	}
	
	private static boolean shouldBoost(double currentSpeed, double targetSpeed) {
		return (targetSpeed > 1410)	&& (currentSpeed + 60 < targetSpeed);
	}

	public static void controlSteer(ControlsOutput controls, Car car, Vector3 targetPosition) {
        Vector3 localTarget = car.inLocalCoordinates(targetPosition); 

        double angle;
        double rate;
        
		if(car.isOnWallOrCeiling()) {
			angle = Math.atan2(localTarget.y, localTarget.x);
			rate = -car.angularVelocity.z * 0.01;
		} else {
			angle = Math.atan2(localTarget.y, localTarget.z);
			rate = -car.angularVelocity.x * 0.01;
		}
		
		controls.withSteer(steerPD(angle, rate));
	}
	
	private static double steerPD(double angle, double rate){
		return Math.pow(35 * (angle + rate), 3) / 10;
	}
	
}
