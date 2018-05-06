package com.dom.forti2d.screens;

import com.dom.forti2d.GameMain;
import com.dom.forti2d.hud.HUDObject;
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

		for (HUDObject h : hud)
			h.setPlayer(player);
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
