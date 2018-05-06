package com.dom.forti2d.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class Pistol extends Gun {

	public Pistol(World world, float x, float y, int tier, String item) {
		super(world, x, y, tier, item);
		setDamage();
	}
	
	public void setItemDrawable() {
		super.setItemDrawable(item);
	}
	
	public Texture getBulletTexture() {
		return new Texture("sprites/bullets/pistolBullet.png");
	}
	
	protected void setDamage() {
		switch (tier) {
			case 0:
				this.damage = .1f;
				break;
			case 1:
				this.damage = .13f;
				break;
			case 2:
				this.damage = .15f;
				break;
			case 3:
				this.damage = .2f;
				break;
		}
	}
}
