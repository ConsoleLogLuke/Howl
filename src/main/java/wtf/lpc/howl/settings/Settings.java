package wtf.lpc.howl.settings;

import javafx.util.Pair;
import org.json.JSONException;
import org.json.JSONObject;
import wtf.lpc.howl.settings.enums.*;
import wtf.lpc.howl.settings.enums.Weather;
import wtf.lpc.howl.settings.meta.*;
import wtf.lpc.howl.ui.SettingsFrame;
import wtf.lpc.howl.utils.DataDirUtils;
import wtf.lpc.howl.utils.FileUtils;

import javax.swing.*;
import java.io.File;
import java.util.*;

public class Settings {
    private static final GroupMeta[] allGroups = {
            new Games(),
            new wtf.lpc.howl.settings.meta.Weather(),
            new KkTypes(),
            new KkSongTypes(),
            new RainSounds(),
            new CicadaSounds(),
            new CicadaTypes(),
            new FireworkSounds(),
            new Other()
    };

    private static List<Game> games;
    private static Weather weather;
    private static List<KkType> kkTypes;
    private static List<KkSongType> kkSongTypes;
    private static RainSound rainSounds;
    private static CicadaSound cicadaSounds;
    private static List<CicadaType> cicadaTypes;
    private static FireworkSound fireworkSounds;

    private static boolean grandfatherMode;
    private static boolean eventMusic;

    public static void saveDefaultSettings() {
        Map<String, Object> json = new HashMap<>();
        for (GroupMeta group : allGroups) {
            Object value = group.isMultiple() ? group.getDefaults() : group.getDefaults().get(0);
            json.put(group.getKey(), value);
        }

        File configFile = DataDirUtils.getSettingsFile();
        FileUtils.writeText(configFile, new JSONObject(json).toString());
    }

    private static <T> List<T> getMultipleSetting(String key, JSONObject json, List<Pair<String, T>> mappings) {
        Map<String, JCheckBox> checkBoxes = SettingsFrame.getInstance().getCheckBoxes();
        List<Object> settingJson = json.getJSONArray(key).toList();

        List<T> values = new ArrayList<>();
        for (Pair<String, T> mapping : mappings) {
            if (settingJson.contains(mapping.getKey())) {
                values.add(mapping.getValue());
                checkBoxes.get(key + "/" + mapping.getKey()).setSelected(true);
            } else {
                checkBoxes.get(key + "/" + mapping.getKey()).setSelected(false);
            }
        }

        return values;
    }

    private static <T> T getSetting(String key, JSONObject json, List<Pair<String, T>> mappings) {
        Map<String, JRadioButton> radioButtons = SettingsFrame.getInstance().getRadioButtons();
        String valueKey = json.getString(key);

        T value = mappings.get(0).getValue();
        for (Pair<String, T> mapping : mappings) {
            if (mapping.getKey().equals(valueKey)) {
                value = mapping.getValue();
                radioButtons.get(key + "/" + mapping.getKey()).setSelected(true);
            } else {
                radioButtons.get(key + "/" + mapping.getKey()).setSelected(false);
            }
        }

        return value;
    }

    public static void reloadSettings() {
        File settingsFile = DataDirUtils.getSettingsFile();
        if (!settingsFile.exists()) { saveDefaultSettings(); }

        JSONObject json = new JSONObject(FileUtils.readText(settingsFile));

        try {
            games = getMultipleSetting("games", json, Arrays.asList(
                    new Pair<>("animalCrossing", Game.ANIMAL_CROSSING),
                    new Pair<>("wildWorld", Game.WILD_WORLD),
                    new Pair<>("newLeaf", Game.NEW_LEAF),
                    new Pair<>("newHorizons", Game.NEW_HORIZONS)
            ));

            weather = getSetting("weather", json, Arrays.asList(
                    new Pair<>("auto", Weather.AUTO),
                    new Pair<>("clear", Weather.CLEAR),
                    new Pair<>("rainy", Weather.RAINY),
                    new Pair<>("snowy", Weather.SNOWY)
            ));

            kkTypes = getMultipleSetting("kkTypes", json, Arrays.asList(
                    new Pair<>("kkSlider", KkType.KK_SLIDER),
                    new Pair<>("djKk", KkType.DJ_KK)
            ));

            kkSongTypes = getMultipleSetting("kkSongTypes", json, Arrays.asList(
                    new Pair<>("aircheck", KkSongType.AIRCHECK),
                    new Pair<>("live", KkSongType.LIVE)
            ));

            rainSounds = getSetting("rainSounds", json, Arrays.asList(
                    new Pair<>("rainOutdoor", RainSound.RAIN_OUTDOOR),
                    new Pair<>("rainIndoor", RainSound.RAIN_INDOOR),
                    new Pair<>("thunderOutdoor", RainSound.THUNDER_OUTDOOR),
                    new Pair<>("thunderIndoor", RainSound.THUNDER_INDOOR),
                    new Pair<>("none", RainSound.NONE)
            ));

            cicadaSounds = getSetting("cicadaSounds", json, Arrays.asList(
                    new Pair<>("timed", CicadaSound.TIMED),
                    new Pair<>("always", CicadaSound.ALWAYS),
                    new Pair<>("none", CicadaSound.NONE)
            ));

            cicadaTypes = getMultipleSetting("cicadaTypes", json, Arrays.asList(
                    new Pair<>("brown", CicadaType.BROWN),
                    new Pair<>("evening", CicadaType.EVENING),
                    new Pair<>("giant", CicadaType.GIANT),
                    new Pair<>("robust", CicadaType.ROBUST),
                    new Pair<>("walker", CicadaType.WALKER)
            ));

            fireworkSounds = getSetting("fireworkSounds", json, Arrays.asList(
                    new Pair<>("duringShows", FireworkSound.DURING_SHOWS),
                    new Pair<>("always", FireworkSound.ALWAYS),
                    new Pair<>("none", FireworkSound.NONE)
            ));

            List<Object> otherValues = json.getJSONArray("other").toList();
            Map<String, JCheckBox> checkBoxes = SettingsFrame.getInstance().getCheckBoxes();

            grandfatherMode = otherValues.contains("grandfatherMode");
            checkBoxes.get("other/grandfatherMode").setSelected(grandfatherMode);

            eventMusic = otherValues.contains("eventMusic");
            checkBoxes.get("other/eventMusic").setSelected(eventMusic);
        } catch (JSONException e) {
            Map<String, Object> oldValues = json.toMap();
            saveDefaultSettings();

            Map<String, Object> newValues = new JSONObject(FileUtils.readText(settingsFile)).toMap();
            for (String key : oldValues.keySet()) {
                if (newValues.containsKey(key)) {
                    newValues.put(key, oldValues.get(key));
                }
            }

            JSONObject newJson = new JSONObject(newValues);
            FileUtils.writeText(settingsFile, newJson.toString());
            reloadSettings();
        }
    }

    public static GroupMeta[] getAllGroups() {
        return allGroups;
    }

    public static List<Game> getGames() {
        return games;
    }

    public static Weather getWeather() {
        return weather;
    }

    public static List<KkType> getKkTypes() {
        return kkTypes;
    }

    public static List<KkSongType> getKkSongTypes() {
        return kkSongTypes;
    }

    public static RainSound getRainSounds() {
        return rainSounds;
    }

    public static CicadaSound getCicadaSounds() {
        return cicadaSounds;
    }

    public static List<CicadaType> getCicadaTypes() {
        return cicadaTypes;
    }

    public static FireworkSound getFireworkSounds() {
        return fireworkSounds;
    }

    public static boolean isGrandfatherMode() {
        return grandfatherMode;
    }

    public static boolean isEventMusic() {
        return eventMusic;
    }
}
