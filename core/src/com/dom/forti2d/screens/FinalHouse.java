package com.dom.forti2d.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.dom.forti2d.GameMain;
import com.dom.forti2d.hud.HUDObject;
import com.dom.forti2d.sprites.Player;
import com.dom.forti2d.tools.EnemySpawner;

public class FinalHouse extends Level {
	private Level level;
	
	public FinalHouse(GameMain game, Level level, Player player) {
		super(game, "maps/level1/bossMap.tmx");
		this.xUpperBound = 2.0f;
		this.level = level;
		this.player = player;
		this.player.createBody(world, 62f, 32f);
		
		for (HUDObject h : hud)
			h.setPlayer(player);
		
		enemies = EnemySpawner.getBoss(world, enemies);

	}
	
	protected void handleInput(float delta) {
		super.handleInput(delta);
		
		if (Gdx.input.isKeyJustPressed(Keys.X)) {
			game.setScreen(level);
		}
	}
}
