package wtf.lpc.howl.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HowlFrame extends JFrame {
    public static byte EXIT_ON_CLOSE = 0;
    public static byte HIDE_ON_CLOSE = 1;

    private Point initialClick;

    public HowlFrame(String title, byte closeOperation, int width, int height) {
        super(title);

        ImageIcon closeIcon = ResourceUtils.getIcon("close.png", 20, 20);
        ImageIcon minimizeIcon = ResourceUtils.getIcon("minimize.png", 20, 20);

        setSize(width, height);
        setUndecorated(true);
        setResizable(false);
        setLayout(new BorderLayout());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int movedX = e.getX() - initialClick.x;
                int movedY = e.getY() - initialClick.y;

                setLocation(
                        getLocation().x + movedX,
                        getLocation().y + movedY
                );
            }
        });

        JPanel windowButtonPanel = new JPanel();
        windowButtonPanel.setLayout(new BoxLayout(windowButtonPanel, BoxLayout.X_AXIS));
        windowButtonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));
        add(windowButtonPanel, BorderLayout.PAGE_START);

        JButton closeButton = new JButton(closeIcon);
        closeButton.setBorder(BorderFactory.createEmptyBorder());
        closeButton.setContentAreaFilled(false);

        closeButton.addActionListener(e -> {
            if (closeOperation == EXIT_ON_CLOSE) {
                System.exit(0);
            } else if (closeOperation == HIDE_ON_CLOSE) {
                setVisible(false);
            }
        });

        windowButtonPanel.add(closeButton);

        JButton minimizeButton = new JButton(minimizeIcon);
        minimizeButton.setBorder(BorderFactory.createEmptyBorder());
        minimizeButton.setContentAreaFilled(false);
        minimizeButton.addActionListener(e -> setState(Frame.ICONIFIED));
        windowButtonPanel.add(minimizeButton);
    }

    public HowlFrame(String title, byte closeOperation) {
        this(title, closeOperation, 0, 0);
    }

    public void howlShow() {
        if (getWidth() == 0 && getHeight() == 0) { pack(); }
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
