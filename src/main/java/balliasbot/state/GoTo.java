package balliasbot.state;

import balliasbot.controls.ControlsOutput;
import balliasbot.controls.DriveControls;
import balliasbot.data.DataPacket;
import balliasbot.math.Vec3;

public class GoTo extends State {
	
	protected Vec3 target = Vec3.ZERO;
	protected double targetSpeed = 1400;
	
	@Override
	public boolean isViable(DataPacket data) {
		return false;
	}
	
	@Override
	public ControlsOutput exec(DataPacket data) {
		return new DriveControls(data.car, target, targetSpeed);
	}
	
}
