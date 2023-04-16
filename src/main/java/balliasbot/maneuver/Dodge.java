package balliasbot.maneuver;

import balliasbot.controls.ControlsOutput;
import balliasbot.data.DataPacket;
import balliasbot.math.Vector3;

public class Dodge implements Maneuver {

	private Vector3 target;
	private double startTime;
	private boolean done = false;
	
	private double firstJumpDur;
	private double postFirstJumpPauseDur;
	private double aimDur;
	private double secondJumpDur;
	private double postSecondJumpPauseDur;
	
	public Dodge(DataPacket data, Vector3 target) {
        this.target = target;
		this.startTime = data.elapsedSeconds;
		
		// TODO Calculate appropriate timing
		firstJumpDur = 0.08;
		postFirstJumpPauseDur = 0.0;
		aimDur = 0.18;
		secondJumpDur = 0.20;
		postSecondJumpPauseDur = 0.14;
	}
	
	@Override
	public ControlsOutput exec(DataPacket data) {
		
		double t_firstJumpEnd = firstJumpDur;
		double t_aimBegin = t_firstJumpEnd + postFirstJumpPauseDur;
		double t_secondJumpBegin = t_aimBegin + aimDur;
		double t_secondJumpEnd = t_secondJumpBegin + secondJumpDur;
		double t_dodgeEnd = t_secondJumpEnd + postSecondJumpPauseDur;
		
		double currentTime = data.elapsedSeconds - startTime;
		
		ControlsOutput controls = new ControlsOutput();
		
		if (currentTime < t_firstJumpEnd) {
			controls.withJump(true);
			controls.withThrottle(1.0);
			
		} else if (t_firstJumpEnd <= currentTime && currentTime < t_aimBegin) {
			controls.withThrottle(1.0);
			
        } else if (t_aimBegin <= currentTime && currentTime < t_secondJumpEnd) {
    		controls.withThrottle(1.0);
        	
            if (target == null) {
                controls.withPitch(-1.0);
            } else {
                Vector3 localTarget = data.car.local(target);
                Vector3 direction = localTarget.withZ(0).normalized();

                controls.withPitch(-direction.x);
                controls.withYaw(Math.signum(data.car.orientation.up.z) * direction.y);
                controls.withRoll(-Math.signum(data.car.orientation.up.z) * direction.y);
            }

            if (currentTime >= t_secondJumpBegin) {
                controls.withJump(true);
            }
			
		} else if (t_secondJumpEnd <= currentTime && currentTime < t_dodgeEnd) {
			//Wait 
			
		} else if (currentTime >= t_dodgeEnd + 0.5){
			done = true;
		}
		
		return controls;
	}

	@Override
	public boolean done() {
		return done;
	}
}
