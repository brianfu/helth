package com.gamejam.helth;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class MainMenuScreen implements Screen {

    final Helth game;
    float inputTime = 0;
    TextureRegion backgroundTexture;
//    Texture playButtonTexture;
//    TextureRegion playButtonTextureRegion;
//    ImageButton playButton;
//    TextureRegionDrawable playButtonDrawable;

    OrthographicCamera camera;

    public MainMenuScreen(final Helth game){
        this.game = game;


//        playButtonTexture = new Texture(Gdx.files.internal("playbutton.png"));
//
//        playButtonTextureRegion = new TextureRegion(playButtonTexture);
//        playButtonDrawable = new TextureRegionDrawable(playButtonTextureRegion);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);

        backgroundTexture = new TextureRegion(new Texture("mainmenu.jpg"), 0,0, 2220, 1080);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta){

        Gdx.gl.glClearColor(0.2f, 0.6f ,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//         playButton = new ImageButton(playButtonDrawable);
//        playButton.setTransform(true);
//        playButton.setX(500);
//        playButton.setY(360);
//
//        playButton.setSize(100, 100);
        game.batch.begin();
        game.batch.draw(backgroundTexture, 0,0 );
//        game.font.draw(game.batch, "HELTH", 750, 700);
//        game.font.draw(game.batch, "PRESS ANYWHERE TO START", 570, 400);
        game.batch.end();


        if (Gdx.input.isTouched()){
            if (inputTime  == 0 || inputTime > 1) { //delay in seconds
                game.setScreen(new GameScreen_1(game));
                dispose();
            }

                inputTime += delta;

        }
        else {inputTime = 0;}


//       playButton.addListener()



    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


}