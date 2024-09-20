package pama1234.gdx.game.app.lwjgl3

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import pama1234.gdx.launcher.MainApp
import pama1234.util.gdx.lwjgl.Lwjgl3ApplicationPama
import pama1234.util.gdx.lwjgl.UtilLauncher

/** Launches the desktop (LWJGL3) application.  */
object Launcher01 : UtilLauncher() {
    @JvmStatic
    fun main(args: Array<String>) {
        application {
            Window(onCloseRequest = ::exitApplication, title = "Game Launcher") {
                MaterialTheme {
                    LauncherUI()
                }
            }
        }
    }

    @Composable
    @Preview
    fun LauncherUI() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { createApplication() },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Launch Game")
            }
        }
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