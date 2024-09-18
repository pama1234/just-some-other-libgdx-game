package pama1234.gdx.game.app.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import pama1234.gdx.launcher.MainApp
import pama1234.util.gdx.lwjgl.Lwjgl3ApplicationPama
import pama1234.util.gdx.lwjgl.UtilLauncher

/** Launches the desktop (LWJGL3) application.  */
object Launcher01 : UtilLauncher() {
    @kotlin.jvm.JvmStatic
    fun main(args: Array<String>) {
        createApplication()
    }

    fun createApplication(): Lwjgl3Application {
        Lwjgl3ApplicationPama.init()
        val app = MainApp()
        return Lwjgl3Application(app, getConfiguration(app))
    }

    fun getConfiguration(app: MainApp?): Lwjgl3ApplicationConfiguration {
        val conf = getDefaultConfiguration(app)
        return conf
    }
}