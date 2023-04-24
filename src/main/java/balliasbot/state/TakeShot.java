package balliasbot.state;

import balliasbot.controls.ControlsOutput;
import balliasbot.controls.DriveControls;
import balliasbot.data.Car;
import balliasbot.data.DataPacket;
import balliasbot.data.Field;
import balliasbot.data.KinematicInstant;
import balliasbot.math.Vec3;

public class TakeShot extends State {
	
	private KinematicInstant targetBallInstant;
	
	@Override
	public boolean isViable(DataPacket data) {
		return targetBallInstant != null;
	}

	@Override
	public ControlsOutput exec(DataPacket data) {
		Vec3 nextTargetPosition = findNextTargetPosition(data.car);
		
		Vec3 carToBall = data.car.positionToPoint(targetBallInstant.position);
		double remainingTime = targetBallInstant.time - data.currentTime;
		double targetSpeed = carToBall.magnitude() / remainingTime;
 		
		return new DriveControls(data.car, nextTargetPosition, targetSpeed);
	}
	
	private Vec3 findNextTargetPosition(Car car) {
		Vec3 ball = targetBallInstant.position;
		Vec3 carToBall = ball.minus(car.position);
		Vec3 interceptDirection = findInterceptDirection(ball, carToBall, car.team);
		
		Vec3 closestBallOffset = ball.offset(interceptDirection, carToBall.magnitude() - 150);
		Vec3 carToOffset = closestBallOffset.minus(car.position);
		
		if(carToOffset.magnitude() > carToBall.magnitude()) {
			return closestBallOffset;
		} 
		
		return ball;	
	}
	
	private Vec3 findInterceptDirection(Vec3 ball, Vec3 carToBall, int team) {
		Vec3 ballToLeftPost = Field.getGoalLeft(1 - team).minus(ball);
		Vec3 ballToRightPost = Field.getGoalRight(1 - team).minus(ball);
		
		Vec3 carToBallDirection = carToBall.normalized();
		Vec3 ballToLeftPostDirection = ballToLeftPost.normalized(); 
		Vec3 ballToRightPostDirection = ballToRightPost.normalized();
		
		return carToBallDirection.clamp(
				ballToRightPostDirection, 
				ballToLeftPostDirection);
	}
	
}
