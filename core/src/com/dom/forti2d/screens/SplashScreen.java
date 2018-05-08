package com.dom.forti2d.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dom.forti2d.GameMain;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import com.dom.forti2d.tween.SpriteAccessor;

public class SplashScreen implements Screen {
	
	private Sprite splash;
	private SpriteBatch batch;
	private TweenManager tweenManager;
	private GameMain game;
	
	public SplashScreen(GameMain game) {
		this.game = game;
	}
	
	public void show() {
		batch = new SpriteBatch();
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		
		Texture texture = new Texture("splash.png");
		splash = new Sprite(texture);
		splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		
		Tween.to(splash, SpriteAccessor.ALPHA, 1).target(1).repeatYoyo(1, 2).setCallback(new TweenCallback() {
			public void onEvent(int type, BaseTween<?> source) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new Level1(game));
			}
		}).start(tweenManager);
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tweenManager.update(delta);
		batch.begin();
		splash.draw(batch);
		batch.end();
	}

	public void resize(int width, int height) {
		splash.setSize(width, height);
	}

	public void pause() {
		
	}

	public void resume() {
		
	}

	public void hide() {
		
	}

	public void dispose() {
		batch.dispose();
		splash.getTexture().dispose();
	}
	
}
