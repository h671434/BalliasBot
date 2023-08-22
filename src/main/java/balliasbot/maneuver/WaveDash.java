package balliasbot.maneuver;

import balliasbot.controls.AerialUtils;
import balliasbot.controls.ControlsOutput;
import balliasbot.controls.DriveUtils;
import balliasbot.data.Car;
import balliasbot.data.DataPacket;
import balliasbot.math.Vector3;

public class WaveDash implements Maneuver {

	private final double startTime;
	private Vector3 target;
	
	private double firstJumpEnd = 0.1;
	
	private boolean done = false;
	
	public WaveDash(DataPacket data, Vector3 target) {
		this.startTime = data.currentTime;
		this.target = target;
		
		if(!data.car.hasWheelContact) {
			firstJumpEnd = -1;
		}
	}
	
	@Override
	public ControlsOutput exec(DataPacket data) {
		double currentTime = data.currentTime - startTime;
		
		if(currentTime <= firstJumpEnd) {
			return firstJump();
		}
		
		if(!data.car.hasWheelContact) {
			if(isFalling(data.car) && isJustAboveSurface(data.car)) {
				return secondJump();
			} else {
				return betweenJumps(data.car);
			}
		}

		if(data.car.hasWheelContact) {
			done = true;
		}
		
		return afterLanding(data.car);
	}
	
	private ControlsOutput firstJump() {
		return new ControlsOutput().withJump(true);
	}
	
	private boolean isFalling(Car car) {
		return car.velocity.z < 0;
	}
	
	private boolean isJustAboveSurface(Car car) {
		return car.position.z < 100;
	}
	
	private ControlsOutput betweenJumps(Car car) {
		Vector3 localTargetForward = car.inLocalCoordinates(target);
		Vector3 localTargetUp = car.inLocalCoordinates(new Vector3(0.3, 0, 0.7));
		
		return AerialUtils.align(car, localTargetForward, localTargetUp);
	}
	
	private ControlsOutput secondJump() {
		ControlsOutput controls = new ControlsOutput();
		
		controls.withJump(true);
		controls.withPitch(1);
		controls.withSlide(true);
		
		return controls;
	}
	
	private ControlsOutput afterLanding(Car car) {
		ControlsOutput controls = new ControlsOutput();
		
		controls.withSlide(true);
		
		DriveUtils.controlSteer(controls, car, target);
		DriveUtils.controlSpeed(controls, car, target, 2300);
		
		return controls;
	}
	
	@Override
	public boolean done() {
		return done;
	}
	
}
