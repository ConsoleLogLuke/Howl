package wtf.lpc.howl

import java.io.File

val assetsDir = File(dataDir, "assets")

fun getAsset(vararg path: String) = File(assetsDir, path.joinToString(File.separator))
