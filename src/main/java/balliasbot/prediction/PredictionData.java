package balliasbot.prediction;

import balliasbot.data.CarData;
import balliasbot.math.Vector3;

public class PredictionData {

	public Vector3 position;
	public Vector3 velocity;
	public double time;
	
	public PredictionData(Vector3 position, Vector3 velocity, double time) {
		this.position = position;
		this.velocity = velocity;
		this.time = time;
	}	
	
	/**
	 * Is ball reachable for given car?
	 */
	public boolean isReachable(CarData car) {
		Vector3 carToBall = position.minus(car.position);
		Vector3 carToBallDir = carToBall.normalized();
		double dist = carToBall.magnitude();
		 
		double speedTowardsBall = car.velocity.dotProduct(carToBallDir);
		double averageSpeed = (speedTowardsBall + 2300) / 2.0;

		double travelTime = dist / averageSpeed;
		
		return (travelTime < time);
	}
	
}