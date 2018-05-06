package com.dom.forti2d.items;

import com.badlogic.gdx.physics.box2d.World;

public class BulletItem extends Item {

	public BulletItem(World world, float x, float y, int tier, String item) {
		super(world, x, y, tier, item);
	}
	
	public void setItemDrawable(String item) {	
		this.itemDrawable = Drawables.getItem(-1, item);
	}
}
