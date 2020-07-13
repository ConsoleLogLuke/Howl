package wtf.lpc.howl.settings.meta;

public class CicadaTypes extends GroupMeta {
    public CicadaTypes() {
        super("cicadaTypes", "Cicada Types", "", true);

        addDefaultSetting("brown", "Brown Cicada", "");
        addDefaultSetting("evening", "Evening Cicada", "");
        addDefaultSetting("giant", "Giant Cicada", "");
        addDefaultSetting("robust", "Robust Cicada", "");
        addDefaultSetting("walker", "Walker Cicada", "");
    }
}
