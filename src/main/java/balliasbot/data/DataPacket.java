package balliasbot.data;

import java.util.ArrayList;
import java.util.List;

import balliasbot.BalliasBot;
import rlbot.flat.GameTickPacket;

public class DataPacket {

	public static final double DELTA_TIME = 1 / 120; // gameticks per second
	
	public final BalliasBot bot;
    public final Car car;
    public final List<Car> allCars;
    public final Ball ball;
    public final Field field;
    public final int team;
    public final int playerIndex;
    public final double currentTime;

    public DataPacket(BalliasBot bot, GameTickPacket request, int playerIndex) {
    	this.bot = bot;
    	this.playerIndex = playerIndex;
        this.currentTime = request.gameInfo().secondsElapsed();
    	this.ball = new Ball(request.ball(), currentTime);
    	
    	this.allCars = new ArrayList<>();
        for (int i = 0; i < request.playersLength(); i++) {
            allCars.add(new Car(request.players(i), currentTime));
        }
        
        this.car = allCars.get(playerIndex);
        this.team = this.car.team;
        this.field = new Field(team);
    }
}
