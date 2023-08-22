package balliasbot.state;

import java.awt.Color;

import balliasbot.controls.ControlsOutput;
import balliasbot.controls.DriveControls;
import balliasbot.data.Ball;
import balliasbot.data.DataPacket;
import balliasbot.math.Vector3;

public class ChaseBall extends GoTo {
	
	private final double startTime;
	
	public ChaseBall(DataPacket data) {
		super(data.ball.position);
		this.startTime = data.currentTime;
	}
	
	@Override
	public boolean isViable(DataPacket data) {
		return true;
	}
	
	@Override
	public ControlsOutput exec(DataPacket data) {
		if(shouldEndState(data.currentTime)) {
			return null;
		}
		
		targetPosition = data.ball.position;
		targetSpeed = 1400;
		
		return super.exec(data);
	}
	
	private boolean shouldEndState(double currentTime) {
		return startTime + 5 < currentTime && !hasActiveManeuver();
	}
	
}
