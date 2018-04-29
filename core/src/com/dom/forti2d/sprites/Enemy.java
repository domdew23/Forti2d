package com.dom.forti2d.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.dom.forti2d.tools.BodyBuilder;
import com.dom.forti2d.tools.Constants;

public abstract class Enemy extends Sprite {
	
	public Body body;
	private float radius = 7;
	
	protected Animation<TextureRegion> walk;
	
	protected float stateTimer;
	protected float health;
	
	private boolean walkingRight;
	private boolean goingRight;
	
	private int count;
	
	protected Texture healthBar;
	protected float healthBarSize;

	public Enemy(World world, float x, float y) {
		super(Constants.ATLAS.findRegion("character"));
		this.stateTimer = 0;
		this.count = 0;
		this.goingRight = true;
		this.walkingRight = true;
		this.healthBar = new Texture("sprites/blank.png");
		
		setBounds(getX(), getY(), 26 / Constants.SCALE, 23 / Constants.SCALE);
		setPosition(x, y);
		this.body = BodyBuilder.makeCharacterBody(world, x, y, radius);
		setAnimation();
	}
	
	protected TextureRegion getFrame(float delta) {
		TextureRegion region = walk.getKeyFrame(stateTimer, true);
		
		if ((body.getLinearVelocity().x < 0 || !walkingRight) && region.isFlipX()) {
			region.flip(true, false);
			walkingRight = false;
		} else if ((body.getLinearVelocity().x > 0 || walkingRight) && !region.isFlipX()) {
			region.flip(true, false);
			walkingRight = true;
		}
		
		return region;
	}
	
	public void changeDirection() {
		walkingRight = walkingRight ? false : true;
		goingRight = goingRight ? false : true;
	}
	
	protected void checkChange() {		
		if(++count % 150 == 0) {
			changeDirection();
		}
		
		if (goingRight) {
			if (body.getLinearVelocity().x <= 0.5) body.applyLinearImpulse(new Vector2(0.05f, 0), body.getWorldCenter(), true);
		} else {
			if (body.getLinearVelocity().x >= -0.5) body.applyLinearImpulse(new Vector2(-0.05f, 0), body.getWorldCenter(), true);
		}
	}
	
	public void decrementHealth(float delta) {
		float tmp = health;
		if (tmp - delta >= 0) {
			health -= delta;
		} else {
			health = 0;
		}	
	}
	
	public void draw(SpriteBatch batch) {
		super.draw(batch);
		batch.draw(healthBar, body.getPosition().x - .11f,  body.getPosition().y + .14f, healthBarSize * health, .04f);
	}
	
	public void update(float delta) {
		stateTimer += delta;
		checkChange();
		setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
		setRegion(getFrame(delta));
	}
	
	protected abstract void setAnimation();
}
