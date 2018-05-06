package com.dom.forti2d.items;

import com.badlogic.gdx.physics.box2d.World;

public class Sheild extends Item {
	
	public Sheild(World world, float x, float y) {
		super(world, x, y, 2, "Sheild");
	}
	
	public void setItemDrawable(String item) {
		this.itemDrawable = Drawables.getSheild();
	}
}
