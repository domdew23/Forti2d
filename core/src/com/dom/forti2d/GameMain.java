package com.dom.forti2d;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dom.forti2d.screens.Level1;

public class GameMain extends Game {
	public SpriteBatch batch;
	
	public void create () {
		batch = new SpriteBatch();
		setScreen(new Level1(this));
	}
	
	public void render () {
		super.render();
	}
	
	public void dispose () {
		super.dispose();
	}
	
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
	public void pause() {
		super.pause();
	}
	
	public void resume() {
		super.pause();
	}
}
