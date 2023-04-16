package balliasbot.maneuver;

import balliasbot.controls.AerialControls;
import balliasbot.controls.ControlsOutput;
import balliasbot.data.DataPacket;
import balliasbot.math.Vector3;

public class AirRecovery implements Maneuver {

	private Vector3 target;
	private boolean done = false;

	public AirRecovery() {};
	
	public AirRecovery(Vector3 target) {
		this.target = target;
	}
	
	@Override
	public ControlsOutput exec(DataPacket data) {
		if(data.car.hasWheelContact) {
			done = true;
		}
		
		if (target == null) {
			try {
				Vector3 velocity = data.car.velocity;
				Vector3 uprightVelocityDirection = 
						velocity.flatten(Vector3.UP).normalized();
				
				target = data.car.orientation.dotProduct(uprightVelocityDirection);
				
			} catch (IllegalStateException e) {
				Vector3 uprightCarToBall = 
						data.car.carTo(data.ball.position).flatten(Vector3.UP);
				
				target = data.car.orientation.dotProduct(uprightCarToBall);
			}
		}
		
		Vector3 localUp = data.car.local(Vector3.UP);
		
		return new AerialControls(data, target, localUp);
	}

	@Override
	public boolean done() {
		return done;
	}

}
