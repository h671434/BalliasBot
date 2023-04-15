package balliasbot.state;

import balliasbot.controls.ControlsOutput;
import balliasbot.data.DataPacket;

public abstract class State {

	public abstract ControlsOutput exec(DataPacket data);
	
	public abstract boolean isViable(DataPacket data);
	
}
