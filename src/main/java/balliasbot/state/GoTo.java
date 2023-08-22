package balliasbot.state;

import balliasbot.controls.ControlsOutput;
import balliasbot.controls.DriveUtils;
import balliasbot.data.Car;
import balliasbot.data.DataPacket;
import balliasbot.maneuver.AerialRecovery;
import balliasbot.maneuver.Drive;
import balliasbot.maneuver.HalfFlip;
import balliasbot.maneuver.Maneuver;
import balliasbot.maneuver.QuickFlip;
import balliasbot.maneuver.WaveDash;
import balliasbot.math.RotationMatrix;
import balliasbot.math.Vector3;

public abstract class GoTo extends State {

	protected Vector3 targetPosition = Vector3.ZERO;
	protected double targetSpeed = 1800;
	
	protected Maneuver activeManeuver = null;

	public GoTo() {};
	
	public GoTo(Vector3 targetPosition) {
		this.targetPosition = targetPosition;
	}
	
	public GoTo(Vector3 targetPosition, double targetSpeed) {
		this.targetPosition = targetPosition;
		this.targetSpeed = targetSpeed;
	}

	@Override
	public ControlsOutput exec(DataPacket data) {
		if(hasActiveManeuver()) {
			return activeManeuver.exec(data);
		}
		
		if(shouldEndState(data)) {
			return null;
		}
		
		activeManeuver = findViableManeuver(data);
		
		return activeManeuver.exec(data);
	}
	
	protected boolean hasActiveManeuver() {
		if(activeManeuver != null) {
			if(!activeManeuver.done()) {
				return true;
			}
			
			activeManeuver = null;
		}
		
		return false;
	}
	
	protected boolean shouldEndState(DataPacket data) {
		return targetPosition.distance(data.car.position) < 100;
	}
	
	protected Maneuver findViableManeuver(DataPacket data) {
		if(shouldAerialRecover(data)) {
			return new AerialRecovery();
		}
		if(shouldHalfFlip(data)) {
			return new HalfFlip(data, targetPosition);
		}
		if(shouldWaveDash(data)) {
			return new WaveDash(data, targetPosition);
		}
		if(shouldQuickFlip(data)) {
			return new QuickFlip(data, targetPosition);
		}
		
		return new Drive(targetPosition, targetSpeed);
	}
	
	protected boolean shouldAerialRecover(DataPacket data) {
		return !data.car.hasWheelContact;
	}
	
	protected boolean shouldHalfFlip(DataPacket data) {
		Vector3 localTargetDirection = data.car.inLocalCoordinates(targetPosition).normalized();
		
		boolean pointingAwayFromTarget = Math.abs(localTargetDirection.x) > 0.7;
		boolean highDistance = data.car.position.distance(targetPosition) > 500;
		boolean lowSpeed = data.car.speed() < 500;
		
		return highDistance && lowSpeed && pointingAwayFromTarget;
	}
	
	protected boolean shouldWaveDash(DataPacket data) {
		Vector3 localTargetDirection = data.car.inLocalCoordinates(targetPosition).normalized();
		
		boolean pointingToTarget = Math.abs(localTargetDirection.x) < 0.3;
		boolean lowBoost = data.car.boost < 35;
		boolean highDistance = data.car.position.distance(targetPosition) > 500;
		boolean lowSpeed = data.car.speed() < targetSpeed + 300;
		
		return lowBoost && highDistance && lowSpeed && pointingToTarget;
	}
	
	protected boolean shouldQuickFlip(DataPacket data) {
		Vector3 localTargetDirection = data.car.inLocalCoordinates(targetPosition).normalized();
		
		boolean pointingToTarget = Math.abs(localTargetDirection.x) < 0.3;
		boolean highDistance = data.car.position.distance(targetPosition) > 700;
		boolean lowSpeed = data.car.speed() < targetSpeed + 300;
		
		return highDistance && lowSpeed && pointingToTarget;
	}
	
}
