package balliasbot.controls;

import balliasbot.data.DataPacket;
import balliasbot.math.Vector3;

public class DriveControls extends ControlsOutput {

	private DataPacket data;
	private	Vector3 target;
	private double targetSpeed;
	
	public DriveControls(DataPacket data, Vector3 target, double targetSpeed) {
		this.data = data;
		this.target = target;
		this.targetSpeed = targetSpeed;
		
		initControls();
	}
	
	private void initControls() {
		initSteerControls();
		initSpeedControls();
	}
	
	private void initSpeedControls() {
		Vector3 carVelocity =  data.car.velocity;
        Vector3 carToTarget = target.minus(data.car.position);
        Vector3 carToTargetNorm = carToTarget.normalized();
        
        double currentSpeed = carVelocity.dotProduct(carToTargetNorm);
        
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
        Vector3 localTarget = data.car.local(target, data.car.position);
        
		if(data.car.hasWheelContact && !data.car.isUpright) {
			withSteer(localTarget.normalized().x * 5);
		} else {
			withSteer(-localTarget.normalized().y * 5);
		}
			
	}
}
