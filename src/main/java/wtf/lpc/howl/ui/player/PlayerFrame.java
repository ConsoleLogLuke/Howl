package wtf.lpc.howl.ui.player;

import wtf.lpc.howl.ui.SettingsFrame;
import wtf.lpc.howl.utils.HowlFrame;
import wtf.lpc.howl.utils.ResourceUtils;

import javax.swing.*;
import java.awt.*;

public class PlayerFrame extends HowlFrame {
    private static PlayerFrame instance;

    private PlayerFrame() {
        super("Howl", HowlFrame.EXIT_ON_CLOSE, 300, 80);

        ImageIcon settingsIcon = ResourceUtils.getIcon("settings.png", 24, 24);

        StatusPanel statusPanel = new StatusPanel();
        add(statusPanel, BorderLayout.LINE_START);

        JButton settingsButton = new JButton(settingsIcon);
        settingsButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
        settingsButton.setContentAreaFilled(false);
        settingsButton.addActionListener(e -> SettingsFrame.getInstance().howlShow());
        add(settingsButton, BorderLayout.LINE_END);
    }

    public static PlayerFrame getInstance() {
        if (instance == null) { instance = new PlayerFrame(); }
        return instance;
    }
}
