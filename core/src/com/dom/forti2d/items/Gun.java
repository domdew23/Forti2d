package com.dom.forti2d.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Gun extends Item {
	
	protected float damage;
	
	public Gun(World world, float x, float y, int tier, String item) {
		super(world, x, y, tier, item);
	}
	
	public float getDamage() {
		return damage;
	}
	
	public void setItemDrawable(String item) {
		this.itemDrawable = Drawables.getItem(tier, item);
	}
	
	public abstract Texture getBulletTexture();
	protected abstract void setDamage();
}
