package com.dom.forti2d.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.dom.forti2d.GameMain;
import com.dom.forti2d.bullets.PistolBullet;
import com.dom.forti2d.sprites.Player;

public class Level1 extends Level {
	
	private float lastX;
	private float lastY;
	private boolean created;
	
	public static boolean changeScreen = false;
	private boolean changedScreen;
	
	public Level1(GameMain game) {
		super(game, "maps/level1/level1.tmx");
		this.player = new Player(world);
		this.player.createBody(world, 256f, 32f);
		this.lastX = player.body.getPosition().x;
		this.lastY = player.body.getPosition().y;
		this.created = true;
		this.changedScreen = false;
	}
	
	protected void handleInput(float delta) {
		super.handleInput(delta);
		System.out.println(player.getX());
		if (Gdx.input.isKeyJustPressed(Keys.R)) {
			blueElite.decrementHealth(.3f);
			redElite.decrementHealth(.3f);
			grunt.decrementHealth(.3f);
			bullet = new PistolBullet(world,  ((player.getX() - player.getWidth() / 2) + .3f) * 100, ((player.getY() + player.getHeight() / 2) - .02f) * 100, 30f, "sprites/rifleBullet.png");
		}
		
	}
	
	protected void update(float delta) {
		super.update(delta);
		
		if (changeScreen && !changedScreen) {
			changedScreen = true;
			lastX = player.body.getPosition().x;
			lastY = player.body.getPosition().y;
			world.destroyBody(player.body);
			created = false;
			game.setScreen(new House1(game, this, player));
		}
	}
	
	public void show() {
		super.show();
		if (!created) 
			this.player.createBody(world, lastX * 100, lastY * 100);
	}
}
