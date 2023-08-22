package balliasbot.maneuver;

import balliasbot.controls.AerialUtils;
import balliasbot.controls.ControlsOutput;
import balliasbot.data.Car;
import balliasbot.data.DataPacket;
import balliasbot.math.Vector3;

public class QuickFlip implements Maneuver {

	private final double startTime;
	private Vector3 target;
	
	private double firstJumpDuration = 0.1;
	private double betweenJumpsDuration = 0.1;
	private double secondJumpDuration = 0.1;
	private double flipCancelDuration = 0.55;
	
	private boolean done;
	
	public QuickFlip(DataPacket data, Vector3 target) {
		this.startTime = data.currentTime;
		this.target = target;
	}
	
	@Override
	public ControlsOutput exec(DataPacket data) {
		double currentTime = startTime - data.currentTime;
		
		double firstJumpEnd = firstJumpDuration;
		double betweenJumpsEnd = firstJumpEnd + betweenJumpsDuration;
		double secondJumpEnd = betweenJumpsEnd + secondJumpDuration;
		double flipCancelEnd = secondJumpEnd + flipCancelDuration;
		
		if(currentTime <= firstJumpEnd) {
			return firstJump();
		}
		if(currentTime <= betweenJumpsEnd) {
			return betweenJumps(data.car);
		}
		if(currentTime <= secondJumpEnd) {
			return secondJump(data.car);
		}
		if(currentTime <= flipCancelEnd) {
			return flipCancel();
		}
		
		if(data.car.hasWheelContact) {
			done = true;
		}
		
		return landing(data.car);
	}
	
	private ControlsOutput firstJump() {
		return new ControlsOutput().withJump(true);
	}
	
	private ControlsOutput betweenJumps(Car car) {
		Vector3 localTargetForward = car.inLocalCoordinates(target);
		Vector3 localTargetUp = car.inLocalCoordinates(Vector3.UP);
		
		return AerialUtils.align(car, localTargetForward, localTargetUp);
	}
	
	private ControlsOutput secondJump(Car car) {
		ControlsOutput controls = new ControlsOutput();
		
		controls.withThrottle(1);
		controls.withPitch(1);
		
		Vector3 localTarget = car.inLocalCoordinates(target);
		double angle = Math.atan2(localTarget.y, localTarget.x);
		double angleSign = Math.signum(angle);
		
		controls.withYaw(angleSign - (angle / Math.PI));
		controls.withRoll(angleSign);

		return controls;
	}
	
	private ControlsOutput flipCancel() {
		ControlsOutput controls = new ControlsOutput();
		
		controls.withPitch(-1);
		controls.withThrottle(1);
		
		return controls;
	}
	
	private ControlsOutput landing(Car car) {
		Vector3 localTargetForward = car.inLocalCoordinates(target);
		Vector3 localTargetUp = car.inLocalCoordinates(Vector3.UP);
		
		ControlsOutput controls = AerialUtils.align(car, localTargetForward, localTargetUp);
		
		controls.withSlide(true);
		
		return controls;
	}

	@Override
	public boolean done() {
		return done;
	}
	
}
