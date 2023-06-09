package balliasbot.boost;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import balliasbot.math.Vector3;
import rlbot.cppinterop.RLBotDll;
import rlbot.flat.BoostPadState;
import rlbot.flat.FieldInfo;
import rlbot.flat.GameTickPacket;

public class BoostManager {

    private static final List<BoostPad> orderedBoosts = new ArrayList<>();
    private static final List<BoostPad> fullBoosts = new ArrayList<>();
    private static final List<BoostPad> smallBoosts = new ArrayList<>();

    public static List<BoostPad> getFullBoosts() {
        return fullBoosts;
    }

    public static List<BoostPad> getSmallBoosts() {
        return smallBoosts;
    }

    private static void loadFieldInfo(FieldInfo fieldInfo) {

        synchronized (orderedBoosts) {

            orderedBoosts.clear();
            fullBoosts.clear();
            smallBoosts.clear();

            for (int i = 0; i < fieldInfo.boostPadsLength(); i++) {
                rlbot.flat.BoostPad flatPad = fieldInfo.boostPads(i);
                BoostPad ourPad = new BoostPad(new Vector3(flatPad.location()), flatPad.isFullBoost());
                orderedBoosts.add(ourPad);
                if (ourPad.isFullBoost()) {
                    fullBoosts.add(ourPad);
                } else {
                    smallBoosts.add(ourPad);
                }
            }
        }
    }

    public static void loadGameTickPacket(GameTickPacket packet) {

        if (packet.boostPadStatesLength() > orderedBoosts.size()) {
            try {
                loadFieldInfo(RLBotDll.getFieldInfo());
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        for (int i = 0; i < packet.boostPadStatesLength(); i++) {
            BoostPadState boost = packet.boostPadStates(i);
            BoostPad existingPad = orderedBoosts.get(i); // existingPad is also referenced from the fullBoosts and smallBoosts lists
            existingPad.setActive(boost.isActive());
        }
    }

}
