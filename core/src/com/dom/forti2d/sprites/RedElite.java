package com.dom.forti2d.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class RedElite extends Enemy {
	
	public RedElite(World world, float x, float y) {
		super(world, x, y, "character");
		this.health = 3f;
		this.healthBarSize = .2f / health;
	}

	protected void setAnimation() {
		Array<TextureRegion> frames = new Array<TextureRegion>();

		frames.add(new TextureRegion(getTexture(), 343, 138, 26, 36));
		frames.add(new TextureRegion(getTexture(), 310, 138, 26, 36));
		frames.add(new TextureRegion(getTexture(), 279, 138, 26, 36));
		frames.add(new TextureRegion(getTexture(), 252, 138, 26, 36));
		frames.add(new TextureRegion(getTexture(), 222, 138, 26, 36));
		frames.add(new TextureRegion(getTexture(), 198, 138, 26, 36));
		walk = new Animation<TextureRegion>(0.15f, frames);
		frames.clear();
	}

}
