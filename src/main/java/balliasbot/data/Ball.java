package balliasbot.data;


import java.util.List;

import balliasbot.math.Vec3;
import rlbot.cppinterop.RLBotDll;
import rlbot.cppinterop.RLBotInterfaceException;
import rlbot.flat.BallInfo;
import rlbot.flat.BallPrediction;
import rlbot.flat.Physics;

public class Ball extends KinematicInstant {
	
    public final BallTouch latestTouch;
    public final boolean hasBeenTouched;

    public Ball(final BallInfo ball, double time) {
    	super(ball.physics(), time);
        this.hasBeenTouched = ball.latestTouch() != null;
        this.latestTouch = hasBeenTouched ? new BallTouch(ball.latestTouch()) : null;
    }
    
    public Ball(KinematicInstant instant, boolean hasBeenTouched, BallTouch latestTouch) {
    	super(instant);
        this.hasBeenTouched = hasBeenTouched;
        this.latestTouch = latestTouch;
    }
    
    public static Ball nextReachable(DataPacket data, int startTime) {
    	BallPrediction predictedBallPath = null;
		try {
			predictedBallPath = RLBotDll.getBallPrediction();
		} catch (RLBotInterfaceException e) {
    	   return null;
       	}
		
		for (int i = startTime; i < 6 * 60; i += 6) {
			KinematicInstant ballPhysics = new KinematicInstant(
					predictedBallPath.slices(i).physics(),
					data.currentTime + (i / 60.0));
			Ball ball = new Ball(
					ballPhysics,
					data.ball.hasBeenTouched,
					data.ball.latestTouch);
			
			if(ball.isReachable(data.car) && ball.position.z < 200) {
				return ball;
			}
			if(ball.isReachableForOpponent(data.allCars, data.team)) {
				return null;
			}
		}
		
		return null;
	}
    
	public boolean isReachable(Car car) {
		Vec3 carToBall = position.minus(car.position);
		Vec3 carToBallDir = carToBall.normalized();
		double dist = carToBall.magnitude();
		 
		double speedTowardsBall = car.velocity.dotProduct(carToBallDir);
		double averageSpeed = (speedTowardsBall + 2300) / 2.0;

		double travelTime = dist / averageSpeed;
		
		return (travelTime < time);
	}
	
	public boolean isReachableForOpponent(List<Car> allCars, int ownTeam) {
		for(Car anyCar : allCars) {
			if (anyCar.team != ownTeam && isReachable(anyCar)) {
				return true;
			}
		}
		
		return false;
	}
    
}
