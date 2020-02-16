package com.gamejam.helth

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.TimeUtils

class EndScreen(val game : Helth) : Screen {

    private val camera : OrthographicCamera //Don't need lateinit if set on constructor
    private val deadImage : Texture
    private val backgroundTexture: TextureRegion
    var TimeScore = 0f;

    init{ //constructor
        camera = OrthographicCamera()
        camera.setToOrtho(false, 2220f, 1080f)
        deadImage = Texture("dead_brocc.png")
        TimeScore = TimeUtils.timeSinceMillis(game.startTime) / 1000f //Time survived in seconds

        backgroundTexture = TextureRegion(Texture("endscreen.jpg"), 0, 0, 2220, 1080)
    }

    override fun hide() {
        //TODO("not implemented")
    }

    override fun show() {
        //TODO("not implemented")
    }



    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0.8f,0f,0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        camera.update() //recalculates where to put the camera
        game.batch.projectionMatrix = camera.combined //tell the game where the screen goes


        game.batch.begin()
        game.batch.draw(backgroundTexture, 0f,0f)
        game.batch.draw(deadImage, 1500f, 350f)
        game.font.draw(game.batch, "You survived: $TimeScore seconds!", 450f, 300f)
        game.batch.end()

        if (Gdx.input.justTouched()){
            game.screen = MainMenuScreen(game)
            dispose()
        }
    }

    override fun pause() {
        //TODO("not implemented")
    }

    override fun resume() {
        //TODO("not implemented")
    }

    override fun resize(width: Int, height: Int) {
        //TODO("not implemented")
    }

    override fun dispose() {
        //TODO("not implemented")
    }
}