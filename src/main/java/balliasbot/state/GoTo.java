package balliasbot.state;

import balliasbot.controls.ControlsOutput;
import balliasbot.controls.DriveControls;
import balliasbot.data.DataPacket;
import balliasbot.maneuver.Maneuver;
import balliasbot.math.Vector3;

public class GoTo extends State {
	
	protected Vector3 target = Vector3.ZERO;
	protected double targetSpeed = 1400;
	
	protected Maneuver onGoingManeuver;
	
	@Override
	public boolean isViable(DataPacket data) {
		return false;
	}
	
	@Override
	public ControlsOutput exec(DataPacket data) {
		return new DriveControls(data, target, targetSpeed);
	}
	
	protected boolean maneuverIsActive() {
		if(onGoingManeuver != null) {
			if(onGoingManeuver.done()) {
				onGoingManeuver = null;
			} else {
				return true;
			}
		}
		
		return false;
	}
	
	protected ControlsOutput execManeuver(DataPacket data) {
		return onGoingManeuver.exec(data);
	}
	
}
