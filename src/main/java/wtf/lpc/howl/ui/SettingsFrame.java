package wtf.lpc.howl.ui;

import org.json.JSONObject;
import wtf.lpc.howl.settings.Settings;
import wtf.lpc.howl.settings.meta.GroupMeta;
import wtf.lpc.howl.settings.meta.SettingMeta;
import wtf.lpc.howl.utils.DataDirUtils;
import wtf.lpc.howl.utils.FileUtils;
import wtf.lpc.howl.utils.HowlFrame;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class SettingsFrame extends HowlFrame {
    private static SettingsFrame instance;

    private final Map<String, JCheckBox> checkBoxes = new HashMap<>();
    private final Map<String, JRadioButton> radioButtons = new HashMap<>();

    private Map<String, Object> readSettings() {
        File settingsFile = DataDirUtils.getSettingsFile();
        JSONObject json = new JSONObject(FileUtils.readText(settingsFile));

        return json.toMap();
    }

    private void writeSettings(Map<String, Object> newSettings) {
        File settingsFile = DataDirUtils.getSettingsFile();
        JSONObject json = new JSONObject(newSettings);

        FileUtils.writeText(settingsFile, json.toString());
    }

    private SettingsFrame() {
        super("Settings", HowlFrame.HIDE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        add(panel, BorderLayout.LINE_START);

        GroupMeta[] groups = Settings.getAllGroups();
        List<GroupMeta[]> columns = new ArrayList<>();

        final int columnGroupCount = 3;
        for (int i = 0; i < groups.length; i += columnGroupCount) {
            int to = Math.min(groups.length, i + columnGroupCount);
            columns.add(Arrays.copyOfRange(groups, i, to));
        }

        for (GroupMeta[] column : columns) {
            JPanel columnPanel = new JPanel();
            columnPanel.setLayout(new BoxLayout(columnPanel, BoxLayout.Y_AXIS));
            columnPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            columnPanel.setAlignmentY(Component.TOP_ALIGNMENT);
            panel.add(columnPanel);

            int currentIndex = 0;
            for (GroupMeta group : column) {
                JLabel groupLabel = new JLabel(group.getDisplayName());
                groupLabel.setFont(groupLabel.getFont().deriveFont(Font.BOLD, 14));
                columnPanel.add(groupLabel);

                if (group.isMultiple()) {
                    for (String key : group.getSettings().keySet()) {
                        SettingMeta setting = group.getSettings().get(key);
                        JCheckBox checkBox = new JCheckBox(setting.getDisplayName());

                        checkBox.addActionListener(e -> {
                            Map<String, Object> settings = readSettings();

                            List<?> valuesAny = (List<?>) settings.get(group.getKey());
                            List<String> values = valuesAny.stream().map(obj -> (String) obj)
                                    .collect(Collectors.toList());

                            if (checkBox.isSelected()) { values.add(key); }
                            else { values.remove(key); }

                            settings.put(group.getKey(), values);

                            writeSettings(settings);
                            Settings.reloadSettings();
                        });

                        checkBoxes.put(group.getKey() + "/" + key, checkBox);
                        columnPanel.add(checkBox);
                    }
                } else {
                    ButtonGroup buttonGroup = new ButtonGroup();

                    for (String key : group.getSettings().keySet()) {
                        SettingMeta setting = group.getSettings().get(key);
                        JRadioButton radioButton = new JRadioButton(setting.getDisplayName());

                        radioButton.addActionListener(e -> {
                            Map<String, Object> settings = readSettings();
                            settings.put(group.getKey(), key);

                            writeSettings(settings);
                            Settings.reloadSettings();
                        });

                        radioButtons.put(group.getKey() + "/" + key, radioButton);
                        buttonGroup.add(radioButton);
                        columnPanel.add(radioButton);
                    }
                }

                if (currentIndex < column.length - 1) {
                    columnPanel.add(Box.createRigidArea(new Dimension(0, 15)));
                }
            }
        }
    }

    public Map<String, JCheckBox> getCheckBoxes() {
        return checkBoxes;
    }

    public Map<String, JRadioButton> getRadioButtons() {
        return radioButtons;
    }

    public static SettingsFrame getInstance() {
        if (instance == null) { instance = new SettingsFrame(); }
        return instance;
    }
}
