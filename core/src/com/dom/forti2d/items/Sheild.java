package com.dom.forti2d.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Sheild extends Item {
	
	public Sheild() {
		super(2, "Sheild");
	}
	
	public void setItemDrawable(String item) {
		this.itemDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("sprites/" + item + ".png")));
	}
}
