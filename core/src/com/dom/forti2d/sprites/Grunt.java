package com.dom.forti2d.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Grunt extends Enemy {
	
	public Grunt(World world, float x, float y) {
		super(world, x, y, "character");
		this.health = 1f;
		this.healthBarSize = .2f / health;
	}

	protected void setAnimation() {
		Array<TextureRegion> frames = new Array<TextureRegion>();

		frames.add(new TextureRegion(getTexture(), 358, 69, 26, 36));
		frames.add(new TextureRegion(getTexture(), 326, 69, 26, 36));
		frames.add(new TextureRegion(getTexture(), 296, 69, 26, 36));
		frames.add(new TextureRegion(getTexture(), 270, 69, 26, 36));
		frames.add(new TextureRegion(getTexture(), 242, 69, 26, 36));
		frames.add(new TextureRegion(getTexture(), 215, 69, 26, 36));
		frames.add(new TextureRegion(getTexture(), 182, 69, 26, 36));

		walk = new Animation<TextureRegion>(0.15f, frames);
		frames.clear();
	}

}
