package balliasbot.maneuver;

import balliasbot.controls.ControlsOutput;
import balliasbot.controls.DriveUtils;
import balliasbot.data.DataPacket;
import balliasbot.math.Vector3;

public class Drive implements Maneuver {

	private Vector3 targetPosition;
	private double targetSpeed;
	
	private boolean done = false;
	
	public Drive(Vector3 targetPosition, double targetSpeed) {
		this.targetPosition = targetPosition;
		this.targetSpeed = targetSpeed;
	}
	
	@Override
	public ControlsOutput exec(DataPacket data) {
		ControlsOutput controls = new ControlsOutput();
		
		DriveUtils.controlSpeed(controls, data.car, targetPosition, targetSpeed);
		DriveUtils.controlSteer(controls, data.car, targetPosition);
		
		done = true;
		
		return controls;
	}

	@Override
	public boolean done() {
		return done;
	}

}
