package wtf.lpc.howl.settings.meta;

public class RainSounds extends GroupMeta {
    public RainSounds() {
        super("rainSounds", "Rain Sounds", "", false);

        addDefaultSetting("rainOutdoor", "Rain (Outdoor)", "");
        addSetting("rainIndoor", "Rain (Indoor)", "");
        addSetting("thunderOutdoor", "Rain + Thunder (Outdoor)", "");
        addSetting("thunderIndoor", "Rain + Thunder (Indoor)", "");
        addSetting("none", "No Rain Sounds", "");
    }
}
