package com.dom.forti2d.objects;

import java.util.HashMap;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.dom.forti2d.tools.BodyBuilder;
import com.dom.forti2d.tools.Constants;

public abstract class Interactive {
	
	public static HashMap<Float, Boolean> doors = new HashMap<Float, Boolean>();
	public static HashMap<Float, Rectangle> rects = new HashMap<Float, Rectangle>();
	public static HashMap<Float, Boolean> storedDoors;
	public static HashMap<Float, Rectangle> storedRects;

	public Interactive(TiledMap map, World world, String id) {
		for (MapObject object : map.getLayers().get(id).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			BodyBuilder.createBox(world, Constants.STATIC_BODY, rect, Constants.INTERACTIVE_BITS, Constants.INTERACTIVE_BITS, this);
			doors.put(rect.x, false);
			rects.put(rect.x, rect);
		}
	}
	
	public static void storeMaps() {
		storedDoors = new HashMap<Float, Boolean>(doors);
		storedRects = new HashMap<Float, Rectangle>(rects);
	}
	
	public static void restoreMaps() {
		doors = storedDoors;
		rects = storedRects;
	}
	
	public static void clearMaps() {
		doors = new HashMap<Float, Boolean>();
		rects = new HashMap<Float, Rectangle>();
	}
}
