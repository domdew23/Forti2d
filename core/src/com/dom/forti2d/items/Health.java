package com.dom.forti2d.items;

import com.badlogic.gdx.physics.box2d.World;

public class Health extends Item {
	
	public Health(World world, float x, float y) {
		super(world, x, y, 1, "Health");
	}
	
	public void setItemDrawable(String item) {
		this.itemDrawable = Drawables.getHealth();
	}
}
