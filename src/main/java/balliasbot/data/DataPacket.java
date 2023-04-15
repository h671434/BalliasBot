package balliasbot.data;

import java.util.ArrayList;
import java.util.List;

import rlbot.flat.GameTickPacket;

public class DataPacket {

	public static final double DELTA_TIME = 1 / 120; // gameticks per second
	
    public final CarData car;
    public final List<CarData> allCars;
    public final BallData ball;
    public final int team;
    public final int playerIndex;
    public final double elapsedSeconds;

    public DataPacket(GameTickPacket request, int playerIndex) {
    	this.playerIndex = playerIndex;
    	this.ball = new BallData(request.ball());
        
    	this.allCars = new ArrayList<>();
        for (int i = 0; i < request.playersLength(); i++) {
            allCars.add(new CarData(request.players(i)));
        }
        
        this.elapsedSeconds = request.gameInfo().secondsElapsed();
        this.car = allCars.get(playerIndex);
        this.team = this.car.team;
    }
}
