package com.dom.forti2d.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dom.forti2d.GameMain;

public class GameOver implements Screen {
	
	Texture img;
	SpriteBatch batch;
	private GameMain game;

	public GameOver(GameMain game){
		this.game = game;
		img = new Texture("sprites/game_over.png");
		batch = new SpriteBatch();
	}
	
	public void show() {
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);	
		batch.begin();
		batch.draw(img, -228, -170);
		batch.end();

		if(Gdx.input.isKeyPressed(Keys.ENTER)) {
			((Game) Gdx.app.getApplicationListener()).setScreen(new Level1(game));
		}		
	}

	public void resize(int width, int height) {
		
	}

	public void resume() {	
		
	}

	public void hide() {
		
	}

	public void dispose() {
		batch.dispose();
		img.dispose();
	}

	public void pause() {
		
	}

}
