package com.dom.forti2d.objects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.dom.forti2d.tools.BodyBuilder;
import com.dom.forti2d.tools.Constants;

public abstract class Interactive {
	public Interactive(TiledMap map, World world, String id) {
		for (MapObject object : map.getLayers().get(id).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			BodyBuilder.createBox(world, Constants.STATIC_BODY, rect, Constants.INTERACTIVE_BITS, Constants.INTERACTIVE_BITS);
		}
	}
}
