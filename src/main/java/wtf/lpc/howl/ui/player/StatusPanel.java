package wtf.lpc.howl.ui.player;

import wtf.lpc.howl.utils.ResourceUtils;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {
    private void addSeparator() {
        add(Box.createRigidArea(new Dimension(10, 0)));

        JLabel separatorLabel = new JLabel("|");
        separatorLabel.setFont(separatorLabel.getFont().deriveFont(Font.PLAIN, 18));
        add(separatorLabel);

        add(Box.createRigidArea(new Dimension(10, 0)));
    }

    public StatusPanel() {
        ImageIcon pauseIcon = ResourceUtils.getIcon("pause.png", 24, 24);
        ImageIcon playIcon = ResourceUtils.getIcon("play.png", 24, 24);

        ImageIcon clearDayIcon = ResourceUtils.getIcon("clear-day.png", 24, 24);
        ImageIcon clearNightIcon = ResourceUtils.getIcon("clear-night.png", 24, 24);
        ImageIcon rainyIcon = ResourceUtils.getIcon("rainy.png", 24, 24);
        ImageIcon snowyIcon = ResourceUtils.getIcon("snowy.png", 24, 24);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        JButton pausePlayButton = new JButton(pauseIcon);
        pausePlayButton.setBorder(BorderFactory.createEmptyBorder());
        pausePlayButton.setContentAreaFilled(false);
        add(pausePlayButton);

        add(Box.createRigidArea(new Dimension(5, 0)));

        JLabel timeLabel = new JLabel("8 PM");
        timeLabel.setFont(timeLabel.getFont().deriveFont(Font.BOLD, 18));
        add(timeLabel);

        addSeparator();

        JLabel weatherLabel = new JLabel(rainyIcon);
        add(weatherLabel);

        addSeparator();

        JLabel typeLabel = new JLabel("NL");
        typeLabel.setFont(typeLabel.getFont().deriveFont(Font.PLAIN, 18));
        add(typeLabel);
    }
}
