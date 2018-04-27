package com.dom.forti2d.screens;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.dom.forti2d.GameMain;
import com.dom.forti2d.objects.Doors;
import com.dom.forti2d.objects.Ground;
import com.dom.forti2d.objects.Obstacles;
import com.dom.forti2d.objects.Platforms;
import com.dom.forti2d.sprites.Player;
import com.dom.forti2d.tools.Constants;

public class Level1 implements Screen {
	
	private GameMain game;
	private World world;
	private Box2DDebugRenderer debug;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private Player player;
	
	private OrthographicCamera camera;
	
	private final float xUpperBound=3630, xLowerBound=200, yUpperBound=104, yLowerBound=104;
	
	public Level1(GameMain game) {
		this.game = game;
		this.world = new World(new Vector2(0, -9.81f), true);
		this.debug = new Box2DDebugRenderer();
		
		loadCamera();
		loadMap();
		loadObjects();
	}
	
	private void loadMap() {
		map = new TmxMapLoader().load("maps/level1/level1.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1 / Constants.SCALE);
	}
	
	private void loadCamera() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 400 / Constants.SCALE, 208 / Constants.SCALE);
		camera.update();
	}
	
	private void loadObjects() {	
		Ground g = new Ground(map, world);
		Platforms p = new Platforms(map, world);
		Obstacles o = new Obstacles(map, world);
		Doors d = new Doors(map, world);
		player = new Player(world);
	}
	
	public void show() {
		
	}
	
	private void handleInput(float delta) {
		if (Gdx.input.isKeyPressed(Keys.A) && player.body.getLinearVelocity().x >= -2)
			player.body.applyLinearImpulse(new Vector2(-.1f, 0), player.body.getWorldCenter(), true);
		if (Gdx.input.isKeyPressed(Keys.D) && player.body.getLinearVelocity().x <= 2)
			if (camera.position.x < xUpperBound)
				player.body.applyLinearImpulse(new Vector2(.1f, 0), player.body.getWorldCenter(), true);
		
		if (Gdx.input.isKeyJustPressed(Keys.SPACE))
			if (camera.position.y <= yUpperBound)
				player.body.applyLinearImpulse(new Vector2(0, 4f), player.body.getWorldCenter(), true);
		
		if (Gdx.input.isKeyJustPressed(Keys.NUM_1)) {
			player.hasNoGun = true;
			player.hasPistol = false;
			player.hasRifle = false;
			player.hasRocketLauncher = false;
		} else if (Gdx.input.isKeyJustPressed(Keys.NUM_2)) {
			player.hasNoGun = false;
			player.hasPistol = true;
			player.hasRifle = false;
			player.hasRocketLauncher = false;
		} else if (Gdx.input.isKeyJustPressed(Keys.NUM_3)) {
			player.hasNoGun = false;
			player.hasPistol = false;
			player.hasRifle = true;
			player.hasRocketLauncher = false;
		} else if (Gdx.input.isKeyJustPressed(Keys.NUM_4)) {
			player.hasNoGun = false;
			player.hasPistol = false;
			player.hasRifle = false;
			player.hasRocketLauncher = true;
		}
	}
	
	private void update(float delta) {
		world.step(1/60f, 6, 2);
		camera.position.x = player.body.getPosition().x;
		camera.update();
		player.update(delta);
		renderer.setView(camera);
		game.batch.setProjectionMatrix(camera.combined);
	}
	
	private void draw(float delta) {
		renderer.render();
		//debug.render(world, camera.combined);
		game.batch.begin();
		player.draw(game.batch);
		game.batch.end();
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		handleInput(delta);
    	update(delta);
    	draw(delta);
	}

	public void resize(int width, int height) {
		
	}

	public void pause() {
		
	}

	public void resume() {
		
	}

	public void hide() {
		
	}

	public void dispose() {
		world.dispose();
		renderer.dispose();
		map.dispose();
		debug.dispose();
	}
}
