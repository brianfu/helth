package com.gamejam.helth

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.TimeUtils

class EndScreen(val game : Helth) : Screen {

    private val camera : OrthographicCamera //Don't need lateinit if set on constructor
    private val deadImage : Texture
    var TimeScore = 0f;

    init{ //constructor
        camera = OrthographicCamera()
        camera.setToOrtho(false, 2220f, 1080f)
        deadImage = Texture("dead_brocc.png")
        TimeScore = TimeUtils.timeSinceMillis(game.startTime) / 1000f //Time survived in seconds
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
        game.batch.draw(deadImage, 800f, 600f)
        game.font.draw(game.batch, ":( Ded", 950f, 400f)
        game.font.draw(game.batch, "You survived: $TimeScore seconds!", 450f, 300f)
        game.font.draw(game.batch, "Tap to play again!", 750f, 200f);
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