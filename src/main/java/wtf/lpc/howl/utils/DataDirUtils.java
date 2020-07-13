package wtf.lpc.howl.utils;

import javax.swing.*;
import java.io.File;

public class DataDirUtils {
    private static File dataDir;

    public static File getDataDir() {
        if (dataDir == null) {
            File homeDir = new File(System.getProperty("user.home"));

            String operatingSystem = System.getProperty("os.name").toLowerCase();
            if (operatingSystem.startsWith("mac os x")) {
                dataDir = new File(homeDir, "Library/Application Support/Howl");
            } else if (operatingSystem.startsWith("windows")) {
                dataDir = new File(homeDir, "AppData\\Roaming\\lpc\\Howl");
            } else {
                dataDir = new File(homeDir, ".local/share/Howl");
            }
        }

        if (!dataDir.exists()) {
            if (!dataDir.mkdirs()) {
                JOptionPane.showMessageDialog(
                        null,
                        "Failed to create the data folder. Try again or ensure you have permission to access " +
                                "your home folder.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                System.exit(1);
            }
        }

        return dataDir;
    }

    public static File getSettingsFile() {
        return new File(getDataDir(), "settings.json");
    }
}
