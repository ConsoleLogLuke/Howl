package wtf.lpc.howl.settings.meta;

public class KkSongTypes extends GroupMeta {
    public KkSongTypes() {
        super("kkSongTypes", "K.K. Song Types", "", true);

        addSetting("aircheck", "Aircheck", "");
        addDefaultSetting("live", "Live", "");
    }
}
