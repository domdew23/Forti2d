package com.dom.forti2d.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.dom.forti2d.bullets.Bullet;
import com.dom.forti2d.items.Gun;
import com.dom.forti2d.tools.AI;
import com.dom.forti2d.tools.BodyBuilder;
import com.dom.forti2d.tools.Constants;

public abstract class Enemy extends Sprite {
	
	public Body body;
	private float radius = 7;
	
	protected Animation<TextureRegion> walk;
	
	protected float stateTimer, health, healthBarSize, maxSpeed, maxForce;
	
	protected Vector2 velocity;
	
	private boolean walkingRight;
	private boolean goingRight;
	
	private int count;
	
	protected Texture healthBar;
	public boolean isDead, kill;

	public Enemy(World world, float x, float y) {
		super(Constants.ATLAS.findRegion("character"));
		this.stateTimer = 0;
		this.count = 0;
		this.goingRight = true;
		this.walkingRight = true;
		this.healthBar = new Texture("sprites/blank.png");
		this.isDead = false;
		this.kill = false;
		this.maxSpeed = .1f;
		this.maxForce = .05f;
		this.velocity = new Vector2(0, 0);
		
		setBounds(getX(), getY(), 26 / Constants.SCALE, 23 / Constants.SCALE);
		setPosition(x, y);
		this.body = BodyBuilder.makeCharacterBody(world, x, y, radius, this);
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
		if(++count % 150 == 0)
			changeDirection();
		
		if (goingRight) {
			if (body.getLinearVelocity().x <= 0.5) 
				body.applyLinearImpulse(new Vector2(0.05f, 0), body.getWorldCenter(), true);
		} else {
			if (body.getLinearVelocity().x >= -0.5)
				body.applyLinearImpulse(new Vector2(-0.05f, 0), body.getWorldCenter(), true);
		}
	}
	
	public void decrementHealth(float delta) {
		float tmp = health;
		if (tmp - delta >= 0)
			health -= delta;
		else
			health = 0;
		
		if (health <= 0)
			kill = true;
	}
	
	public void seek(Vector2 target) {
		Vector2 desired = target.cpy().sub(this.body.getPosition());
		desired.nor();
		desired.scl(maxSpeed);
		
		Vector2 force = desired.sub(velocity);
		force.limit(maxForce);
		body.applyLinearImpulse(force, body.getWorldCenter(), true);
	}
	
	public void draw(SpriteBatch batch) {
		if(!isDead) {
			super.draw(batch);
			batch.draw(healthBar, body.getPosition().x - .11f,  body.getPosition().y + .14f, healthBarSize * health, .04f);
		}
	}
	
	public void update(float delta, Vector2 target) {
		if (!kill) {
			stateTimer += delta;
			Vector2 force = AI.seek(body, target);
			force.y = 0;
			
			//body.applyLinearImpulse(force, body.getWorldCenter(), true);
			setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
			setRegion(getFrame(delta));
		}
	}
	
	public void gotShot(Bullet bullet) {
		Gun gun = bullet.getGun();
		float damage = gun.getDamage();
		decrementHealth(damage);
	}
	
	protected abstract void setAnimation();
}
