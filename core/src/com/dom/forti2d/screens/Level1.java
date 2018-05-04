package com.dom.forti2d.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.dom.forti2d.GameMain;
import com.dom.forti2d.hud.HUDObject;
import com.dom.forti2d.items.Pistol;
import com.dom.forti2d.items.Rifle;
import com.dom.forti2d.items.RocketLauncher;
import com.dom.forti2d.items.Sheild;
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
		
		player.addItem(new Sheild(), 0);
		player.addItem(new RocketLauncher(0, "RocketLauncher"), 1);
		player.addItem(new Pistol(2, "Pistol"), 3);
		player.addItem(new RocketLauncher(1, "RocketLauncher"), 2);
		player.addItem(new Rifle(3, "Rifle"), 4);

		for (HUDObject h : hud)
			h.setPlayer(player);
	}
	
	protected void handleInput(float delta) {
		super.handleInput(delta);
		super.checkEquipmentChange(delta);
		
		if (Gdx.input.isKeyJustPressed(Keys.F)) {
			player.getAmmo()[0]++;
			player.getAmmo()[1]++;
			player.getAmmo()[2]++;
			player.getAmmo()[3]++;
			player.getAmmo()[4]++;
			player.decrementHealth(.3f);
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.R)) {
			player.shoot();
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
