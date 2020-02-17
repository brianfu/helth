package com.gamejam.helth

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.TimeUtils

class EndScreen(val game : Helth, val time: Float  ) : Screen {

    private val camera : OrthographicCamera //Don't need lateinit if set on constructor
    private val deadImage : Texture
    private val backgroundTexture: TextureRegion
    val TimeScore = time;

    init{ //constructor
        camera = OrthographicCamera()
        camera.setToOrtho(false, 2220f, 1080f)
        deadImage = Texture("dead_brocc.png")
//        TimeScore = TimeUtils.timeSinceMillis(game.startTime) / 1000f //Time survived in seconds

        backgroundTexture = TextureRegion(Texture("endscreen.png"), 0, 0, 2220, 1080)
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
        game.font.draw(game.batch, "You survived: ${two_dec(TimeScore)} seconds!", 550f, 400f)
        game.font.draw(game.batch, "Press to play again!", 700f, 300f)
        game.batch.end()

        if (Gdx.input.justTouched() || Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            game.screen = MainMenuScreen(game)
            dispose()
        }
    }

    fun two_dec( x : Float) : Float{
        var res = (x * 100).toInt()
        var res2 = res / 100f
        return res2
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