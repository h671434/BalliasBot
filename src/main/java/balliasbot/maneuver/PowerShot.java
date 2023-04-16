package balliasbot.maneuver;

import balliasbot.controls.ControlsOutput;
import balliasbot.data.DataPacket;
import balliasbot.prediction.PredictionData;

public class PowerShot implements Maneuver {

	private PredictionData ballPrediction;
	private boolean done = false;
	
	public PowerShot(PredictionData ballPrediction) {
		this.ballPrediction = ballPrediction;
	}
	
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
