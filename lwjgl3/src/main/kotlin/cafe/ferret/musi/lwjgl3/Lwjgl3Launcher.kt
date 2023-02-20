@file:JvmName("Lwjgl3Launcher")

package cafe.ferret.musi.lwjgl3

import cafe.ferret.musi.Game
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration

/** Launches the desktop (LWJGL3) application. */
fun main() {
    Lwjgl3Application(Game(), Lwjgl3ApplicationConfiguration().apply {
        setTitle("musi-toki")
        setWindowedMode(640, 480)
        setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
    })
}
