package balliasbot.state;

import balliasbot.controls.AerialControls;
import balliasbot.controls.ControlsOutput;
import balliasbot.controls.DriveControls;
import balliasbot.data.DataPacket;
import balliasbot.maneuver.Maneuver;
import balliasbot.math.Vec3;

public class ChaseBall extends GoTo {
	
	private double startTime = -1;
	
	private double lastDodgeTime = 0;
	
	@Override
	public boolean isViable(DataPacket data) {
		return true;
	}
	
	@Override
	public ControlsOutput exec(DataPacket data) {
		if(limitStateDuration(data.elapsedSeconds)) {
			return null;
		}
		
		target = data.ball.position;
		targetSpeed = 1200;
		
		return super.exec(data);
	}
	
	private boolean limitStateDuration(double currentTime) {
		if(startTime == -1) {
			startTime = currentTime;
		} else if(startTime + 5 < currentTime) {
			return true;
		}
		
		return false;
	}
	
}
