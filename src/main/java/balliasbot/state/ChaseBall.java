package balliasbot.state;

import balliasbot.controls.AerialControls;
import balliasbot.controls.ControlsOutput;
import balliasbot.controls.DriveControls;
import balliasbot.data.DataPacket;
import balliasbot.maneuver.AirRecovery;
import balliasbot.maneuver.Dodge;
import balliasbot.maneuver.Maneuver;
import balliasbot.math.Vector3;

public class ChaseBall extends GoTo {
	
	private double startTime = -1;
	
	private double lastDodgeTime = 0;
	
	@Override
	public boolean isViable(DataPacket data) {
		return true;
	}
	
	@Override
	public ControlsOutput exec(DataPacket data) {
		if(maneuverIsActive()) {
			return execManeuver(data);
		}
		if(limitStateDuration(data.elapsedSeconds)) {
			return null;
		}
		
		target = data.ball.position;
		targetSpeed = 1200;
		
		onGoingManeuver = checkForViableManeuvers(data);
		
		return super.exec(data);
	}
	
	private Maneuver checkForViableManeuvers(DataPacket data) {
		if(!data.car.hasWheelContact) {
			return new AirRecovery(target);
		} 
		
		if(data.car.position.distance(target.flatten()) > 500
				&& data.elapsedSeconds - lastDodgeTime > 5
				&& data.car.orientation.forward.dotProduct(target.normalized()) < 0.2) {
			lastDodgeTime = data.elapsedSeconds;
			
			return new Dodge(data, target);
		}
		
		return null;
	}

	private boolean limitStateDuration(double currentTime) {
		if(startTime == -1) {
			startTime = currentTime;
		} else if(startTime + 2 < currentTime) {
			return true;
		}
		
		return false;
	}
	
}
