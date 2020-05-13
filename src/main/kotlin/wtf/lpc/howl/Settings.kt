package wtf.lpc.howl

import java.awt.Component
import java.awt.Dimension
import java.awt.Font
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import javax.swing.*

lateinit var settingsFrame: JFrame
lateinit var settingsPanel: JPanel
lateinit var leftPanel: JPanel
lateinit var centerPanel: JPanel
lateinit var rightPanel: JPanel

lateinit var gamesSectionLabel: JLabel
lateinit var animalCrossingCheckBox: JCheckBox
lateinit var wildWorldCheckBox: JCheckBox
lateinit var newLeafCheckBox: JCheckBox
lateinit var newHorizonsCheckBox: JCheckBox

lateinit var weatherSectionLabel: JLabel
lateinit var weatherButtonGroup: ButtonGroup
lateinit var autoWeatherButton: JRadioButton
lateinit var sunnyWeatherButton: JRadioButton
lateinit var rainyWeatherButton: JRadioButton
lateinit var snowyWeatherButton: JRadioButton

lateinit var kkSliderSectionLabel: JLabel
lateinit var kkSliderCheckBox: JCheckBox
lateinit var djKkCheckBox: JCheckBox

lateinit var kkSongTypesSectionLabel: JLabel
lateinit var aircheckCheckBox: JCheckBox
lateinit var liveCheckBox: JCheckBox

lateinit var rainSoundsSectionLabel: JLabel
lateinit var rainSoundsButtonGroup: ButtonGroup
lateinit var outdoorRainButton: JRadioButton
lateinit var indoorRainButton: JRadioButton
lateinit var outdoorThunderButton: JRadioButton
lateinit var indoorThunderButton: JRadioButton
lateinit var noRainSoundsButton: JRadioButton

lateinit var cicadaSoundsSectionLabel: JLabel
lateinit var cicadaSoundsButtonGroup: ButtonGroup
lateinit var timedCicadaSoundsButton: JRadioButton
lateinit var alwaysCicadaSoundsButton: JRadioButton
lateinit var noCicadaSoundsButton: JRadioButton

lateinit var cicadaTypesLabel: JLabel
lateinit var brownCicadaCheckBox: JCheckBox
lateinit var eveningCicadaCheckBox: JCheckBox
lateinit var giantCicadaCheckBox: JCheckBox
lateinit var robustCicadaCheckBox: JCheckBox
lateinit var walkerCicadaCheckBox: JCheckBox

lateinit var fireworkSoundsSectionLabel: JLabel
lateinit var fireworkSoundsButtonGroup: ButtonGroup
lateinit var duringFireworkShowsButton: JRadioButton
lateinit var alwaysFireworksButton: JRadioButton
lateinit var noFireworkSoundsButton: JRadioButton

lateinit var otherSectionLabel: JLabel
lateinit var grandfatherModeCheckBox: JCheckBox
lateinit var eventSongsCheckBox: JCheckBox

lateinit var hoverLabel: JLabel

@Suppress("DuplicatedCode")
fun initSettings() {
    settingsFrame = JFrame("Settings")
    settingsFrame.defaultCloseOperation = JFrame.HIDE_ON_CLOSE
    settingsFrame.isResizable = false
    settingsFrame.layout = BoxLayout(settingsFrame.contentPane, BoxLayout.Y_AXIS)
    settingsFrame.addComponentListener(object : ComponentAdapter() {
        override fun componentHidden(e: ComponentEvent) = saveSettings()
    })

    settingsPanel = JPanel()
    settingsPanel.layout = BoxLayout(settingsPanel, BoxLayout.X_AXIS)
    settingsFrame.add(settingsPanel)

    leftPanel = JPanel()
    leftPanel.layout = BoxLayout(leftPanel, BoxLayout.Y_AXIS)
    leftPanel.border = BorderFactory.createEmptyBorder(15, 15, 15, 15)
    leftPanel.alignmentY = Component.TOP_ALIGNMENT
    settingsPanel.add(leftPanel)

    gamesSectionLabel = JLabel("Games")
    gamesSectionLabel.font = gamesSectionLabel.font.deriveFont(Font.BOLD, 14f)
    gamesSectionLabel.toolTipText = "Choose which games you want the hourly music to be from.\n" +
            "If multiple games are selected, a random game from your selection will be chosen for each song.\n" +
            "This setting does not apply to event music."
    leftPanel.add(gamesSectionLabel)

    animalCrossingCheckBox = JCheckBox("Animal Crossing")
    animalCrossingCheckBox.toolTipText = "Music from the original GameCube game."
    leftPanel.add(animalCrossingCheckBox)

    wildWorldCheckBox = JCheckBox("Wild World / City Folk")
    wildWorldCheckBox.toolTipText = "Music from the DS (Wild World) and Wii (City Folk) games.\n" +
            "Both games share the same hourly music."
    leftPanel.add(wildWorldCheckBox)

    newLeafCheckBox = JCheckBox("New Leaf")
    newLeafCheckBox.toolTipText = "Music from the 3DS game."
    leftPanel.add(newLeafCheckBox)

    newHorizonsCheckBox = JCheckBox("New Horizons")
    newHorizonsCheckBox.toolTipText = "Music from the Switch game, the latest in the series."
    leftPanel.add(newHorizonsCheckBox)

    leftPanel.add(Box.createRigidArea(Dimension(0, 15)))

    weatherSectionLabel = JLabel("Weather")
    weatherSectionLabel.font = weatherSectionLabel.font.deriveFont(Font.BOLD, 14f)
    weatherSectionLabel.toolTipText = "Choose which weather condition you want the hourly music to be from.\n" +
            "This setting does not apply to event music."
    leftPanel.add(weatherSectionLabel)

    weatherButtonGroup = ButtonGroup()

    autoWeatherButton = JRadioButton("Automatic")
    autoWeatherButton.toolTipText = "Automatically determine the current weather condition.\n" +
            "This setting uses your IP address; using cellular data, a VPN, or a proxy may cause inaccuracies."
    weatherButtonGroup.add(autoWeatherButton)
    leftPanel.add(autoWeatherButton)

    sunnyWeatherButton = JRadioButton("Sunny")
    sunnyWeatherButton.toolTipText = "Use sunny music."
    weatherButtonGroup.add(sunnyWeatherButton)
    leftPanel.add(sunnyWeatherButton)

    rainyWeatherButton = JRadioButton("Rainy")
    rainyWeatherButton.toolTipText = "Use rainy music."
    weatherButtonGroup.add(rainyWeatherButton)
    leftPanel.add(rainyWeatherButton)

    snowyWeatherButton = JRadioButton("Snowy")
    snowyWeatherButton.toolTipText = "Use snowy music."
    weatherButtonGroup.add(snowyWeatherButton)
    leftPanel.add(snowyWeatherButton)

    leftPanel.add(Box.createRigidArea(Dimension(0, 15)))

    kkSliderSectionLabel = JLabel("K.K. Slider")
    kkSliderSectionLabel.font = kkSliderSectionLabel.font.deriveFont(Font.BOLD, 14f)
    kkSliderSectionLabel.toolTipText = "Choose which type(s) of K.K. Slider you want to perform in the evening.\n" +
            "If multiple types are selected, a random type from your selection will be chosen for each song."
    leftPanel.add(kkSliderSectionLabel)

    kkSliderCheckBox = JCheckBox("K.K. Slider")
    kkSliderCheckBox.toolTipText = "K.K. Slider will perform from 8 PM to 12 AM on Saturdays."
    leftPanel.add(kkSliderCheckBox)

    djKkCheckBox = JCheckBox("DJ K.K.")
    djKkCheckBox.toolTipText = "DJ K.K. will perform from 8 PM to 12 AM on Sunday to Friday.\n" +
            "If K.K. Slider is not selected, DJ K.K. will perform on Saturday nights as well."
    leftPanel.add(djKkCheckBox)

    centerPanel = JPanel()
    centerPanel.layout = BoxLayout(centerPanel, BoxLayout.Y_AXIS)
    centerPanel.border = BorderFactory.createEmptyBorder(15, 15, 15, 15)
    centerPanel.alignmentY = Component.TOP_ALIGNMENT
    settingsPanel.add(centerPanel)

    kkSongTypesSectionLabel = JLabel("K.K. Song Types")
    kkSongTypesSectionLabel.font = kkSongTypesSectionLabel.font.deriveFont(Font.BOLD, 14f)
    kkSongTypesSectionLabel.toolTipText = "Choose which type of song you want K.K. Slider to perform.\n" +
            "If multiple types are selected, a random type from your selection will be chosen for each song.\n" +
            "This setting does not apply for DJ K.K."
    centerPanel.add(kkSongTypesSectionLabel)

    aircheckCheckBox = JCheckBox("Aircheck")
    aircheckCheckBox.toolTipText = "K.K. Slider will perform aircheck songs.\n" +
            "Aircheck songs are the stereo versions of the songs, not the live performances.\n" +
            "Aircheck songs are also referred to as bootleg or boot songs in certain games."
    centerPanel.add(aircheckCheckBox)

    liveCheckBox = JCheckBox("Live")
    liveCheckBox.toolTipText = "K.K. Slider will perform live songs.\n" +
            "Live songs are the live performances of the songs, not the stereo versions."
    centerPanel.add(liveCheckBox)

    centerPanel.add(Box.createRigidArea(Dimension(0, 15)))

    rainSoundsSectionLabel = JLabel("Rain Sounds")
    rainSoundsSectionLabel.font = rainSoundsSectionLabel.font.deriveFont(Font.BOLD, 14f)
    rainSoundsSectionLabel.toolTipText = "Choose whether you want rain sounds or not, and which type of rain sound.\n" +
            "Rain sounds will only be played when the weather is rainy."
    centerPanel.add(rainSoundsSectionLabel)

    rainSoundsButtonGroup = ButtonGroup()

    outdoorRainButton = JRadioButton("Rain (Outdoor)")
    outdoorRainButton.toolTipText = "Play the sound of rain as if you were outside."
    rainSoundsButtonGroup.add(outdoorRainButton)
    centerPanel.add(outdoorRainButton)

    indoorRainButton = JRadioButton("Rain (Indoor)")
    indoorRainButton.toolTipText = "Play the sound of rain as if you were inside your home."
    rainSoundsButtonGroup.add(indoorRainButton)
    centerPanel.add(indoorRainButton)

    outdoorThunderButton = JRadioButton("Rain + Thunder (Outdoor)")
    outdoorThunderButton.toolTipText = "Play the sound of rain, with occasional thunder claps, as if you were outside."
    rainSoundsButtonGroup.add(outdoorThunderButton)
    centerPanel.add(outdoorThunderButton)

    indoorThunderButton = JRadioButton("Rain + Thunder (Indoor)")
    indoorThunderButton.toolTipText = "Play the sound of rain, with occasional thunder claps, as if you were inside " +
            "your home."
    rainSoundsButtonGroup.add(indoorThunderButton)
    centerPanel.add(indoorThunderButton)

    noRainSoundsButton = JRadioButton("No Rain Sounds")
    noRainSoundsButton.toolTipText = "Don't play rain sounds at all."
    rainSoundsButtonGroup.add(noRainSoundsButton)
    centerPanel.add(noRainSoundsButton)

    centerPanel.add(Box.createRigidArea(Dimension(0, 15)))

    cicadaSoundsSectionLabel = JLabel("Cicada Sounds")
    cicadaSoundsSectionLabel.font = cicadaSoundsSectionLabel.font.deriveFont(Font.BOLD, 14f)
    cicadaSoundsSectionLabel.toolTipText = "Choose whether or not to play cicada sounds, found in-game during the " +
            "summer."
    centerPanel.add(cicadaSoundsSectionLabel)

    cicadaSoundsButtonGroup = ButtonGroup()

    timedCicadaSoundsButton = JRadioButton("Timed")
    timedCicadaSoundsButton.toolTipText = "Play cicada sounds during the times they appear in-game.\n" +
            "This setting only applies to the time of day, not the time of year."
    cicadaSoundsButtonGroup.add(timedCicadaSoundsButton)
    centerPanel.add(timedCicadaSoundsButton)

    alwaysCicadaSoundsButton = JRadioButton("Always")
    alwaysCicadaSoundsButton.toolTipText = "Always play cicada sounds."
    cicadaSoundsButtonGroup.add(alwaysCicadaSoundsButton)
    centerPanel.add(alwaysCicadaSoundsButton)

    noCicadaSoundsButton = JRadioButton("No Cicada Sounds")
    noCicadaSoundsButton.toolTipText = "Don't play cicada sounds at all."
    cicadaSoundsButtonGroup.add(noCicadaSoundsButton)
    centerPanel.add(noCicadaSoundsButton)

    rightPanel = JPanel()
    rightPanel.layout = BoxLayout(rightPanel, BoxLayout.Y_AXIS)
    rightPanel.border = BorderFactory.createEmptyBorder(15, 15, 15, 15)
    rightPanel.alignmentY = Component.TOP_ALIGNMENT
    settingsPanel.add(rightPanel)

    cicadaTypesLabel = JLabel("Cicada Types")
    cicadaTypesLabel.font = cicadaTypesLabel.font.deriveFont(Font.BOLD, 14f)
    cicadaTypesLabel.toolTipText = "Choose which cicada types you want to hear.\n" +
            "These settings only take effect if cicada sounds are enabled."
    rightPanel.add(cicadaTypesLabel)

    brownCicadaCheckBox = JCheckBox("Brown Cicada")
    brownCicadaCheckBox.toolTipText = "Play brown cicada sounds.\n" +
            "If cicada sounds are set to timed, this cicada can be heard from 8 AM to 5 PM."
    rightPanel.add(brownCicadaCheckBox)

    eveningCicadaCheckBox = JCheckBox("Evening Cicada")
    eveningCicadaCheckBox.toolTipText = "Play evening cicada sounds.\n" +
            "If cicada sounds are set to timed, this cicada can be heard from 4 AM to 8 AM, and 4 PM to 7 PM."
    rightPanel.add(eveningCicadaCheckBox)

    giantCicadaCheckBox = JCheckBox("Giant Cicada")
    giantCicadaCheckBox.toolTipText = "Play giant cicada sounds.\n" +
            "If cicada sounds are set to timed, this cicada can be heard from 8 AM to 5 PM."
    rightPanel.add(giantCicadaCheckBox)

    robustCicadaCheckBox = JCheckBox("Robust Cicada")
    robustCicadaCheckBox.toolTipText = "Play robust cicada sounds.\n" +
            "If cicada sounds are set to timed, this cicada can be heard from 8 AM to 5 PM."
    rightPanel.add(robustCicadaCheckBox)

    walkerCicadaCheckBox = JCheckBox("Walker Cicada")
    walkerCicadaCheckBox.toolTipText = "Play walker cicada sounds.\n" +
            "If cicada sounds are set to timed, this cicada can be heard from 8 AM to 5 PM."
    rightPanel.add(walkerCicadaCheckBox)

    rightPanel.add(Box.createRigidArea(Dimension(0, 15)))

    fireworkSoundsSectionLabel = JLabel("Firework Sounds")
    fireworkSoundsSectionLabel.font = fireworkSoundsSectionLabel.font.deriveFont(Font.BOLD, 14f)
    fireworkSoundsSectionLabel.toolTipText = "Choose whether or not to play firework sounds, and when."
    rightPanel.add(fireworkSoundsSectionLabel)

    fireworkSoundsButtonGroup = ButtonGroup()

    duringFireworkShowsButton = JRadioButton("During Firework Shows")
    duringFireworkShowsButton.toolTipText = "Play firework sounds during firework shows.\n" +
            "This setting only takes effect if event songs are enabled."
    fireworkSoundsButtonGroup.add(duringFireworkShowsButton)
    rightPanel.add(duringFireworkShowsButton)

    alwaysFireworksButton = JRadioButton("Always")
    alwaysFireworksButton.toolTipText = "Always play firework sounds."
    fireworkSoundsButtonGroup.add(alwaysFireworksButton)
    rightPanel.add(alwaysFireworksButton)

    noFireworkSoundsButton = JRadioButton("No Firework Sounds")
    noFireworkSoundsButton.toolTipText = "Don't play firework sounds at all."
    fireworkSoundsButtonGroup.add(noFireworkSoundsButton)
    rightPanel.add(noFireworkSoundsButton)

    rightPanel.add(Box.createRigidArea(Dimension(0, 15)))

    otherSectionLabel = JLabel("Other")
    otherSectionLabel.font = otherSectionLabel.font.deriveFont(Font.BOLD, 14f)
    otherSectionLabel.toolTipText = "Miscellaneous settings that don't fit into any other categories."
    rightPanel.add(otherSectionLabel)

    grandfatherModeCheckBox = JCheckBox("Grandfather Mode")
    grandfatherModeCheckBox.toolTipText = "Rather than looping the music, play each song fully only once per hour.\n" +
            "This setting is similar to a grandfather clock's chimes, hence the name."
    rightPanel.add(grandfatherModeCheckBox)

    eventSongsCheckBox = JCheckBox("Event Songs")
    eventSongsCheckBox.toolTipText = "Play the event songs on the days of in-game events.\n" +
            "All event songs are ripped from New Leaf, and the game setting doesn't affect event music.\n" +
            "These events are New Year's Day, Festivale, Bunny Day, Fireworks Shows, Halloween, Harvest Festival, " +
            "Toy Day, and New Year's Eve.\n" +
            "K.K. Slider will take priority if the event is all day, otherwise the event will take priority."
    rightPanel.add(eventSongsCheckBox)

    hoverLabel = JLabel("Hover over any section title or setting to get a detailed description of it.")
    hoverLabel.border = BorderFactory.createEmptyBorder(5, 5, 5, 5)
    hoverLabel.alignmentX = Component.CENTER_ALIGNMENT
    hoverLabel.toolTipText = "Like that! :)"
    settingsFrame.add(hoverLabel)

    settingsFrame.pack()
    settingsFrame.setLocationRelativeTo(null)
}

fun showSettings() { settingsFrame.isVisible = true }
