package balliasbot.maneuver;

import balliasbot.controls.ControlsOutput;
import balliasbot.data.DataPacket;

public class SpeedFlip implements Maneuver {

	private boolean done = false;
	
	@Override
	public ControlsOutput exec(DataPacket data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean done() {
		return done;
	}
	
}
