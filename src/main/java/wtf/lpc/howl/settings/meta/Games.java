package wtf.lpc.howl.settings.meta;

public class Games extends GroupMeta {
    public Games() {
        super("games", "Games", "", true);

        addSetting("animalCrossing", "Animal Crossing", "");
        addSetting("wildWorld", "Wild World / City Folk", "");
        addSetting("newLeaf", "New Leaf", "");
        addDefaultSetting("newHorizons", "New Horizons", "");
    }
}
