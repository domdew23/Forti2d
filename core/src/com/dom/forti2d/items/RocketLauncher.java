package com.dom.forti2d.items;

import com.badlogic.gdx.graphics.Texture;

public class RocketLauncher extends Gun {

	public RocketLauncher(int tier, String item) {
		super(tier, item);
		setDamage();
	}
	
	public void setItemDrawable() {
		super.setItemDrawable(item);
	}
	
	public Texture getBulletTexture() {
		return new Texture("sprites/bullets/rocketBullet.png");
	}
	
	protected void setDamage() {
		switch (tier) {
			case 0:
				this.damage = .7f;
				break;
			case 1:
				this.damage = .8f;
				break;
			case 2:
				this.damage = .9f;
				break;
			case 3:
				this.damage = 1f;
				break;
		}
	}
}
