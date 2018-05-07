package com.dom.forti2d.screens;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

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
import com.dom.forti2d.bullets.Explosion;
import com.dom.forti2d.hud.AmmoDisplay;
import com.dom.forti2d.hud.HUDObject;
import com.dom.forti2d.hud.HealthDisplay;
import com.dom.forti2d.hud.SlotsDisplay;
import com.dom.forti2d.items.Health;
import com.dom.forti2d.items.Item;
import com.dom.forti2d.items.Pistol;
import com.dom.forti2d.items.Rifle;
import com.dom.forti2d.items.RocketLauncher;
import com.dom.forti2d.items.Sheild;
import com.dom.forti2d.listeners.CollisionListener;
import com.dom.forti2d.objects.Doors;
import com.dom.forti2d.objects.Ground;
import com.dom.forti2d.objects.NonInteractive;
import com.dom.forti2d.objects.Obstacles;
import com.dom.forti2d.objects.Platforms;
import com.dom.forti2d.sprites.Enemy;
import com.dom.forti2d.sprites.Grunt;
import com.dom.forti2d.sprites.Player;
import com.dom.forti2d.tools.Constants;
import com.dom.forti2d.tools.EnemySpawner;
import com.dom.forti2d.tools.ItemSpawner;

public abstract class Level implements Screen {
	
	public static CopyOnWriteArrayList<Explosion> explosions;
	protected CopyOnWriteArrayList<Item> items;
	protected CopyOnWriteArrayList<Enemy> enemies;
	protected ArrayList<HUDObject> hud;

	protected GameMain game;
	protected World world;
	private Box2DDebugRenderer debug;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	protected Player player;
	
	private OrthographicCamera camera;
	
	private final float xUpperBound=36.4f, xLowerBound=2;
	private final String mapName;
	
	public Level(GameMain game, String mapName) { 
		this.game = game;
		this.world = new World(new Vector2(0, -9.81f), true);
		this.world.setContactListener(new CollisionListener());
		this.debug = new Box2DDebugRenderer();
		this.mapName = mapName;
		this.hud = new ArrayList<HUDObject>();
		this.enemies = new CopyOnWriteArrayList<Enemy>();
		

		explosions = new CopyOnWriteArrayList<Explosion>();
		NonInteractive.clearMaps();
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
		
		items = ItemSpawner.spawnItems(world, 500f, 3800f);
		enemies = EnemySpawner.spawnEnemies(world, 500f, 3800f);
		
		hud.add(new SlotsDisplay());
		hud.add(new AmmoDisplay());
		hud.add(new HealthDisplay());
	}
	
	public void show() {
		player.setWorld(world);
	}
	
	protected void handleInput(float delta) {
		if (Gdx.input.isKeyPressed(Keys.A) && player.body.getLinearVelocity().x >= -2)
			player.body.applyLinearImpulse(new Vector2(-.1f, 0), player.body.getWorldCenter(), true);
		if (Gdx.input.isKeyPressed(Keys.D) && player.body.getLinearVelocity().x <= 2)
			player.body.applyLinearImpulse(new Vector2(.1f, 0), player.body.getWorldCenter(), true);
		if (Gdx.input.isKeyJustPressed(Keys.SPACE) && player.isNotJumping())
			player.body.applyLinearImpulse(new Vector2(0, 4f), player.body.getWorldCenter(), true);
		
		checkEquipmentChange(delta);
		
		if (Gdx.input.isKeyJustPressed(Keys.R)) {
			player.shoot();
		}
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
		
		if (Gdx.input.isKeyJustPressed(Keys.F)) {
			Item closest = null;
			float distance = Float.MAX_VALUE;
			float tmpDistance = 0;
			
			for (Item item : items) {
				tmpDistance = Math.abs(player.body.getPosition().x - item.body.getPosition().x);
				if (tmpDistance < distance) {
					closest = item;
					distance = tmpDistance; 
				}
			}
			
			float yDistance = Math.abs(player.body.getPosition().y - closest.body.getPosition().y);
			
			if (distance < .3 && yDistance < .25) {
				Item droppedItem = player.pickUp(closest);
				if (droppedItem != null)
					items.add(determineItemType(droppedItem).setWasDropped());
			}
		}
				
		for (HUDObject h : hud)
			h.update();
		
		for (Explosion e : explosions)
			e.update(delta);
		
		for (Enemy e : enemies) {
			e.update(delta, player.body.getPosition());
			if (e.kill && !world.isLocked()) {
				if (!e.isDead) {
					float x = e.body.getPosition().x * 100;
					float y = (e.body.getPosition().y * 100) + 10;
					items.add(ItemSpawner.getItem(world, ItemSpawner.possibleItems[ThreadLocalRandom.current().nextInt(100)], ItemSpawner.possibleTiers[ThreadLocalRandom.current().nextInt(100)], x, y));
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
		debug.render(world, camera.combined);
		
		for (HUDObject h : hud)
			h.draw(delta);
		
		game.batch.begin();
		
		for (Item item : items) {
			if (item.isPickedUp && !world.isLocked()) {
				if (!item.isDestroyed){
					world.destroyBody(item.body);
					item.isDestroyed = true;
					items.remove(item);
				}
			} else {
				item.draw(game.batch);
			}
		}
		
		for (Enemy e : enemies)
			e.draw(game.batch);
		
		for (Explosion e : explosions)
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
	
	private Item determineItemType(Item item) {
		float x = player.body.getPosition().x * 100;
		float y = player.body.getPosition().y * 100;
		
		if (item instanceof Health)
			return new Health(world, x, y).setCount(item.getCount());
		else if (item instanceof Sheild)
			return new Sheild(world, x, y).setCount(item.getCount());
		else if (item instanceof Pistol)
			return new Pistol(world, x, y, item.getTier(), item.getItem());
		else if (item instanceof Rifle)
			return new Rifle(world, x, y, item.getTier(), item.getItem());
		else if (item instanceof RocketLauncher)
			return new RocketLauncher(world, x, y, item.getTier(), item.getItem());
		else
			return null;

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
