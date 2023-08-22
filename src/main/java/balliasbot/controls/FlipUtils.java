package balliasbot.controls;

public class FlipUtils {
	
	/* 
	 * First jump can be held for 0.2 sec for the highest possible jump.
	 * Second jump is lost after 1.25 sec. Countdown starts after the first jump is released.
	 * Vertical momentum continues as normal for 0.15 sec after starting a dodge. 
	 * After that vertical momentum decreses by 35% per tick.
	 * The dodge lasts for 0.65 sec.
	 * Forward and sideways dodge increses the momentum in the direction by 500 uu/s. Scaling factor is 0.9.
	 * 500uu/s * (1 + 0.9 * (forwardSpeed / 2300uu/s))
	 * forwardSpeed is |Velocityforward|
	 * Backwards dodge increases momentum backwards by 533 uu/s. Scaling factor is 1.5.
	 * 533uu/s * (1 + 1.5 * (forwardSpeed / 2300uu/s))
	 * A dodge makes the car reach max angular velocity in the direction in 3 ticks / 25ms.
	 * A flip can only be cancelled up or down. Needs to be completely up or down.
	 * With a diagonal flip, the roll can not be cancelled, but pitch can.
	 * 5 ticks / 40ms before a dodge can be cancelled
	 */
	
	public static final double DODGE_DURATION = 0.65;
	
}
