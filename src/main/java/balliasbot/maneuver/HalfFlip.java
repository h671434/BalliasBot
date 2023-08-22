package balliasbot.maneuver;

import balliasbot.controls.AerialUtils;
import balliasbot.controls.ControlsOutput;
import balliasbot.controls.DriveUtils;
import balliasbot.controls.FlipUtils;
import balliasbot.data.Car;
import balliasbot.data.DataPacket;
import balliasbot.math.RotationMatrix;
import balliasbot.math.Vector3;

public class HalfFlip implements Maneuver {
	
	private final double startTime;
	private Vector3 target;
	
	private double firstJumpDuration = 0.1;
	private double betweenJumpsDuration = 0.1;
	private double secondJumpDuration = 0.15;
	
	private boolean done = false;
	
	public HalfFlip(DataPacket data, Vector3 target) {
		this.startTime = data.currentTime;
		this.target = target;
	}
	
	@Override
	public ControlsOutput exec(DataPacket data) {
		double currentTime = data.currentTime - startTime;
		
		double firstJumpEnd = firstJumpDuration;
		double betweenJumpsEnd = firstJumpEnd + betweenJumpsDuration;
		double secondJumpEnd = betweenJumpsEnd + secondJumpDuration;
		
		if(currentTime <= firstJumpEnd) {
			return firstJump();
		}
		if(currentTime <= betweenJumpsEnd) {
			return betweenJumps();
		}
		if(currentTime <= secondJumpEnd) {
			return secondJump(data.car);
		}
		
		
		if(data.car.hasWheelContact && isGoingInDirectionOfVelocity(data.car)) {
			done = true;
		}
		
		return alignLanding(data.car);
	}
	
	private ControlsOutput firstJump() {
		return new ControlsOutput().withJump(true);
	}
	
	private ControlsOutput betweenJumps() {
		return new ControlsOutput();
	}
	
	private ControlsOutput secondJump(Car car) {
		ControlsOutput controls = new ControlsOutput();
		
		controls.withJump(true);
		controls.withPitch(-1);
		
		Vector3 localTarget = car.inLocalCoordinates(target);
		double angle = Math.atan2(localTarget.y, localTarget.x);
		
		controls.withYaw(angle / Math.PI);
		controls.withRoll(angle < 0 ? -1 : 1);
		
		return controls;
	}
	
	private boolean isGoingInDirectionOfVelocity(Car car) {
		Vector3 velocityDirection = car.velocity.normalized();
		Vector3 forwardDirection = car.orientation.forward;
		Vector3 directionCompared = velocityDirection.minus(forwardDirection);
		
		return directionCompared.magnitude() < 0.3;
	}
	
	private ControlsOutput alignLanding(Car car) {
		Vector3 localTargetForward = car.inLocalCoordinates(target);
		Vector3 localTargetUp = car.inLocalCoordinates(Vector3.UP);
		
		ControlsOutput controls = AerialUtils.align(car, localTargetForward, localTargetUp);
		
		if(car.position.distance(target) > 500) {
			DriveUtils.controlSpeed(controls, car, target, 2000);
		}
		if(car.hasWheelContact) {
			DriveUtils.controlSteer(controls, car, target);
		}
		
		controls.withSlide(true);
		
		return controls;
	}

	@Override
	public boolean done() {
		return done;
	}

}
