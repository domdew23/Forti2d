package com.dom.forti2d.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.dom.forti2d.sprites.Player;

public class AmmoDisplay {

	private Stage stage;
	private Label label;
	private Label[] labels;
	private BitmapFont whiteFont;
	
	private Player player;
	
	public AmmoDisplay() {
		this.labels = new Label[3];
		
		this.stage = new Stage();
		this.whiteFont = new BitmapFont(Gdx.files.internal("fonts/white.fnt"), false);
		
		LabelStyle style = new LabelStyle(whiteFont, Color.WHITE);
		
		for (int i = 0, y = 420; i < 3; i++, y += 40) {
			label = new Label("x 31", style);
			label.setPosition(50, y);
			labels[i] = label;
			stage.addActor(label);
			
			Image image = null;
			switch (i) {
				case 0: 
					image = new Image(new Texture("sprites/ammoDisplay/pistolBullet.png"));
					break;
				case 1: 
					image = new Image(new Texture("sprites/ammoDisplay/rifleBullet.png"));
					break;
				case 2: 
					image = new Image(new Texture("sprites/ammoDisplay/rocketBullet.png"));
					break;
			}
			
			image.setPosition(5, y);
			image.setSize(30, 30);
			stage.addActor(image);
		}
	
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void update() {
		for (int i = 0; i < labels.length; i++)
			labels[i].setText("x " + player.getAmmo()[i]);
	}
	
	public void draw(float delta) {
		stage.act(delta);
		stage.draw();
	}
}
