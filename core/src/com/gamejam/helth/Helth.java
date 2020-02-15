package com.gamejam.helth;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

//import java.lang.reflect.Array;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.sun.xml.internal.bind.v2.TODO;

public class Helth extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;


	static class Player {
		static float WIDTH;
		static float HEIGHT;
		static float RUN_SPEED = 10f;
		static float JUMP_SPEED = 50f;

		enum State {
			Running, Jumping, Stopped
		}

		final Vector2 position = 	 new Vector2();
		final Vector2 velocity = new Vector2();

		State playerState = State.Running;

		float stateTime = 0;
		boolean onGround = true;

	}

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private Texture playerTexture;
	private Animation	run;
	private Animation jump;
	private Animation stop;
	private Player player;

	private Pool rectPool = new Pool()
	{
		@Override
		protected Rectangle newObject()
		{
			return new Rectangle();
		}
	};


	private Array tiles = new Array();

	private static final float GRAVITY = -2f;
	@Override
	public void create () {
//		batch = new SpriteBatch();
		playerTexture = new Texture(Gdx.files.internal("stickman.png"));
		//TODO : ACTUAL TEXTURE FOR PLAYER
		TextureRegion[] regions = TextureRegion.split(playerTexture, 18, 26)[0];


		stop = new Animation<>(0, regions[0]);
		jump = new Animation(0, regions[1]);
		run = new Animation(0.2f, regions[2], regions[3], regions[4]);

		run.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

		// figure out the width and height of the player for collision
		// detection and rendering by converting a player frames pixel
		// size into world units (1 unit == 16 pixels)
		Player.WIDTH = 1 / 16f * regions[0].getRegionWidth();
		Player.HEIGHT = 1 / 16f * regions[0].getRegionHeight();

		// load the map, set the unit scale to 1/16 (1 unit == 16 pixels)
//		map = new TmxMapLoader().load("data/level1.tmx");
		// TODO: make the level!
		renderer = new OrthogonalTiledMapRenderer(map, 1 / 16f);


		camera = new OrthographicCamera();
		camera.setToOrtho(false, 30, 20);
		camera.update();

		player = new Player();
		player.position.set(20, 20);



	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		float deltaTime = Gdx.graphics.getDeltaTime();

		updatePlayer(deltaTime);

		camera.position.x = player.position.x;
		camera.update();

		renderer.setView(camera);
		renderer.render();
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();

//		renderPlayer(deltaTime);

		//TODO!
	}

	private void updatePlayer(float deltaTime) {
	}


	private Vector2 tmp = new Vector2();



	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

}


