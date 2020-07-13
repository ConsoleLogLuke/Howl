package wtf.lpc.howl.settings.meta;

public class CicadaSounds extends GroupMeta {
    public CicadaSounds() {
        super("cicadaSounds", "Cicada Sounds", "", false);

        addSetting("timed", "Timed", "");
        addSetting("always", "Always", "");
        addDefaultSetting("none", "No Cicada Sounds", "");
    }
}
