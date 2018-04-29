package com.dom.forti2d.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class BlueElite extends Enemy {
	
	public BlueElite(World world, float x, float y) {
		super(world, x, y);
		this.health = 2f;
		this.healthBarSize = .2f / health;
	}

	protected void setAnimation() {
		Array<TextureRegion> frames = new Array<TextureRegion>();

		frames.add(new TextureRegion(getTexture(), 343, 175, 26, 36));
		frames.add(new TextureRegion(getTexture(), 310, 175, 26, 36));
		frames.add(new TextureRegion(getTexture(), 279, 175, 26, 36));
		frames.add(new TextureRegion(getTexture(), 252, 175, 26, 36));
		frames.add(new TextureRegion(getTexture(), 222, 175, 26, 36));
		frames.add(new TextureRegion(getTexture(), 198, 175, 26, 36));
		walk = new Animation<TextureRegion>(0.15f, frames);
		frames.clear();
	}

}
