package balliasbot.state;

import java.awt.Color;

import balliasbot.controls.ControlsOutput;
import balliasbot.controls.DriveControls;
import balliasbot.data.DataPacket;
import balliasbot.math.Vector3;

public class ChaseBall extends State {
	
	private double startTime = -1;
	
	@Override
	public boolean isViable(DataPacket data) {
		return true;
	}
	
	@Override
	public ControlsOutput exec(DataPacket data) {
		
		if(limitStateDuration(data.currentTime)) {
			return null;
		}
		
		Vector3 targetPosition = data.ball.position;
		double targetSpeed = 1400;

		data.bot.renderer.drawLine3d(Color.RED, data.car.position, targetPosition);
		
		return new DriveControls(data.car, targetPosition, targetSpeed) ;
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
