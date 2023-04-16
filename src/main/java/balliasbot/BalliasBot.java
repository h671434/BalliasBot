package balliasbot;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import balliasbot.boost.BoostManager;
import balliasbot.controls.ControlsOutput;
import balliasbot.data.DataPacket;
import balliasbot.state.StateHandler;
import balliasbot.util.PortReader;
import balliasbot.util.SmartRenderer;
import rlbot.Bot;
import rlbot.ControllerState;
import rlbot.flat.GameTickPacket;
import rlbot.manager.BotManager;

public class BalliasBot implements Bot {

    private static final int DEFAULT_PORT = 17357;
    
    public final SmartRenderer renderer;
    public final StateHandler stateHandler;
    public final int playerIndex;

    public BalliasBot(int playerIndex) {
    	this.renderer = new SmartRenderer(playerIndex);
    	this.stateHandler = new StateHandler(this);
        this.playerIndex = playerIndex;
    }

    public static void main(String[] args) {
        BotManager botManager = new BotManager();
        int port = PortReader.readPortFromArgs(args).orElseGet(() -> {
            System.out.println("Could not read port from args, using default!");
            return DEFAULT_PORT;
        });

        BalliasPythonInterface pythonInterface = new BalliasPythonInterface(port, botManager);
        new Thread(pythonInterface::start).start();

        displayWindow(botManager, port);
    }
    
    private static void displayWindow(BotManager botManager, int port) {
        JFrame frame = new JFrame("Java Bot");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        BorderLayout borderLayout = new BorderLayout();
        panel.setLayout(borderLayout);
        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        dataPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
        dataPanel.add(new JLabel("Listening on port " + port), BorderLayout.CENTER);
        dataPanel.add(new JLabel("I'm the thing controlling the Java bot, keep me open :)"), BorderLayout.CENTER);
        JLabel botsRunning = new JLabel("Bots running: ");
        dataPanel.add(botsRunning, BorderLayout.CENTER);
        panel.add(dataPanel, BorderLayout.CENTER);
        frame.add(panel);

        URL url = BalliasBot.class.getClassLoader().getResource("icon.png");
        Image image = Toolkit.getDefaultToolkit().createImage(url);
        panel.add(new JLabel(new ImageIcon(image)), BorderLayout.WEST);
        frame.setIconImage(image);

        frame.pack();
        frame.setVisible(true);

        ActionListener myListener = e -> {
            Set<Integer> runningBotIndices = botManager.getRunningBotIndices();

            String botsStr;
            if (runningBotIndices.isEmpty()) {
                botsStr = "None";
            } else {
                botsStr = runningBotIndices.stream()
                        .sorted()
                        .map(i -> "#" + i)
                        .collect(Collectors.joining(", "));
            }
            botsRunning.setText("Bots indices running: " + botsStr);
        };

        new Timer(1000, myListener).start();
    }
    
    @Override
    public int getIndex() {
        return this.playerIndex;
    }

    @Override
    public ControllerState processInput(GameTickPacket packet) {
        if (packet.playersLength() <= playerIndex || packet.ball() == null || !packet.gameInfo().isRoundActive()) {
            return new ControlsOutput();
        }

        BoostManager.loadGameTickPacket(packet);
        
        DataPacket dataPacket = new DataPacket(packet, playerIndex);
      
        renderer.startPacket();
      
        ControlsOutput controls = stateHandler.execState(dataPacket);
        
        renderer.finishAndSendIfDifferent();
        
        return controls;
    }

    @Override
    public void retire() {
        System.out.println("Retiring sample bot " + playerIndex);
    }

}
