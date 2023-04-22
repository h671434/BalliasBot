package balliasbot.state;

import balliasbot.controls.ControlsOutput;
import balliasbot.data.DataPacket;

public class ChaseBall extends GoTo {
	
	private double startTime = -1;
	
	private double lastDodgeTime = 0;
	
	@Override
	public boolean isViable(DataPacket data) {
		return true;
	}
	
	@Override
	public ControlsOutput exec(DataPacket data) {
		if(limitStateDuration(data.currentTime)) {
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
