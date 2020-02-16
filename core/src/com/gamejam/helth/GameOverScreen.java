package com.gamejam.helth;

//package com.gamejam.helth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import sun.applet.Main;


public class GameOverScreen implements Screen { //Depreciated, see EndScreen

    final Helth game;
    Texture retryButtonTexture;
    OrthographicCamera camera;
    TextureRegion retryButtonTextureRegion;
    TextureRegionDrawable retryButtonDrawable;
    ImageButton retryButton;
    int finalScore;

    public GameOverScreen(final Helth game){
        this.game = game;

//
//        retryButtonTexture = new Texture(Gdx.files.internal("button_retry.png"));
//
//        retryButtonTextureRegion = new TextureRegion(retryButtonTexture);
//        retryButtonDrawable = new TextureRegionDrawable(retryButtonTextureRegion);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0.8f, 0 ,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



//        Gdx.gl.glClearColor(0, 0 ,0.2f,1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        retryButton = new ImageButton(retryButtonDrawable);
//        retryButton.setTransform(true);
//        retryButton.setX(500);
//        retryButton.setY(360);
//
//        retryButton.setSize(100, 100);
         finalScore = 100;
        // TODO: get finalscore
        String scoreDisplay = "Your Final Score Is " + finalScore;
        game.batch.begin();
//        game.batch.begin();

        game.font.draw(game.batch, scoreDisplay, 475, 700);

        game.font.draw(game.batch, "PRESS ANYWHERE TO RETRY", 570, 400);
//        retryButton.draw(game.batch, 1);
        game.batch.end();

        if (Gdx.input.isTouched()){
            game.setScreen(new GameScreen_1(game));
            dispose();
        }
//        playButton.addListener( new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                System.out.println("hiii");
//                Assets.load();
//                // game.getSoundManager().play( TyrianSound.CLICK );
//                game.setScreen( new GameScreen(game) );
//            }
//        } );
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