package com.dom.forti2d.items;

import com.badlogic.gdx.graphics.Texture;

public class Rifle extends Gun {

	public Rifle(int tier, String item) {
		super(tier, item);
		setDamage();
	}

	public void setItemDrawable() {
		super.setItemDrawable(item);
	}
	
	public Texture getBulletTexture() {
		return new Texture("sprites/bullets/rifleBullet.png");
	}
	
	protected void setDamage() {
		switch (tier) {
			case 0:
				this.damage = .18f;
				break;
			case 1:
				this.damage = .22f;
				break;
			case 2:
				this.damage = .24f;
				break;
			case 3:
				this.damage = .3f;
				break;
		}
	}
}
