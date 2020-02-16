package com.gamejam.helth

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.TimeUtils

class MainMenuScreen(val game: Helth) : Screen {

    private var backgroundTexture: Texture
    private val camera: OrthographicCamera

    init {
        camera = OrthographicCamera()
        camera.setToOrtho(false, 2220f, 1080f)
        backgroundTexture = Texture("mainmenu.jpg")
    }

    override fun show() {}
    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        camera.update()
        game.batch.projectionMatrix = camera.combined

        game.batch.begin()
        game.batch.draw(backgroundTexture, 0f, 0f, 2220f, 1080f)
        game.batch.end()
        if (Gdx.input.justTouched()) {
//            game.startTime = TimeUtils.millis()
            game.screen = GameScreen_1(game)
            dispose()
        }
    }

    override fun resize(width: Int, height: Int) {}
    override fun pause() {}
    override fun resume() {}
    override fun hide() {}
    override fun dispose() {}

}