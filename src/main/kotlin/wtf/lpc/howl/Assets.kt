package wtf.lpc.howl

import java.io.File

// TODO Automatic asset downloading

val assetsDir = File(dataDir, "assets")

fun getAsset(vararg path: String) = File(assetsDir, path.joinToString(File.separator))
