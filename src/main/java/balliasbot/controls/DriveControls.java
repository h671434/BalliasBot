package balliasbot.controls;

import balliasbot.data.Car;
import balliasbot.data.DataPacket;
import balliasbot.math.Vec3;

public class DriveControls extends ControlsOutput {

	private Car car;
	private	Vec3 target;
	private double targetSpeed;
	
	public DriveControls(Car car, Vec3 target, double targetSpeed) {
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
		Vec3 carVelocity =  car.velocity;
        Vec3 carToTarget = car.getVectorTo(target);
        Vec3 direction = carToTarget.normalized();
        
        double currentSpeed = carVelocity.dotProduct(direction);
        
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
        Vec3 localTarget = car.getLocalCoordinates(target);
        
		if(car.hasWheelContact && !car.isUpright) {
			withSteer(localTarget.normalized().x * 5);
		} else {
			withSteer(localTarget.normalized().y * 5);
		}
			
	}
}
