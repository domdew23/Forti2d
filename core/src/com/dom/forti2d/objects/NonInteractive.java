package com.dom.forti2d.objects;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * @ author Dominic Dewhurst
*/

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.dom.forti2d.tools.BodyBuilder;
import com.dom.forti2d.tools.Constants;

public abstract class NonInteractive {
	
	private byte cBits = Constants.NON_INTERACTIVE_BITS;
	private byte mBits = Constants.PLAYER_BITS | Constants.BULLET_BITS | Constants.ITEM_BITS;
	
	private static ArrayList<NonInteractive> objects = new ArrayList<NonInteractive>();
	
	public static HashMap<Integer, Rectangle> rects = new HashMap<Integer, Rectangle>();
	public static HashMap<Integer, NonInteractive> objs = new HashMap<Integer, NonInteractive>();
	
	public static HashMap<Integer, Rectangle> storedRects;
	public static HashMap<Integer, NonInteractive> storedObjs;

	public Body body;
	public Rectangle rect;
	
	public NonInteractive(TiledMap map, World world, String id) {
		int i = 0;
		for (MapObject object : map.getLayers().get(id).getObjects().getByType(RectangleMapObject.class)) {
			rect = ((RectangleMapObject) object).getRectangle();
			body = BodyBuilder.createBox(world, Constants.STATIC_BODY, rect, cBits, mBits, this);
			rects.put(i, rect);
			objs.put(i++, this);
		}
	}
	
	public static void storeMaps() {
		storedRects = new HashMap<Integer, Rectangle>(rects);
		storedObjs = new HashMap<Integer, NonInteractive>(objs);
	}
	
	public static void restoreMaps() {
		rects = storedRects;
		objs = storedObjs;
	}
	
	public static void clearMaps() {
		rects = new HashMap<Integer, Rectangle>();
		objs = new HashMap<Integer, NonInteractive>();
	}
	
	public static ArrayList<NonInteractive> getObjects(){
		return objects;
	}
}
