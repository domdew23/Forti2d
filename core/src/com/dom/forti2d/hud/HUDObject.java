package com.dom.forti2d.hud;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dom.forti2d.sprites.Player;

public abstract class HUDObject {
	
	protected Stage stage;
	protected Player player;
	
	public HUDObject() {
		this.stage = new Stage();
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void draw(float delta) {
		stage.act(delta);
		stage.draw();
	}
	
	public void dispose() {
		stage.dispose();
	}
	
	public abstract void update();
	
}
