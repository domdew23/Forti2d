package com.dom.forti2d.bullets;

import com.badlogic.gdx.physics.box2d.World;
import com.dom.forti2d.items.Gun;

public class PistolBullet extends Bullet {

	public PistolBullet(World world, float x, float y, float width, float height, float speed, boolean runningRight, Gun gun) {
		super(world, x, y, width, height, speed, runningRight, gun);
	}

}
