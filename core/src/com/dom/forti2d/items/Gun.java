package com.dom.forti2d.items;

import com.badlogic.gdx.graphics.Texture;

public abstract class Gun extends Item {
	
	protected float damage;
	
	public Gun(int tier, String item) {
		super(tier, item);
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
