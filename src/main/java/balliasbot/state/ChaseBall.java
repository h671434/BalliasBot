package balliasbot.state;

import balliasbot.controls.AerialControls;
import balliasbot.controls.ControlsOutput;
import balliasbot.controls.DriveControls;
import balliasbot.data.DataPacket;
import balliasbot.math.Vector3;

public class ChaseBall extends State{
	
	@Override
	public ControlsOutput exec(DataPacket data) {
		Vector3 target = data.ball.position;
		double targetSpeed = 1200;
		
		if (!data.car.hasWheelContact) {
			return AerialControls.recover(data, null);
		}
		
		return new DriveControls(data, target, targetSpeed);
	}
	
	@Override
	public boolean isViable(DataPacket data) {
		return true;
	}
	
}
