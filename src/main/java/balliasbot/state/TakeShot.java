package balliasbot.state;

import java.time.Duration;

import balliasbot.controls.AerialControls;
import balliasbot.controls.ControlsOutput;
import balliasbot.controls.DriveControls;
import balliasbot.data.DataPacket;
import balliasbot.data.FieldData;
import balliasbot.maneuver.AirRecovery;
import balliasbot.maneuver.Dodge;
import balliasbot.maneuver.Maneuver;
import balliasbot.math.Vector3;
import balliasbot.prediction.BallPredictionHelper;
import balliasbot.prediction.PredictionData;

public class TakeShot extends GoTo {

	private PredictionData prediction;
	private double startTime = 0;
	
	private double lastDodgeTime = 0;
	
	@Override
	public boolean isViable(DataPacket data) {
		prediction = BallPredictionHelper.nextReachable(data, 10);
		startTime = data.elapsedSeconds;
		
		return prediction != null;
	}
	
	@Override
	public ControlsOutput exec(DataPacket data) {
		if(maneuverIsActive()) {
			return execManeuver(data);
		}
		if(data.elapsedSeconds - startTime > prediction.time + 0.2) {
			return null;
		}

		target = findTarget(data);
		targetSpeed = findTargetSpeed(data);
		
		onGoingManeuver = checkForViableManeuvers(data);
		
		return super.exec(data);
	}	
	
	private Vector3 findTarget(DataPacket data) {
		Vector3 ball = prediction.position;
		Vector3 carToBall = data.car.carTo(ball);
		Vector3 interceptDirection = findInterceptDirection(ball, carToBall, data.team);
		Vector3 closestBallOffset = ball.offset(interceptDirection, carToBall.magnitude() - 150);
		Vector3 carToOffset = closestBallOffset.minus(data.car.position);
		
		if(carToOffset.magnitude() < carToBall.magnitude()) {
			return ball;
		}
		
		return closestBallOffset;
	}
	
	private Vector3 findInterceptDirection(Vector3 ball, Vector3 carToBall, int team) {
		Vector3 ballToLeftPost = FieldData.getGoalLeft(1 - team).minus(ball);
		Vector3 ballToRightPost = FieldData.getGoalRight(1 - team).minus(ball);
		
		Vector3 carToBallDirection = carToBall.normalized();
		Vector3 ballToLeftPostDirection = ballToLeftPost.normalized(); 
		Vector3 ballToRightPostDirection = ballToRightPost.normalized();
		
		return carToBallDirection.clamp(
				ballToRightPostDirection, 
				ballToLeftPostDirection);
	}
	
	private double findTargetSpeed(DataPacket data) {
		Vector3 ball = prediction.position;
		Vector3 carToBall = data.car.carTo(ball);
		
		double remainingTime = prediction.time - (data.elapsedSeconds - startTime);
		
		return carToBall.magnitude() / remainingTime;
	}
	
	private Maneuver checkForViableManeuvers(DataPacket data) {
		if(!data.car.hasWheelContact) {
			return new AirRecovery(target);
		} 
		
		if(data.car.position.distance(target.flatten()) < 500
				&& data.elapsedSeconds - lastDodgeTime > 6
				&& data.car.orientation.forward.dotProduct(target.normalized()) < 0.0) {
			lastDodgeTime = data.elapsedSeconds;
			
			return new Dodge(data, target);
		}
		
		return null;
	}

}
