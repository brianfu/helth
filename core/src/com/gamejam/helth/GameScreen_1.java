package com.gamejam.helth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen_1 implements Screen {

    final Helth game;

    Texture characterImage;
    Texture platformImage;
    Texture platform6;
    Texture platform3;
    Texture playerImage;
    OrthographicCamera camera;
    Player vegetable;
    Array<Block> platforms;
    long lastDropTime;
    int dropsGathered;
    TextureRegion backgroundTexture;

    private Block surface;
    private float platform_width = 600f;


    public GameScreen_1(Helth game) {
        this.game = game;

        platformImage = new Texture("block.png");
        characterImage = new Texture("broccoli.png");

        backgroundTexture = new TextureRegion(new Texture("firstscreen.jpg"), 0, 0, 2220, 1080);
        platform6 = new Texture("platform6.png");
        platform3 = new Texture("platform3.png");
        playerImage = new Texture("bucket.png");

        //backgroundTexture = new Texture("plain_background.png");
        backgroundTexture = new TextureRegion(new Texture("plain_background.jpg"), 0, 0, 2220, 1080);

        //camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 2220, 1080);

        vegetable = new Player(0, 0, 10, 100, 10);
        vegetable.x = 0;
        vegetable.y = 0;

        vegetable.width = 150;
        vegetable.height = 150;

        platforms = new Array<>();
        spawnPlatforms();
    }

    private void spawnPlatforms() {
        GroundBlock platform = new GroundBlock(0,0, platform_width, 100);
        platform.x = 2220;
        platform.y = MathUtils.random(200, 800);
        platforms.add(platform);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void show() {

    }

    private Vector2 centerTest = new Vector2();
    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        vegetable.jumpProcess();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0);
        game.batch.draw(characterImage, vegetable.x, vegetable.y, vegetable.width, vegetable.height);
        for (Block platform : platforms) {
            game.batch.draw(platformImage, platform.x, platform.y);

//            if (vegetable.y != 0 && vegetable.getJumpState() == Player.JumpState.STANDING) {
//                vegetable.gravity();
//            }

            if (vegetable.collision(platform, 2f)) { //TODO: replace 2f with collision radius
                vegetable.jumpCollider(platform);
                surface = platform;
                vegetable.setOnPlatformState(Player.OnPlatformState.ON_PLATFORM);
            }

        }
        game.batch.end();

        if (vegetable.getOnPlatformState() == Player.OnPlatformState.ON_PLATFORM){
            if (!vegetable.collision(surface, 2f)){
                vegetable.setOnPlatformState(Player.OnPlatformState.BETWEEN_PLATFORMS);
            }
        }



        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            vegetable.jump(); //TODO: this only works sometimes, no idea why
        }

        if (TimeUtils.nanoTime() - lastDropTime > 8000000000L) {
            spawnPlatforms();
        }

        Iterator<Block> iter = platforms.iterator();
        while (iter.hasNext()) {
            Block raindrop = iter.next();
            raindrop.x -= 500 * Gdx.graphics.getDeltaTime();
            if (raindrop.x + platform_width < 0) {
                iter.remove();
            }

        }




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
        characterImage.dispose();
        platformImage.dispose();

    }
}

