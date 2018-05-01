package com.dom.forti2d.bullets;

import com.badlogic.gdx.physics.box2d.World;
import com.dom.forti2d.sprites.Player;

public class RocketBullet extends Bullet {

	public RocketBullet(World world, float x, float y, float radians, String textureFile, Player player) {
		super(world, x, y, radians, textureFile, player);
	}

}
