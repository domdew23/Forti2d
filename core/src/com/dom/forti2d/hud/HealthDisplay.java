package com.dom.forti2d.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class HealthDisplay extends HUDObject {

	private Image healthBar;
	private Image sheildBar;
	
	private final float healthBarY = 5.4f, width = 200f, height = 12f, x= (Gdx.graphics.getWidth() / 2) - 100f, sheildBarY = 21f;

	public HealthDisplay() {
		super();
		this.healthBar = new Image(new Texture("sprites/blank.png"));
		this.sheildBar = new Image(new Texture("sprites/blank2.png"));
		
		this.healthBar.setPosition(x, healthBarY);
		this.sheildBar.setPosition(x, sheildBarY);
		
		this.healthBar.setSize(width, height);
		this.sheildBar.setSize(width, height);
		
		this.stage.addActor(healthBar);
		this.stage.addActor(sheildBar);
	}
	
	public void update() {
		this.healthBar.setSize(width * player.health, height);
		this.sheildBar.setSize(width * player.sheild, height);	
	}
}
