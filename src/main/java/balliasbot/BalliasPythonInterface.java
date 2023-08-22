package balliasbot;

import rlbot.Bot;
import rlbot.manager.BotManager;
import rlbot.pyinterop.SocketServer;

public class BalliasPythonInterface extends SocketServer {
	
    public BalliasPythonInterface(int port, BotManager botManager) {
        super(port, botManager);
    }

    @Override
    protected Bot initBot(int index, String botType, int team) {
        return new BalliasBot(index);
    }
}
