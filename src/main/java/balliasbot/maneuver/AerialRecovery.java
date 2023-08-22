package balliasbot.maneuver;

import balliasbot.controls.AerialUtils;
import balliasbot.controls.ControlsOutput;
import balliasbot.data.DataPacket;
import balliasbot.math.Vector3;

public class AerialRecovery implements Maneuver {
	
	private boolean done = false;
	
	@Override
	public ControlsOutput exec(DataPacket data) {
		if(data.car.hasWheelContact) {
			done = true;
		}
		
		Vector3 velocityDirection = data.car.velocity.normalized().flatten();
		Vector3 localTarget = data.car.inLocalCoordinates(velocityDirection);
		Vector3 localUp = data.car.inLocalCoordinates(Vector3.UP);
		
		ControlsOutput controls = AerialUtils.align(data.car, localTarget, localUp);
		
		controls.withSlide(true);
		
		return controls;
	}

	@Override
	public boolean done() {
		return done;
	}

}
