package wtf.lpc.howl.settings.meta;

public class Weather extends GroupMeta {
    public Weather() {
        super("weather", "Weather", "", false);

        addDefaultSetting("auto", "Automatic", "");
        addSetting("clear", "Clear", "");
        addSetting("rainy", "Rainy", "");
        addSetting("snowy", "Snowy", "");
    }
}
