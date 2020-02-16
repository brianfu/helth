package com.gamejam.helth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

    //Texture characterImage;
    Texture platformImage;
    TextureRegion floorImage;
    //Texture platform6;
    //Texture platform3;
    //Texture playerImage;
    OrthographicCamera camera;
    Player vegetable;
    Array<Block> platforms;
    Array<DeathBlock> enemies;
    GroundBlock floor;
    long lastDropTime;
    long lastShotFired;
    //long lastEnemyDisappear;
    //int dropsGathered;
    TextureRegion backgroundTexture;

    DeathBlock enemy;
    Texture enemyImage;

    private Block surface;
    public float floorHeight = 150f;
    private float platform_width = 380f;


    public GameScreen_1(Helth game) {
        this.game = game;

        platformImage = new Texture("block.png");

        //backgroundTexture = new TextureRegion(new Texture("firstscreen.jpg"), 0, 0, 2220, 1080);
        //platform6 = new Texture("platform6.png");
        //platform3 = new Texture("platform3.png");
        //playerImage = new Texture("bucket.png");

        //backgroundTexture = new Texture("plain_background.png");
        backgroundTexture = new TextureRegion(new Texture("plain_background.jpg"), 0, 0, 2220, 1080);

        floorImage = new TextureRegion(new Texture("ground.png"), 0, 0, 2220, 150);
        floor = new GroundBlock(0, 0, 2220, floorHeight);

        //camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 2220, 1080);

        vegetable = new Player(50, floorHeight, 10, 10, 10);
        vegetable.setFloorHeight$core(floorHeight); //Tell the player where the ground is
        vegetable.characterImage = new Texture("broccoli.png");

        vegetable.width = 150;
        vegetable.height = 150;

        platforms = new Array<>();
        spawnPlatforms();

        enemy = new DeathBlock(1800f, floorHeight + 400f, 10);
        enemyImage = new Texture("fat.png");

        enemy.shoot(vegetable);
        lastShotFired = TimeUtils.nanoTime();
    }

    private void spawnPlatforms() {
        GroundBlock platform = new GroundBlock(0, 0, platform_width, 100);
        platform.x = 2220;
        platform.y = MathUtils.random(200, 600);
        platforms.add(platform);
        lastDropTime = TimeUtils.nanoTime();
    }


//    private void spawnEnemies(){ //Don't run this too often
//        DeathBlock enemy = new DeathBlock(0,0, 10);
//        enemy.x = MathUtils.random(400, 2000);
//        enemies.add(enemy);
//        //lastEnemyTime = TimeUtils.nanoTime();
//        //lastEnemyDisappear = TimeUtils.nanoTime(); //Last time an enemy disappeared
//    }


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

        vegetable.enemyBulletsUpdate();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0);
        game.batch.draw(floorImage, 0, 0);
        game.font.draw(game.batch, "Helth:" + vegetable.getHealth(), 1700f, 1000f);
        game.batch.draw(enemyImage, enemy.x, enemy.y);
        game.batch.draw(vegetable.characterImage, vegetable.x, vegetable.y, vegetable.width, vegetable.height);

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


        for (BulletBlock bullet : vegetable.getEnemyBullets()) {
            game.batch.draw(bullet.getBulletImage(), bullet.x, bullet.y);
        }

        game.batch.end();

        if (vegetable.getOnPlatformState() == Player.OnPlatformState.ON_PLATFORM) {
            if (!vegetable.collision(surface, 2f)) {
                vegetable.setOnPlatformState(Player.OnPlatformState.BETWEEN_PLATFORMS);
            }
        }


        if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            vegetable.setInputState(Player.InputState.HELD);
            vegetable.jump(); //TODO: this only works sometimes, no idea why
            if (isTouched(0, 0.50f)){
                vegetable.x -= 300 * Gdx.graphics.getDeltaTime();
            }

            if (isTouched(0.50f, 1)){
                vegetable.x += 300 * Gdx.graphics.getDeltaTime();
            }
        }



        else {
            vegetable.setInputState(Player.InputState.NONE);
        }


        //keep vegetable within bounds
        if (vegetable.x < 0){
            vegetable.x = 0;
        }

        if (vegetable.x > 2075){
            vegetable.x = 2075;
        }


        if (TimeUtils.nanoTime() - lastDropTime > 2000000000L) {
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


        if (TimeUtils.nanoTime() - lastShotFired > 2000000000L) {
            enemy.shoot(vegetable);
            lastShotFired = TimeUtils.nanoTime();
        }


        if (vegetable.isDead()) {
            game.setScreen(new EndScreen(game));
            dispose();
        }

    }

    private boolean isTouched(float startX, float endX)
    {
        for (int i = 0; i < 2; i++)
        {
            float x = Gdx.input.getX() / (float) Gdx.graphics.getWidth();
            if (Gdx.input.isTouched(i) && (x >= startX && x <= endX))
            {
                return true;
            }
        }
        return false;
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
        vegetable.characterImage.dispose();
        platformImage.dispose();

    }
}

