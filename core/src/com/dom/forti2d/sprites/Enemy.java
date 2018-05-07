package com.dom.forti2d.sprites;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.dom.forti2d.bullets.Bullet;
import com.dom.forti2d.bullets.PistolBullet;
import com.dom.forti2d.items.Pistol;
import com.dom.forti2d.objects.NonInteractive;
import com.dom.forti2d.objects.Platforms;
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
	private boolean jumped;
	private float startY;
	private final float EPSILON = .02f;
	private CopyOnWriteArrayList<Bullet> bullets;
	private World world;
	private int shotCoolDown;
	
	public Enemy(World world, float x, float y) {
		super(Constants.ATLAS.findRegion("character"));
		this.world = world;
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
		this.jumped = false;
		this.bullets = new CopyOnWriteArrayList<Bullet>();
		this.shotCoolDown = 0;
		
		setBounds(getX(), getY(), 26 / Constants.SCALE, 23 / Constants.SCALE);
		setPosition(x, y);
		this.body = BodyBuilder.makeCharacterBody(world, x, y, radius, this);
		this.startY = body.getPosition().y;
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
	
	public void draw(SpriteBatch batch) {
		if(!isDead) {
			super.draw(batch);
			batch.draw(healthBar, body.getPosition().x - .11f,  body.getPosition().y + .14f, healthBarSize * health, .04f);
		}
		
		for (Bullet b : bullets)
			b.draw(batch);
	}
	
	public void update(float delta, Vector2 target) {
		if (!kill) {
			stateTimer += delta;
			
			if (Math.abs(target.x - body.getPosition().x) < 1.5)
				seek(target, delta);
			else
				checkChange();
			
		for (Bullet b : bullets) {
			if (b != null) {
				if (b.shouldRemove() && !world.isLocked()) {
					world.destroyBody(b.body);
					bullets.remove(b);
				}
				b.update(delta);
			}
		}
			
			setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
			setRegion(getFrame(delta));
		}
	}
	
	private void seek(Vector2 target, float delta) {
		Vector2 force = AI.seek(body, target);
		
		float distance = Float.MAX_VALUE;
		float tmpDistance = 0;
		Platforms closest = null;
		
		Iterator<Integer> it = NonInteractive.objs.keySet().iterator();
		
		while (it.hasNext()) {
			int id = it.next();
			NonInteractive platform = NonInteractive.objs.get(id);
			if (platform instanceof Platforms) {
				Vector2 center = new Vector2(0, 0);
				NonInteractive.rects.get(id).getCenter(center);

				tmpDistance = Math.abs(body.getPosition().x - (center.x / 100));
				if (tmpDistance < distance) {
					distance = tmpDistance;
					closest = (Platforms) platform;
				}
			}
		}
						
		if  (distance <= .8 && !jumped && body.getPosition().y <= closest.body.getPosition().y && target.y + .1 > .5) {
			force.y = 1f;
			jumped = true;
		} else {
			force.y = 0;
		}
		
		if (body.getPosition().y - startY < EPSILON)
			jumped = false;
		
		body.applyLinearImpulse(force, body.getWorldCenter(), true);
		
		if (Math.abs(target.y - body.getPosition().y) < 1)
			shoot(delta);
	}
	
	private void shoot(float delta) {
		if (shotCoolDown++ % 100 == 0)
			bullets.add(new PistolBullet(world, ((getX() - getWidth() / 2)), ((getY() + getHeight() / 2)), 6, 2, 5, walkingRight, new Pistol(world, getX(), getY(), 2, "Pistol")));
	}
	public void gotShot(Bullet bullet) {
		decrementHealth(bullet.getGun().getDamage());
	}
	
	protected abstract void setAnimation();
}
