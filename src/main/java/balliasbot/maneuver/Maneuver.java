package balliasbot.maneuver;

import balliasbot.controls.ControlsOutput;
import balliasbot.data.DataPacket;

public interface Maneuver {

	ControlsOutput exec(DataPacket data);
	
	boolean done();
	
}
