package balliasbot.data;

import balliasbot.math.Vector3;

public class Field {

	public static final Vector3 CENTER_FIELD = new Vector3(0, 0, 0);
	public static final Vector3 FLOOR = new Vector3(0, 0, 0);
	public static final Vector3 SIDE_WALL_A = new Vector3(4096, 0, 0);
	public static final Vector3 SIDE_WALL_B = new Vector3(-4096, 0, 0);
	public static final Vector3 BACK_WALL_A = new Vector3(0, 5120, 0);
	public static final Vector3 BACK_WALL_B = new Vector3(0, -5120, 0);
	public static final Vector3 CEILING = new Vector3(0, 0, 2044);
	public static final double SIDE_WALL_LENGTH = 7936;
	public static final double BACK_WALL_LENGTH = 5888;
	public static final double CORNER_WALL_LENGTH = 1629.174;
	public static final double GOAL_HEIGHT = 642.755;
	public static final double GOAL_LENGTH = 1786;
	public static final double GOAL_DEPTH = 880;
	
	public final Vector3 ownGoal;
	public final Vector3 ownGoalLeft;
	public final Vector3 ownGoalRight;
	public final Vector3 opponentGoal;
	public final Vector3 opponentGoalLeft;
	public final Vector3 opponentGoalRight;
	
	public Field(int team) {
		ownGoal = getGoal(team);
		ownGoalLeft = getGoalLeft(team);
		ownGoalRight = getGoalRight(team);
		opponentGoal = getGoal(1 - team);
		opponentGoalLeft = getGoalLeft(1 - team);
		opponentGoalRight = getGoalRight(1 - team);
	}
	
	public static Vector3 getGoal(int team) {
		return team == 0 ? new Vector3(0, -5200, 0) : new Vector3(0, 5200, 0);
	}
	
	public static Vector3 getGoalLeft(int team) {
		return team == 0 ? new Vector3(-800, -5200, 321) : new Vector3(800, 5200, 321);
	}
	
	public static Vector3 getGoalRight(int team) {
		return team == 0 ? new Vector3(800, -5200, 321) : new Vector3(-800, 5200, 321);
	}
	
}