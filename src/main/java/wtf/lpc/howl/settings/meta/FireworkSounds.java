package wtf.lpc.howl.settings.meta;

public class FireworkSounds extends GroupMeta {
    public FireworkSounds() {
        super("fireworkSounds", "Firework Sounds", "", false);

        addDefaultSetting("duringShows", "During Firework Shows", "");
        addSetting("always", "Always", "");
        addSetting("none", "No Firework Sounds", "");
    }
}
