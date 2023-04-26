package balliasbot.state;

import balliasbot.controls.ControlsOutput;
import balliasbot.controls.DriveControls;
import balliasbot.data.Car;
import balliasbot.data.DataPacket;
import balliasbot.data.Field;
import balliasbot.data.KinematicInstant;
import balliasbot.math.Vector3;

public class TakeShot extends State {
	
	private KinematicInstant targetBallInstant;
	
	@Override
	public boolean isViable(DataPacket data) {
		return targetBallInstant != null;
	}

	@Override
	public ControlsOutput exec(DataPacket data) {
		Vector3 nextTargetPosition = findNextTargetPosition(data.car);
		
		Vector3 carToBall = data.car.pointingTo(targetBallInstant.position);
		double remainingTime = targetBallInstant.time - data.currentTime;
		double targetSpeed = carToBall.magnitude() / remainingTime;
 		
		return new DriveControls(data.car, nextTargetPosition, targetSpeed);
	}
	
	private Vector3 findNextTargetPosition(Car car) {
		Vector3 ball = targetBallInstant.position;
		Vector3 carToBall = ball.minus(car.position);
		Vector3 interceptDirection = findInterceptDirection(ball, carToBall, car.team);
		
		Vector3 closestBallOffset = ball.offset(interceptDirection, carToBall.magnitude() - 150);
		Vector3 carToOffset = closestBallOffset.minus(car.position);
		
		if(carToOffset.magnitude() > carToBall.magnitude()) {
			return closestBallOffset;
		} 
		
		return ball;	
	}
	
	private Vector3 findInterceptDirection(Vector3 ball, Vector3 carToBall, int team) {
		Vector3 ballToLeftPost = Field.getGoalLeft(1 - team).minus(ball);
		Vector3 ballToRightPost = Field.getGoalRight(1 - team).minus(ball);
		
		Vector3 carToBallDirection = carToBall.normalized();
		Vector3 ballToLeftPostDirection = ballToLeftPost.normalized(); 
		Vector3 ballToRightPostDirection = ballToRightPost.normalized();
		
		return carToBallDirection.clamp(
				ballToRightPostDirection, 
				ballToLeftPostDirection);
	}
	
}
