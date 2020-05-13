package wtf.lpc.howl

import com.formdev.flatlaf.FlatDarkLaf
import javafx.embed.swing.JFXPanel
import net.harawata.appdirs.AppDirsFactory
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter
import java.io.File
import javax.imageio.ImageIO
import javax.swing.*
import kotlin.system.exitProcess

lateinit var frame: JFrame
lateinit var statusPanel: JPanel
lateinit var windowButtonPanel: JPanel

lateinit var timeLabel: JLabel
lateinit var timeSeparatorLabel: JLabel
lateinit var weatherLabel: JLabel
lateinit var weatherSeparatorLabel: JLabel
lateinit var gameLabel: JLabel

lateinit var playPauseButton: JButton
lateinit var closeButton: JButton
lateinit var minimizeButton: JButton
lateinit var settingsButton: JButton

lateinit var clearNightIcon: ImageIcon
lateinit var closeIcon: ImageIcon
lateinit var djKkIcon: ImageIcon
lateinit var kkSliderIcon: ImageIcon
lateinit var minimizeIcon: ImageIcon
lateinit var pauseIcon: ImageIcon
lateinit var playIcon: ImageIcon
lateinit var rainyIcon: ImageIcon
lateinit var settingsIcon: ImageIcon
lateinit var snowyIcon: ImageIcon
lateinit var sunnyIcon: ImageIcon

lateinit var initialClick: Point

val dataDir = File(AppDirsFactory.getInstance()
    .getUserDataDir("Howl", null, "lpc", true))

@Suppress("DuplicatedCode")
fun main() {
    FlatDarkLaf.install()
    ToolTipManager.sharedInstance().dismissDelay = Integer.MAX_VALUE
    JFXPanel()

    frame = JFrame()
    frame.setSize(300, 80)
    frame.setLocationRelativeTo(null)
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.isUndecorated = true
    frame.isResizable = false
    frame.layout = BorderLayout()

    frame.addMouseListener(object : MouseAdapter() {
        override fun mousePressed(e: MouseEvent) { initialClick = e.point }
    })

    frame.addMouseMotionListener(object : MouseMotionAdapter() {
        override fun mouseDragged(e: MouseEvent) {
            val xMoved = e.x - initialClick.x
            val yMoved = e.y - initialClick.y
            frame.setLocation(frame.location.x + xMoved, frame.location.y + yMoved)
        }
    })

    if (!dataDir.exists()) dataDir.mkdirs()
    initSettings()

    while (true) {
        try {
            loadSettings()
            break
        } catch (e: Exception) {
            e.printStackTrace()
            val resetConfirm = JOptionPane.showConfirmDialog(
                frame,
                "An error occurred while loading your settings. Would you like to reset your settings, which " +
                        "would likely fix the issue?",
                "Settings Error",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE
            )
            if (resetConfirm == JOptionPane.YES_OPTION) settingsFile.delete()
            else exitProcess(1)
        }
    }

    clearNightIcon = getIcon("clearNight.png", 24, 24)
    closeIcon = getIcon("close.png", 20, 20)
    djKkIcon = getIcon("djKk.png", 24, 24)
    kkSliderIcon = getIcon("kkSlider.png", 24, 24)
    minimizeIcon = getIcon("minimize.png", 20, 20)
    pauseIcon = getIcon("pause.png", 24, 24)
    playIcon = getIcon("play.png", 24, 24)
    rainyIcon = getIcon("rainy.png", 24, 24)
    settingsIcon = getIcon("settings.png", 24, 24)
    snowyIcon = getIcon("snowy.png", 24, 24)
    sunnyIcon = getIcon("sunny.png", 24, 24)

    statusPanel = JPanel()
    statusPanel.layout = BoxLayout(statusPanel, BoxLayout.X_AXIS)
    statusPanel.border = BorderFactory.createEmptyBorder(0, 10, 0, 0)
    frame.add(statusPanel, BorderLayout.LINE_START)

    playPauseButton = JButton(pauseIcon)
    playPauseButton.border = BorderFactory.createEmptyBorder()
    playPauseButton.isContentAreaFilled = false
    playPauseButton.addActionListener {
        if (paused) {
            playPauseButton.icon = pauseIcon
            playPauseButton.toolTipText = "Pause"
            resetPlayers()
        } else {
            playPauseButton.icon = playIcon
            playPauseButton.toolTipText = "Play"
            stopPlayers()
        }
        paused = !paused
    }
    playPauseButton.toolTipText = "Pause"
    statusPanel.add(playPauseButton)

    statusPanel.add(Box.createRigidArea(Dimension(5, 0)))

    timeLabel = JLabel("4 PM")
    timeLabel.font = timeLabel.font.deriveFont(Font.BOLD, 18f)
    statusPanel.add(timeLabel)

    statusPanel.add(Box.createRigidArea(Dimension(10, 0)))

    timeSeparatorLabel = JLabel("|")
    timeSeparatorLabel.font = timeSeparatorLabel.font.deriveFont(Font.PLAIN, 18f)
    statusPanel.add(timeSeparatorLabel)

    statusPanel.add(Box.createRigidArea(Dimension(10, 0)))

    weatherLabel = JLabel(rainyIcon)
    statusPanel.add(weatherLabel)

    statusPanel.add(Box.createRigidArea(Dimension(10, 0)))

    weatherSeparatorLabel = JLabel("|")
    weatherSeparatorLabel.font = weatherSeparatorLabel.font.deriveFont(Font.PLAIN, 18f)
    statusPanel.add(weatherSeparatorLabel)

    statusPanel.add(Box.createRigidArea(Dimension(10, 0)))

    gameLabel = JLabel("NL")
    gameLabel.font = gameLabel.font.deriveFont(Font.PLAIN, 18f)
    statusPanel.add(gameLabel)

    settingsButton = JButton(settingsIcon)
    settingsButton.border = BorderFactory.createEmptyBorder(0, 0, 0, 15)
    settingsButton.isContentAreaFilled = false
    settingsButton.addActionListener { showSettings() }
    settingsButton.toolTipText = "Settings"
    frame.add(settingsButton, BorderLayout.LINE_END)

    windowButtonPanel = JPanel()
    windowButtonPanel.layout = BoxLayout(windowButtonPanel, BoxLayout.X_AXIS)
    windowButtonPanel.border = BorderFactory.createEmptyBorder(5, 5, 0, 0)
    frame.add(windowButtonPanel, BorderLayout.PAGE_START)

    closeButton = JButton(closeIcon)
    closeButton.border = BorderFactory.createEmptyBorder()
    closeButton.isContentAreaFilled = false
    closeButton.addActionListener { exitProcess(0) }
    windowButtonPanel.add(closeButton)

    minimizeButton = JButton(minimizeIcon)
    minimizeButton.border = BorderFactory.createEmptyBorder()
    minimizeButton.isContentAreaFilled = false
    minimizeButton.addActionListener { frame.state = Frame.ICONIFIED }
    windowButtonPanel.add(minimizeButton)

    startMainLoop()
    frame.isVisible = true
}

fun getIcon(name: String, width: Int, height: Int) : ImageIcon {
    val image = ImageIO.read({}.javaClass.classLoader.getResource("icons${File.separator}$name"))
    val scaled = image.getScaledInstance(width, height, Image.SCALE_SMOOTH)
    return ImageIcon(scaled)
}
