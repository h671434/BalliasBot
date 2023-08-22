package balliasbot.maneuver;

import balliasbot.controls.ControlsOutput;
import balliasbot.data.DataPacket;
import balliasbot.state.State;

public class HalfFlipTestState extends State {

	@Override
	public boolean isViable(DataPacket data) {
		return true;
	}

	@Override
	public ControlsOutput exec(DataPacket data) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
