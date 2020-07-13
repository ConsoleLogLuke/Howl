package wtf.lpc.howl.settings.meta;

import java.util.*;

public class GroupMeta extends SettingMeta {
    private final String key;
    private final boolean multiple;
    private final Map<String, SettingMeta> settings = new LinkedHashMap<>();
    private final List<String> defaults = new ArrayList<>();

    public GroupMeta(String key, String displayName, String toolTipText, boolean multiple) {
        super(displayName, toolTipText);

        this.key = key;
        this.multiple = multiple;
    }

    public void addSetting(String key, String displayName, String toolTipText) {
        SettingMeta meta = new SettingMeta(displayName, toolTipText);
        settings.put(key, meta);
    }

    public void addDefaultSetting(String key, String displayName, String toolTipText) {
        addSetting(key, displayName, toolTipText);
        defaults.add(key);
    }

    public String getKey() {
        return key;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public Map<String, SettingMeta> getSettings() {
        return settings;
    }

    public List<String> getDefaults() {
        return defaults;
    }
}
