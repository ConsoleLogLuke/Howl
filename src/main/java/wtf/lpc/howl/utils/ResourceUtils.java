package wtf.lpc.howl.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ResourceUtils {
    private static final Map<String, ImageIcon> iconCache = new HashMap<>();

    public static ImageIcon getIcon(String name, int width, int height) {
        if (iconCache.containsKey(name)) { return iconCache.get(name); }

        URL resource = ResourceUtils.class.getClassLoader().getResource("icons/" + name);
        if (resource == null) { return null; }

        BufferedImage image;

        try {
            image = ImageIO.read(resource);
        } catch (IOException e) {
            return null;
        }

        Image scaled = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        ImageIcon imageIcon = new ImageIcon(scaled);
        iconCache.put(name, imageIcon);
        return imageIcon;
    }
}
