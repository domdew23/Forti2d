package com.dom.forti2d.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.dom.forti2d.GameMain;
import com.dom.forti2d.hud.HUDObject;
import com.dom.forti2d.sprites.Player;

public class House1 extends Level {
	
	private Level level;
	
	public House1(GameMain game, Level level, Player player) {
		super(game, "maps/level1/house1.tmx");
		this.level = level;
		this.player = player;
		this.player.createBody(world, 62f, 32f);
		
		for (HUDObject h : hud)
			h.setPlayer(player);

	}
	
	protected void handleInput(float delta) {
		super.handleInput(delta);
		
		if (Gdx.input.isKeyJustPressed(Keys.X)) {
			game.setScreen(level);
		}
	}
}
