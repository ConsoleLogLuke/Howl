package wtf.lpc.howl.settings.meta;

public class SettingMeta {
    private final String displayName;
    private final String toolTipText;

    public SettingMeta(String displayName, String toolTipText) {
        this.displayName = displayName;
        this.toolTipText = toolTipText;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getToolTipText() {
        return toolTipText;
    }
}
