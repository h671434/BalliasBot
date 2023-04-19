package balliasbot.data;


import balliasbot.math.Vec3;
import rlbot.flat.Touch;

public class BallTouch {
	
	public final Vec3 position;
	public final Vec3 normal;
	public final String playerName;
	public final float gameSeconds;
	public final int playerIndex;
	public final int team;

	public BallTouch(final Touch touch) {
		this.position = new Vec3(touch.location());
		this.normal = new Vec3(touch.normal());
		this.playerName = touch.playerName();
		this.gameSeconds = touch.gameSeconds();
		this.playerIndex = touch.playerIndex();
		this.team = touch.team();
	}
	
}
