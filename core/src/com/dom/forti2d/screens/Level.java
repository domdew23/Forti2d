package com.dom.forti2d.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.dom.forti2d.GameMain;
import com.dom.forti2d.bullets.Bullet;
import com.dom.forti2d.guns.Pistol;
import com.dom.forti2d.guns.Rifle;
import com.dom.forti2d.guns.RocketLauncher;
import com.dom.forti2d.hud.AmmoDisplay;
import com.dom.forti2d.hud.Slots;
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
	
	private Texture healthBar;
	private Texture sheildBar;
	private final float healthBarY = .014f, healthBarHeight = .04f, sheildBarY = .074f;
	
	protected Enemy blueElite;
	protected Enemy redElite;
	protected Enemy grunt;
	protected Bullet bullet;
	protected Slots slots;
	protected AmmoDisplay ammoDisplay;
	
	public Level(GameMain game, String mapName) { 
		this.game = game;
		this.world = new World(new Vector2(0, -9.81f), true);
		this.world.setContactListener(new CollisionListener());
		this.debug = new Box2DDebugRenderer();
		this.mapName = mapName;
		this.healthBar = new Texture("sprites/blank.png");
		this.sheildBar = new Texture("sprites/blank2.png");
		
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
		
		blueElite = new BlueElite(world, 306f, 32f);
		redElite = new RedElite(world, 356f, 32f);
		grunt = new Grunt(world, 206f, 32f);
		
		slots = new Slots();
		ammoDisplay = new AmmoDisplay();
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
	
	protected void update(float delta) {
		world.step(1/60f, 8, 3);
		if (player.body.getPosition().x > xLowerBound && player.body.getPosition().x < xUpperBound) camera.position.x = player.body.getPosition().x;
		camera.update();
		player.update(delta);
		blueElite.update(delta);
		redElite.update(delta);
		grunt.update(delta);
		ammoDisplay.update();
		slots.update();
		if (bullet != null) bullet.update(delta);
		renderer.setView(camera);
		game.batch.setProjectionMatrix(camera.combined);
	}
	
	private void draw(float delta) {
		renderer.render();
		slots.draw(delta);
		ammoDisplay.draw(delta);

		debug.render(world, camera.combined);
		game.batch.begin();
		player.draw(game.batch);
		blueElite.draw(game.batch);
		redElite.draw(game.batch);
		if (bullet != null) bullet.draw(game.batch);
		grunt.draw(game.batch);
		game.batch.draw(healthBar, player.body.getPosition().x - .5f, healthBarY, 1 * player.health, healthBarHeight);
		game.batch.draw(sheildBar, player.body.getPosition().x - .5f, sheildBarY, 1 * player.sheild, healthBarHeight);
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
