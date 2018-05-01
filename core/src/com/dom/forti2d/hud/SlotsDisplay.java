package com.dom.forti2d.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class SlotsDisplay extends HUDObject {
	
	private Image[] slots;
	private Image[] items;
	private float width, height;
	
	public SlotsDisplay() {
		super();
		this.width = 40;
		this.height = 40;
		this.slots = new Image[5];
		this.items = new Image[5];
		
		for (int i = 0, x = 900; i < 5; i++, x += (width + 10)) {
			slots[i] = new Image(new Texture("sprites/gunSlots/slot.png"));
			slots[i].setPosition(x, 3f);
			slots[i].setSize(width, height);
			
			items[i] = new Image(new Texture("sprites/guns/blank.png"));
			items[i].setPosition(x + 6f, 6f);
			items[i].setSize(width - 8, height - 8);
			
			stage.addActor(slots[i]);
			stage.addActor(items[i]);
		}
		
	}
	
	public void update() {
		for (int i = 0; i < player.getInventory().length; i++) {
			if (player.getInventory()[i] != null) {
				slots[i].setDrawable(player.getInventory()[i].getSlotDrawable());
				items[i].setDrawable(player.getInventory()[i].getItemDrawable());
			}
		}
	}
}
