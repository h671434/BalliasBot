package balliasbot.boost;

import balliasbot.math.Vec3;

public class BoostPad {

    private final Vec3 location;
    private final boolean isFullBoost;
    private boolean isActive;

    public BoostPad(Vec3 location, boolean isFullBoost) {
        this.location = location;
        this.isFullBoost = isFullBoost;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Vec3 getLocation() {
        return location;
    }

    public boolean isFullBoost() {
        return isFullBoost;
    }

    public boolean isActive() {
        return isActive;
    }
}
