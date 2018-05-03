package com.dom.forti2d.screens;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.dom.forti2d.GameMain;
import com.dom.forti2d.hud.AmmoDisplay;
import com.dom.forti2d.hud.HUDObject;
import com.dom.forti2d.hud.HealthDisplay;
import com.dom.forti2d.hud.SlotsDisplay;
import com.dom.forti2d.listeners.CollisionListener;
import com.dom.forti2d.objects.Doors;
import com.dom.forti2d.objects.Ground;
import com.dom.forti2d.objects.Obstacles;
import com.dom.forti2d.objects.Platforms;
import com.dom.forti2d.sprites.BlueElite;
import com.dom.forti2d.sprites.Enemy;
import com.dom.forti2d.sprites.Grunt;
import com.dom.forti2d.sprites.Player;
import com.dom.forti2d.sprites.RedElite;
import com.dom.forti2d.tools.Constants;

public abstract class Level implements Screen {
	
	protected GameMain game;
	protected World world;
	private Box2DDebugRenderer debug;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	protected Player player;
	
	private OrthographicCamera camera;
	
	private final float xUpperBound=36.4f, xLowerBound=2;
	private final String mapName;
	
	protected CopyOnWriteArrayList<Enemy> enemies;
	
	protected ArrayList<HUDObject> hud;
	
	public Level(GameMain game, String mapName) { 
		this.game = game;
		this.world = new World(new Vector2(0, -9.81f), true);
		this.world.setContactListener(new CollisionListener());
		this.debug = new Box2DDebugRenderer();
		this.mapName = mapName;
		this.hud = new ArrayList<HUDObject>();
		this.enemies = new CopyOnWriteArrayList<Enemy>();
		
		loadCamera();
		loadMap();
		loadObjects();
	}
	
	private void loadMap() {
		map = new TmxMapLoader().load(mapName);
		renderer = new OrthogonalTiledMapRenderer(map, 1 / Constants.SCALE);
	}
	
	private void loadCamera() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 400 / Constants.SCALE, 208 / Constants.SCALE);
		camera.update();
	}
	
	private void loadObjects() {	
		new Ground(map, world);
		new Platforms(map, world);
		new Obstacles(map, world);
		new Doors(map, world);
		
		enemies.add(new BlueElite(world, 306f, 32f));
		enemies.add(new RedElite(world, 356f, 32f));
		enemies.add(new Grunt(world, 206f, 32f));
		
		hud.add(new SlotsDisplay());
		hud.add(new AmmoDisplay());
		hud.add(new HealthDisplay());
	}
	
	public void show() {
	}
	
	protected void handleInput(float delta) {
		if (Gdx.input.isKeyPressed(Keys.A) && player.body.getLinearVelocity().x >= -2)
			player.body.applyLinearImpulse(new Vector2(-.1f, 0), player.body.getWorldCenter(), true);
		if (Gdx.input.isKeyPressed(Keys.D) && player.body.getLinearVelocity().x <= 2)
			player.body.applyLinearImpulse(new Vector2(.1f, 0), player.body.getWorldCenter(), true);
		if (Gdx.input.isKeyJustPressed(Keys.SPACE))
			player.body.applyLinearImpulse(new Vector2(0, 4f), player.body.getWorldCenter(), true);
	}
	
	protected void checkEquipmentChange(float delta) {
		
		if (Gdx.input.isKeyJustPressed(Keys.NUM_1)) {
			player.getInventory()[0].setEquipped();
			player.clearEquipped(0);
		}
		else if (Gdx.input.isKeyJustPressed(Keys.NUM_2)) {
			player.getInventory()[1].setEquipped();
			player.clearEquipped(1);
		}
		else if (Gdx.input.isKeyJustPressed(Keys.NUM_3)) {
			player.getInventory()[2].setEquipped();
			player.clearEquipped(2);
		}
		else if (Gdx.input.isKeyJustPressed(Keys.NUM_4)) {
			player.getInventory()[3].setEquipped();
			player.clearEquipped(3);
		} else if (Gdx.input.isKeyJustPressed(Keys.NUM_5)) {
			player.getInventory()[4].setEquipped();
			player.clearEquipped(4);
		}
	}
	
	protected void update(float delta) {
		world.step(1/60f, 8, 3);
		if (player.body.getPosition().x > xLowerBound && player.body.getPosition().x < xUpperBound) camera.position.x = player.body.getPosition().x;
		camera.update();
		player.update(delta);
		
		for (HUDObject h : hud)
			h.update();
		
		for (Enemy e : enemies) {
			e.update(delta);
			if (e.kill && !world.isLocked()) {
				if (!e.isDead) {
					world.destroyBody(e.body);
					e.isDead = true;
					enemies.remove(e);
				}
			}
		}
		
		renderer.setView(camera);
		game.batch.setProjectionMatrix(camera.combined);
	}
	
	private void draw(float delta) {
		renderer.render();
		for (HUDObject h : hud)
			h.draw(delta);
		
		debug.render(world, camera.combined);
		game.batch.begin();
		
		for (Enemy e : enemies)
			e.draw(game.batch);
		
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
		for (HUDObject h : hud)
			h.dispose();
	}
}
