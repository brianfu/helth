package com.gamejam.helth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen_1 implements Screen {

    final Helth game;

    Texture dropImage;
    Texture bucketImage;
    OrthographicCamera camera;
    Rectangle bucket;
    Array<Rectangle> raindrops;
    long lastDropTime;
    int dropsGathered;


    public GameScreen_1(Helth game) {
        this.game = game;

        dropImage = new Texture("droplet.png");
        bucketImage = new Texture("bucket.png");

        //omit sound for now

        //camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);

        //create rectangle to represent bucket
        bucket = new Rectangle();
        bucket.x = 0;
        bucket.y = 0;

        bucket.width = 64;
        bucket.height = 64;

        raindrops = new Array<>();
        spawnRaindrops();
    }

    private void spawnRaindrops() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = 800;
        raindrop.y = MathUtils.random(96, 400);
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);


        game.batch.begin();
        game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, 480);
        game.batch.draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height);
        for (Rectangle raindrop : raindrops) {
            game.batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        game.batch.end();


        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.y = touchPos.y;
        }

        if (bucket.x < 0) {
            bucket.x = 0;
        }

        if (bucket.x > 800 - 64) {
            bucket.x = 800 - 64;
        }

        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
            spawnRaindrops();
        }

        Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.x -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.x < 0) {
                iter.remove();
            }
            if (raindrop.overlaps(bucket)) {
                dropsGathered++;
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
        dropImage.dispose();
        bucketImage.dispose();

    }
}

