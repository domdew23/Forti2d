package com.dom.forti2d.objects;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

public class Obstacles extends NonInteractive {

	public Obstacles(TiledMap map, World world) {
		super(map, world, "Obstacles");
	}
}
