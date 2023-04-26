package balliasbot.data;


import java.util.List;

import balliasbot.math.Vector3;
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
    
}
