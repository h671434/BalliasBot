package balliasbot.data;

import balliasbot.math.Vec3;

public class Field {

	public static final Vec3 CENTER_FIELD = new Vec3(0, 0, 0);
	public static final Vec3 FLOOR = new Vec3(0, 0, 0);
	public static final Vec3 SIDE_WALL_A = new Vec3(4096, 0, 0);
	public static final Vec3 SIDE_WALL_B = new Vec3(-4096, 0, 0);
	public static final Vec3 BACK_WALL_A = new Vec3(0, 5120, 0);
	public static final Vec3 BACK_WALL_B = new Vec3(0, -5120, 0);
	public static final Vec3 CEILING = new Vec3(0, 0, 2044);
	public static final double SIDE_WALL_LENGTH = 7936;
	public static final double BACK_WALL_LENGTH = 5888;
	public static final double CORNER_WALL_LENGTH = 1629.174;
	public static final double GOAL_HEIGHT = 642.755;
	public static final double GOAL_LENGTH = 1786;
	public static final double GOAL_DEPTH = 880;
	
	public final Vec3 ownGoal;
	public final Vec3 ownGoalLeft;
	public final Vec3 ownGoalRight;
	public final Vec3 opponentGoal;
	public final Vec3 opponentGoalLeft;
	public final Vec3 opponentGoalRight;
	
	public Field(int team) {
		ownGoal = getGoal(team);
		ownGoalLeft = getGoalLeft(team);
		ownGoalRight = getGoalRight(team);
		opponentGoal = getGoal(1 - team);
		opponentGoalLeft = getGoalLeft(1 - team);
		opponentGoalRight = getGoalRight(1 - team);
	}
	
	public static Vec3 getGoal(int team) {
		return team == 0 ? new Vec3(0, -5200, 0) : new Vec3(0, 5200, 0);
	}
	
	public static Vec3 getGoalLeft(int team) {
		return team == 0 ? new Vec3(-800, -5200, 321) : new Vec3(800, 5200, 321);
	}
	
	public static Vec3 getGoalRight(int team) {
		return team == 0 ? new Vec3(800, -5200, 321) : new Vec3(-800, 5200, 321);
	}
	
}