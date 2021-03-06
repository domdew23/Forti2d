package com.dom.forti2d;

import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dom.forti2d.screens.SplashScreen;

public class GameMain extends Game {
	public SpriteBatch batch;
	public HashMap<String, Float> gameState;
	
	public void create () {
		batch = new SpriteBatch();
		setScreen(new SplashScreen(this));
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
