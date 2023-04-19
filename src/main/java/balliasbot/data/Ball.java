package balliasbot.data;


import balliasbot.math.Vec3;
import rlbot.cppinterop.RLBotDll;
import rlbot.cppinterop.RLBotInterfaceException;
import rlbot.flat.BallInfo;
import rlbot.flat.BallPrediction;
import rlbot.flat.Physics;

public class Ball {
	
    public final Vec3 position;
    public final Vec3 velocity;
    public final Vec3 spin;
    public final BallTouch latestTouch;
    public final boolean hasBeenTouched;
    public final double time;

    public Ball(final BallInfo ball, double time) {
        this.position = new Vec3(ball.physics().location());
        this.velocity = new Vec3(ball.physics().velocity());
        this.spin = new Vec3(ball.physics().angularVelocity());
        this.hasBeenTouched = ball.latestTouch() != null;
        this.latestTouch = this.hasBeenTouched ? new BallTouch(ball.latestTouch()) : null;
        this.time = time;
    }
    
    public Ball(Vec3 position, Vec3 velocity, Vec3 spin, BallTouch latestTouch,
    		boolean hasBeenTouched, double time) {
    	this.position = position;
    	this.velocity = velocity;
    	this.spin = spin;
    	this.latestTouch = latestTouch;
    	this.hasBeenTouched = hasBeenTouched;
    	this.time = time;
    }
    
    public static Ball nextReachable(DataPacket data, int startTime) {
		try {
			BallPrediction predictedBallPath = RLBotDll.getBallPrediction();
			
			for (int i = startTime; i < 6 * 60; i += 6) {
				Physics ballPhysics = predictedBallPath.slices(i).physics();
				Ball ball = new Ball(
						new Vec3(ballPhysics.location()),
						new Vec3(ballPhysics.velocity()),
						new Vec3(ballPhysics.angularVelocity()),
						data.ball.latestTouch,
						data.ball.hasBeenTouched,
						data.elapsedSeconds + (i / 60.0));
				
				if(ball.isReachable(data.car) && ball.position.z < 200) {
					return ball;
				}
				
				// Return null if any other car is closer
				for(Car anyCar : data.allCars) {
					if (ball.isReachable(anyCar)) {
						return null;
					}
				}
			}
       } catch (RLBotInterfaceException ignored) { }

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
    
}
