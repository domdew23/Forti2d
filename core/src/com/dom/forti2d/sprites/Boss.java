package com.dom.forti2d.sprites;

import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.dom.forti2d.bullets.Bullet;
import com.dom.forti2d.tools.BodyBuilder;
import com.dom.forti2d.tools.Constants;

public class Boss extends Enemy {
	
	private Animation<TextureRegion> creeping;
	private Animation<TextureRegion> running;
	private Animation<TextureRegion> jabbing;
	private Animation<TextureRegion> slashing;
	private Animation<TextureRegion> hit;
	private TextureRegion dead;

	private enum State {
		CREEPING,
		RUNNING,
		JABBING,
		SLASHING,
		HIT,
		DEAD
	}
	
	private State currentState;
	private State previousState;
	
	private boolean seeking, playerInRange, gotHit;

	public Boss(World world, float x, float y) {
		super(world, x, y, "firstboss");
		this.health = 6f;
		this.healthBarSize = .2f / health;
		this.seeking = false;
		this.playerInRange = false;
		this.currentState = State.CREEPING;
		this.previousState = State.CREEPING;
		
		setBounds(getX(), getY(), 24 / Constants.SCALE, 35 / Constants.SCALE);
		setPosition(x, y);
		
		if (!world.isLocked())
			world.destroyBody(body);
		
		this.body = BodyBuilder.makeCharacterBody(world, x, y, 14, this);
		setAnimation();
	}

	protected void setAnimation() {
		Array<TextureRegion> frames = new Array<TextureRegion>();

		// creeping (24 bound)
		frames.add(new TextureRegion(getTexture(), 122, 517, 50, 68));
		frames.add(new TextureRegion(getTexture(), 172, 517, 50, 68));
		frames.add(new TextureRegion(getTexture(), 222, 517, 50, 68));
		creeping = new Animation<TextureRegion>(0.15f, frames);
		frames.clear();
		
		//running ( 24 bound)
		frames.add(new TextureRegion(getTexture(), 76, 582, 50, 65));
		frames.add(new TextureRegion(getTexture(), 126, 585, 50, 65));
		frames.add(new TextureRegion(getTexture(), 176, 585, 50, 65));
		frames.add(new TextureRegion(getTexture(), 226, 585, 50, 65));
		running = new Animation<TextureRegion>(0.15f, frames);
		frames.clear();

		//jabbing (36 bound)
		frames.add(new TextureRegion(getTexture(), 80, 704, 86, 63));
		jabbing = new Animation<TextureRegion>(0.15f, frames);
		frames.clear();

		//slashing (36 bound)
		frames.add(new TextureRegion(getTexture(), 1, 762, 86, 63));
		frames.add(new TextureRegion(getTexture(), 87, 762, 86, 63));
		frames.add(new TextureRegion(getTexture(), 186, 762, 86, 57));
		slashing = new Animation<TextureRegion>(0.15f, frames);
		frames.clear();

		//hit (24 bound)
		frames.add(new TextureRegion(getTexture(), 186, 816, 50, 68));
		frames.add(new TextureRegion(getTexture(), 232, 816, 45, 68));
		hit = new Animation<TextureRegion>(.4f, frames);
		frames.clear();

		//dead
		dead = new TextureRegion(getTexture(), 116, 816, 70, 68);
	}
	
	protected TextureRegion getFrame(float delta) {
		currentState = getState();
		TextureRegion region;
		
		switch (currentState) {
			case CREEPING:
				setBounds(getX(), getY(), 24 / Constants.SCALE, 35 / Constants.SCALE);
				region = creeping.getKeyFrame(stateTimer, true); 
				break;
			case RUNNING:
				setBounds(getX(), getY(), 24 / Constants.SCALE, 35 / Constants.SCALE);
				region = running.getKeyFrame(stateTimer, true); 
				break;
			case JABBING:
				setBounds(getX(), getY(), 36 / Constants.SCALE, 35 / Constants.SCALE);
				region = jabbing.getKeyFrame(stateTimer, true); 
				break;
			case SLASHING:
				setBounds(getX(), getY(), 36 / Constants.SCALE, 35 / Constants.SCALE);
				region = slashing.getKeyFrame(stateTimer, true); 
				break;
			case HIT: 
				setBounds(getX(), getY(), 24 / Constants.SCALE, 35 / Constants.SCALE);
				region = hit.getKeyFrame(stateTimer, false); 
				break;
			case DEAD:
				setBounds(getX(), getY(), 24 / Constants.SCALE, 35 / Constants.SCALE);
				region = dead; 
				break;
			default: 
				setBounds(getX(), getY(), 24 / Constants.SCALE, 35 / Constants.SCALE);
				return creeping.getKeyFrame(stateTimer, true);
		}
		
		flip(region);
		
		stateTimer = currentState == previousState ? stateTimer + delta : 0;
		previousState = currentState;
		
		return region;
	}
	
	private State getState() {
		if (gotHit)
			return State.HIT;
		else if (seeking && playerInRange)
			return State.SLASHING;
		else if (seeking)
			return State.RUNNING;
		else
			return State.CREEPING;
	}
	
	public void gotShot(Bullet bullet) {
		super.gotShot(bullet);
		if (ThreadLocalRandom.current().nextDouble() < .4)
			gotHit = true;
	}
	
	public void update(float delta, Vector2 target) {
		if (!kill) {
			Vector2 force = new Vector2();

			if (Math.abs(target.x - body.getPosition().x) < 1.5 || health < 4f) {
				seeking = true;
				force = seek(target, delta);
				if (Math.abs(target.x - body.getPosition().x) < .5)
					playerInRange = true;
				else
					playerInRange = false;
			} else
				force = checkChange();
			
			if (gotHit) {
				if (hit.isAnimationFinished(stateTimer))
					gotHit = false;
			}
			
			if (!gotHit)
				body.applyLinearImpulse(force, body.getWorldCenter(), true);
			else
				body.setLinearVelocity(new Vector2(0, 0));
				
			setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
			setRegion(getFrame(delta));
		} else {
			setRegion(dead);
		}
	}
}
