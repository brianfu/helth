package com.gamejam.helth.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.gamejam.helth.Helth

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.title = "Helth"
        config.width = 800
        config.height = 480
        LwjglApplication(Helth(), config)
    }
}