package com.dom.forti2d.bullets;

import com.badlogic.gdx.physics.box2d.World;
import com.dom.forti2d.items.Gun;
import com.dom.forti2d.screens.Level;

public class RocketBullet extends Bullet {
	
	public RocketBullet(World world, float x, float y, float width, float height, float speed, boolean runningRight, Gun gun) {
		super(world, x, y, width, height, speed, runningRight, gun);
	}
	
	public void update(float delta) {
		super.update(delta);
		
		if (remove)
			Level.explosions.add(new Explosion(world, getWidth(), getHeight(), body));
	}
}
