package com.dom.forti2d.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Slots {
	private Stage stage;
	private Image slot;
	private Image[] slots;
	private float width, height;
	
	public Slots() {
		this.stage = new Stage();
		this.width = 40;
		this.height = 40;
		this.slots = new Image[5];
		
		for (int i = 0, x = 900; i < 5; i++, x += (width + 10)) {
			slot = new Image(new Texture("sprites/gunSlots/slot.png"));
			slot.setPosition(x, 3f);
			slot.setSize(width, height);
			slots[i] = slot;
			stage.addActor(slots[i]);
		}	
	}
	
	public void update() {
		slots[0].setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("sprites/gunSlots/goldSlot.png"))));
	}
	
	public void draw(float delta) {
		stage.act(delta);
		stage.draw();
	}
}
