package com.dom.forti2d.bullets;

import com.badlogic.gdx.physics.box2d.World;

public class RifleBullet extends Bullet {

	public RifleBullet(World world, float x, float y, float radians, String textureFile) {
		super(world, x, y, radians, textureFile);
	}

}
