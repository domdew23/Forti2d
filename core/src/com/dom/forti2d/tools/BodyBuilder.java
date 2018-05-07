package com.dom.forti2d.tools;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.dom.forti2d.sprites.Enemy;

public class BodyBuilder {
	
	/* category bits - what am i
	 * mask bits - what do i collide with
	 */
	
	/* create box based on rectangle */
	public static Body createBox(World world, BodyType type, Rectangle rect, short cBits, short mBits, Object userData) {
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixDef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		Body body;
		
		bodyDef.type = type;
		bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / Constants.SCALE, (rect.getY() + rect.getHeight() / 2) / Constants.SCALE);
		
		body = world.createBody(bodyDef);
		shape.setAsBox((rect.getWidth() / 2) / Constants.SCALE, (rect.getHeight() / 2) / Constants.SCALE);
		fixDef.shape = shape;
		fixDef.filter.categoryBits = cBits;
		fixDef.filter.maskBits = mBits;
		body.createFixture(fixDef).setUserData(userData);
		return body;
	}
	
	/* create box based on dimensions 
	 * @param world - the world body belongs to
	 * @param type - type of body it is (dynamic or static)
	 * @param x - x coordinate of body
	 * @param y - y coordinate of body
	 * @param width - width of body's rectangle
	 * @param height - height of body's rectangle
	 * @param cBits - category bits (what the body is)
	 * @param mBits - mask bits ( what the body collides with)
	 */
	public static Body createSensor(World world, BodyType type, float x, float y, float width, float height, short cBits, short mBits, Object userData, boolean flipped) {
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixDef = new FixtureDef();
		Body body;
		
		bodyDef.type = type;
		bodyDef.position.set((x + width / 2) / Constants.SCALE, (y + height / 2) / Constants.SCALE);
		body = world.createBody(bodyDef);
		
		EdgeShape sensor = new EdgeShape();
		if (flipped)
			sensor.set(new Vector2((width / 2) / Constants.SCALE, 0), new Vector2((width / 2) / Constants.SCALE, (height / 2) / Constants.SCALE));
		else
			sensor.set(new Vector2((-width / 2) / Constants.SCALE, 0), new Vector2((-width / 2) / Constants.SCALE, (height / 2) / Constants.SCALE));
		fixDef.shape = sensor;
		fixDef.isSensor = true;
		fixDef.filter.categoryBits = cBits;
		fixDef.filter.maskBits = mBits;
		body.createFixture(fixDef).setUserData(userData);
		return body;
	}
	
	/* create circle body used for characters 
	 * @param world - the world body belongs to
	 * @param type - type of body it is (dynamic or static)
	 * @param x - x coordinate of body
	 * @param y - y coordinate of body
	 * @param radius - radius of body's circle
	 */
	public static Body createCircle(World world, BodyType type, float x, float y, float radius, Object userData) {
		Body body;
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x / Constants.SCALE, y / Constants.SCALE);
		bodyDef.type = type;
		body = world.createBody(bodyDef);
		
		FixtureDef fixDef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(radius / Constants.SCALE);
		fixDef.shape = shape;
		fixDef.filter.categoryBits = Constants.PLAYER_BITS;
		fixDef.filter.maskBits = Constants.NON_INTERACTIVE_BITS | Constants.PLAYER_BITS | Constants.BULLET_BITS;
		body.createFixture(fixDef).setUserData(userData);
		
		if (userData instanceof Enemy)
			return body;
		
		EdgeShape head = new EdgeShape();
		head.set(new Vector2(-5 / Constants.SCALE, 5 / Constants.SCALE), new Vector2(5 / Constants.SCALE, 5 / Constants.SCALE));
		fixDef.shape = head;
		fixDef.isSensor = true;
		fixDef.filter.categoryBits = Constants.INTERACTIVE_BITS;
		fixDef.filter.maskBits = Constants.INTERACTIVE_BITS;
		body.createFixture(fixDef).setUserData("sensor");
		
		return body;
	}
	
	public static Body createCircle(World world, BodyType type, float x, float y, float radius, byte cBits, byte mBits, Object userData) {
		Body body;
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x / Constants.SCALE, y / Constants.SCALE);
		bodyDef.type = type;
		body = world.createBody(bodyDef);
		
		FixtureDef fixDef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(radius / Constants.SCALE);
		fixDef.shape = shape;
		fixDef.filter.categoryBits = cBits;
		fixDef.filter.maskBits = mBits;
		body.createFixture(fixDef).setUserData(userData);
		
		return body;
	}
	
	
	public static Body makeCharacterBody(World world, float x, float y, float radius, Object userData) {
		return createCircle(world, Constants.DYNAMIC_BODY, x, y, radius, userData);
	}
	
	public static Body makeItemBody(World world, float x, float y, float radius, byte cBits, byte mBits, Object userData) {
		return createCircle(world, Constants.DYNAMIC_BODY, x, y, radius, cBits, mBits, userData);
	}
	
	public static Body makeBullet(World world, float x, float y, float width, float height, byte cBits, byte mBits, Object userData, boolean flipped) {
		return createSensor(world, Constants.DYNAMIC_BODY, x, y, width, height, cBits, mBits, userData, flipped);
	}
}
