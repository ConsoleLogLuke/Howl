package wtf.lpc.howl;

import com.formdev.flatlaf.FlatDarkLaf;
import wtf.lpc.howl.settings.Settings;
import wtf.lpc.howl.ui.SettingsFrame;
import wtf.lpc.howl.ui.player.PlayerFrame;

public class Main {
    public static void main(String[] args) {
        System.setProperty("apple.awt.application.name", "Howl");
        FlatDarkLaf.install();

        SettingsFrame.getInstance();
        Settings.reloadSettings();

        PlayerFrame.getInstance().howlShow();
    }
}
