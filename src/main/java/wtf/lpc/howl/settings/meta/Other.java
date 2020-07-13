package wtf.lpc.howl.settings.meta;

public class Other extends GroupMeta {
    public Other() {
        super("other", "Other", "", true);

        addSetting("grandfatherMode", "Grandfather Mode", "");
        addDefaultSetting("eventMusic", "Event Music", "");
    }
}
