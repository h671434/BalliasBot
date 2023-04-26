package balliasbot.controls;

import balliasbot.data.Car;
import balliasbot.math.Vector3;

public class DriveControls extends ControlsOutput {

	private Car car;
	private	Vector3 target;
	private double targetSpeed;
	
	public DriveControls(Car car, Vector3 target, double targetSpeed) {
		this.car = car;
		this.target = target;
		this.targetSpeed = targetSpeed;
		
		initControls();
	}
	
	private void initControls() {
		initSteerControls();
		initSpeedControls();
	}
	
	private void initSpeedControls() {
		Vector3 carVelocity =  car.velocity;
        Vector3 carToTarget = car.pointingTo(target);
        Vector3 direction = carToTarget.normalized();
        
        double currentSpeed = carVelocity.dot(direction);
        
        if (currentSpeed < targetSpeed) {
            withThrottle(1.0f);
            
            if (targetSpeed > 1410 
            		&& currentSpeed + 60 < targetSpeed) {
               withBoost(true);
            }
        } else {
        	double extraSpeed = currentSpeed - targetSpeed;
        	
        	withThrottle(0.3 - extraSpeed / 500);
        }
	}

	private void initSteerControls() {
        Vector3 localTarget = car.inLocalCoordinates(target); 

		if(car.hasWheelContact && !car.isUpright) {
			withSteer(steerPD(
					Math.atan2(localTarget.y, localTarget.x), 
					-car.angularVelocity.z * 0.01));
		} else {
			withSteer(steerPD(
					Math.atan2(localTarget.y, localTarget.z), 
					-car.angularVelocity.x * 0.01));
		}
			
	}
	
	private static double steerPD(double angle, double rate){
		return Math.pow(35 * (angle + rate), 3) / 10;
	}
	
}
