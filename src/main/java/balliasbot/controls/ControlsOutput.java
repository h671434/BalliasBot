package balliasbot.controls;

import rlbot.ControllerState;

/**
 * A helper class for returning controls for your bot.
 *
 * This class is here for your convenience, it is NOT part of the framework. You can change it as much
 * as you want, or delete it.
 */
public class ControlsOutput implements ControllerState {

    private double steer; // 0 is straight, -1 is hard left, 1 is hard right.
    private double pitch; // -1 for front flip, 1 for back flip
    private double yaw; // 0 is straight, -1 is hard left, 1 is hard right.
    private double roll; // 0 is straight, -1 is hard left, 1 is hard right.
    private double throttle; // 0 is none, -1 is backwards, 1 is forwards

    private boolean jumpDepressed;
    private boolean boostDepressed;
    private boolean slideDepressed;
    private boolean useItemDepressed;

    public ControlsOutput withSteer(double steer) {
        this.steer = clamp(steer);
        return this;
    }

    public ControlsOutput withPitch(double pitch) {
        this.pitch = clamp(pitch);
        return this;
    }

    public ControlsOutput withYaw(double yaw) {
        this.yaw = clamp(yaw);
        return this;
    }

    public ControlsOutput withRoll(double roll) {
        this.roll = clamp(roll);
        return this;
    }

    public ControlsOutput withThrottle(double d) {
        this.throttle = clamp(d);
        return this;
    }

    public ControlsOutput withJump(boolean jumpDepressed) {
        this.jumpDepressed = jumpDepressed;
        return this;
    }

    public ControlsOutput withBoost(boolean boostDepressed) {
        this.boostDepressed = boostDepressed;
        return this;
    }

    public ControlsOutput withSlide(boolean slideDepressed) {
        this.slideDepressed = slideDepressed;
        return this;
    }

    public ControlsOutput withUseItem(boolean useItemDepressed) {
        this.useItemDepressed = useItemDepressed;
        return this;
    }

    public ControlsOutput withJump() {
        this.jumpDepressed = true;
        return this;
    }

    public ControlsOutput withBoost() {
        this.boostDepressed = true;
        return this;
    }

    public ControlsOutput withSlide() {
        this.slideDepressed = true;
        return this;
    }

    public ControlsOutput withUseItem() {
        this.useItemDepressed = true;
        return this;
    }

    private double clamp(double d) {
        return Math.max(-1, Math.min(1, d));
    }

    @Override
    public float getSteer() {
        return (float) steer;
    }

    @Override
    public float getThrottle() {
        return (float) throttle;
    }

    @Override
    public float getPitch() {
        return (float) pitch;
    }

    @Override
    public float getYaw() {
        return (float) yaw;
    }

    @Override
    public float getRoll() {
        return (float) roll;
    }

    @Override
    public boolean holdJump() {
        return jumpDepressed;
    }

    @Override
    public boolean holdBoost() {
        return boostDepressed;
    }

    @Override
    public boolean holdHandbrake() {
        return slideDepressed;
    }

    @Override
    public boolean holdUseItem() {
        return useItemDepressed;
    }
}