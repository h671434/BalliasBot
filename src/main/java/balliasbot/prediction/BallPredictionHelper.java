package balliasbot.prediction;

import java.awt.Color;

import balliasbot.data.CarData;
import balliasbot.data.DataPacket;
import balliasbot.math.Vector3;
import rlbot.cppinterop.RLBotDll;
import rlbot.cppinterop.RLBotInterfaceException;
import rlbot.flat.BallPrediction;
import rlbot.flat.PredictionSlice;
import rlbot.render.Renderer;

public class BallPredictionHelper {
	
	
	public static PredictionData nextReachable(DataPacket data, int startTime) {
		try {
			BallPrediction predictedBallPath = RLBotDll.getBallPrediction();
			for (int i = startTime; i < 6 * 60; i += 6) {
				PredictionData ball = new PredictionData(
						new Vector3(predictedBallPath.slices(i).physics().location()),
						new Vector3(predictedBallPath.slices(i).physics().velocity()),
						i / 60.0);
				
				if(ball.isReachable(data.car) && ball.position.z < 200) {
					return ball;
				}
				
				// Return null if any other car is closer
				for(CarData anyCar : data.allCars) {
					if (ball.isReachable(anyCar)) {
						return null;
					}
				}
			}
       } catch (RLBotInterfaceException ignored) { }

		return null;
	}
	
	public static PredictionData getPredictionAt(DataPacket data, int time) {
		try {
			BallPrediction ballPrediction = RLBotDll.getBallPrediction();
			PredictionData ball = new PredictionData(
					new Vector3(ballPrediction.slices(time).physics().location()),
					new Vector3(ballPrediction.slices(time).physics().velocity()),
					time / 60.0);
			
			return ball;
					
		} catch (RLBotInterfaceException ignored) {}

		return new PredictionData(data.ball.position, data.ball.velocity, 0);
	}

    public static void drawTillMoment(BallPrediction ballPrediction, float gameSeconds, Color color, Renderer renderer) {
        Vector3 previousLocation = null;
        
        for (int i = 0; i < ballPrediction.slicesLength(); i += 4) {
            PredictionSlice slice = ballPrediction.slices(i);
            if (slice.gameSeconds() > gameSeconds) {
                break;
            }
            
            Vector3 location = new Vector3(slice.physics().location());
            if (previousLocation != null) {
                renderer.drawLine3d(color, previousLocation, location);
            }
            
            previousLocation = location;
        }
    }
    
}
