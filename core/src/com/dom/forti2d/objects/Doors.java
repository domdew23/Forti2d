package com.dom.forti2d.objects;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

public class Doors extends Interactive {

	public Doors(TiledMap map, World world) {
		super(map, world, "Doors");
	}

}
