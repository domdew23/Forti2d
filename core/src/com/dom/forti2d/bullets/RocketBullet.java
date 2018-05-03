package com.dom.forti2d.bullets;

import com.badlogic.gdx.physics.box2d.World;
import com.dom.forti2d.items.Gun;
import com.dom.forti2d.screens.Level;
import com.dom.forti2d.sprites.Player;

public class RocketBullet extends Bullet {
	
	
	public RocketBullet(World world, float x, float y, float width, float height, float speed, Player player, Gun gun) {
		super(world, x, y, width, height, speed, player, gun);
	}
	
	public void update(float delta) {
		super.update(delta);
		
		if (remove)
			Level.explosions.add(new Explosion(world, getWidth(), getHeight(), body));
	}
}
