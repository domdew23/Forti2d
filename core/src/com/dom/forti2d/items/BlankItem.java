package com.dom.forti2d.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class BlankItem extends Item {
	
	public BlankItem() {
		super();
	}

	public void setItemDrawable(String item) {
		this.itemDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("sprites/guns/blank.png")));
	}
}