package com.dom.forti2d.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.dom.forti2d.tools.BodyBuilder;
import com.dom.forti2d.tools.Constants;

public class Player extends Sprite {
	
	private final float radius = 8;
	
	/* variables for texture coordinate mappings */
	private final short spriteWidth = 20, spriteHeight = 32, noGunY = 101, pistolY = 134, rifleY = 171, rocketLauncherY = 212, paddingRocketLauncher = 3;
	private final short firstFrameX = 9, secondFrameX = 41, thirdFrameX = 73, fourthFrameX = 98, fifthFrameX = 127, sixthFrameX = 155, padding = 1;
	
	private float stateTimer;
	public float health;
	public float sheild;
	
	/* state space of the playable character */
	private enum State {
		IDLE_NO_GUN,
		IDLE_PISTOL,
		IDLE_RIFLE,
		IDLE_ROCKET_LAUNCHER,
		RUNNING_NO_GUN,
		RUNNING_PISTOL,
		RUNNING_RIFLE,
		RUNNING_ROCKET_LAUNCHER,
		JUMPING_NO_GUN,
		JUMPING_PISTOL,
		JUMPING_RIFLE,
		JUMPING_ROCKET_LAUNCHER,
		DEAD
	};
	
	private TextureRegion idleNoGun;
	private TextureRegion idlePistol;
	private TextureRegion idleRifle;
	private TextureRegion idleRocketLauncher;
	private TextureRegion jumpingNoGun;
	private TextureRegion jumpingPistol;
	private TextureRegion jumpingRifle;
	private TextureRegion jumpingRocketLauncher;
	
	private Animation<TextureRegion> runningNoGun;
	private Animation<TextureRegion> runningPistol;
	private Animation<TextureRegion> runningRifle;
	private Animation<TextureRegion> runningRocketLauncher;
	
	public State currentState;
	public State previousState;
	
	public Body body;
	
	private boolean runningRight;
	
	public boolean hasNoGun;
	public boolean hasPistol;
	public boolean hasRifle;
	public boolean hasRocketLauncher;
	
	public Player(World world) {
		super(Constants.ATLAS.findRegion("character"));
		this.currentState = State.IDLE_NO_GUN;
		this.previousState = State.IDLE_NO_GUN;
		this.runningRight = true;
		this.hasNoGun = true;
		this.hasPistol = false;
		this.hasRifle = false;
		this.hasRocketLauncher = false;
		this.stateTimer = 0;
		this.health = 1;
		this.sheild = 1;
		
		setTextures();
		setAnimations();
		
		setBounds(0, 0, 18 / Constants.SCALE, 22 / Constants.SCALE);
		setRegion(idleNoGun);
	}
	
	private void setTextures() {
		idleNoGun = new TextureRegion(getTexture(), sixthFrameX, noGunY, spriteWidth, spriteHeight);
		idlePistol = new TextureRegion(getTexture(), sixthFrameX, pistolY, spriteWidth, spriteHeight);
		idleRifle = new TextureRegion(getTexture(), sixthFrameX, rifleY, spriteWidth, spriteHeight);
		idleRocketLauncher = new TextureRegion(getTexture(), sixthFrameX, rocketLauncherY, spriteWidth, spriteHeight);
		
		jumpingNoGun = new TextureRegion(getTexture(), fourthFrameX, noGunY, spriteWidth, spriteHeight);
		jumpingPistol = new TextureRegion(getTexture(), fourthFrameX, pistolY, spriteWidth, spriteHeight);
		jumpingRifle = new TextureRegion(getTexture(), fourthFrameX, rifleY, spriteWidth + padding, spriteHeight);
		jumpingRocketLauncher = new TextureRegion(getTexture(), fourthFrameX + 3, rocketLauncherY, spriteWidth + padding, spriteHeight);
	}
	
	/* hard coded coordinate mappings from the texture atlas to animations */
	private void setAnimations() {
		Array<TextureRegion> frames = new Array<TextureRegion>();
		
		frames.add(new TextureRegion(getTexture(), firstFrameX, noGunY, spriteWidth, spriteHeight));
		frames.add(new TextureRegion(getTexture(), secondFrameX, noGunY, spriteWidth, spriteHeight));
		frames.add(new TextureRegion(getTexture(), thirdFrameX, noGunY, spriteWidth, spriteHeight));
		frames.add(new TextureRegion(getTexture(), fourthFrameX, noGunY, spriteWidth, spriteHeight));
		frames.add(new TextureRegion(getTexture(), fifthFrameX, noGunY, spriteWidth, spriteHeight));
		frames.add(new TextureRegion(getTexture(), sixthFrameX, noGunY, spriteWidth, spriteHeight));
		runningNoGun = new Animation<TextureRegion>(0.15f, frames);
		frames.clear();
				
		frames.add(new TextureRegion(getTexture(), firstFrameX, pistolY, spriteWidth, spriteHeight));
		frames.add(new TextureRegion(getTexture(), secondFrameX, pistolY, spriteWidth, spriteHeight));
		frames.add(new TextureRegion(getTexture(), thirdFrameX, pistolY, spriteWidth, spriteHeight));
		frames.add(new TextureRegion(getTexture(), fourthFrameX, pistolY, spriteWidth, spriteHeight));
		frames.add(new TextureRegion(getTexture(), fifthFrameX, pistolY, spriteWidth, spriteHeight));
		frames.add(new TextureRegion(getTexture(), sixthFrameX, pistolY, spriteWidth, spriteHeight));
		runningPistol = new Animation<TextureRegion>(0.15f, frames);
		frames.clear();
		
		frames.add(new TextureRegion(getTexture(), firstFrameX, rifleY, spriteWidth + padding, spriteHeight));
		frames.add(new TextureRegion(getTexture(), secondFrameX, rifleY, spriteWidth + padding, spriteHeight));
		frames.add(new TextureRegion(getTexture(), thirdFrameX, rifleY, spriteWidth + padding, spriteHeight));
		frames.add(new TextureRegion(getTexture(), fourthFrameX, rifleY, spriteWidth + padding, spriteHeight));
		frames.add(new TextureRegion(getTexture(), fifthFrameX, rifleY, spriteWidth + padding, spriteHeight));
		frames.add(new TextureRegion(getTexture(), sixthFrameX, rifleY, spriteWidth + padding, spriteHeight));
		runningRifle = new Animation<TextureRegion>(0.15f, frames);
		frames.clear();
		
		frames.add(new TextureRegion(getTexture(), firstFrameX + paddingRocketLauncher, rocketLauncherY, spriteWidth + padding, spriteHeight));
		frames.add(new TextureRegion(getTexture(), secondFrameX + paddingRocketLauncher, rocketLauncherY, spriteWidth + padding, spriteHeight));
		frames.add(new TextureRegion(getTexture(), thirdFrameX + paddingRocketLauncher, rocketLauncherY, spriteWidth + padding, spriteHeight));
		frames.add(new TextureRegion(getTexture(), fourthFrameX + paddingRocketLauncher, rocketLauncherY, spriteWidth + padding, spriteHeight));
		frames.add(new TextureRegion(getTexture(), fifthFrameX + paddingRocketLauncher, rocketLauncherY, spriteWidth + padding, spriteHeight));
		frames.add(new TextureRegion(getTexture(), sixthFrameX + 2, rocketLauncherY, spriteWidth, spriteHeight));
		runningRocketLauncher = new Animation<TextureRegion>(0.15f, frames);
		frames.clear();
	}
	
	/* state machine 
	 * @return - returns current frame based on state 
	 */
	public TextureRegion getFrame(float delta) {
		currentState = getState();
		TextureRegion region;
		
		switch(currentState) {
			case IDLE_NO_GUN: region = idleNoGun; break;
			case IDLE_PISTOL: region = idlePistol; break;
			case IDLE_RIFLE: region = idleRifle; break;
			case IDLE_ROCKET_LAUNCHER: region = idleRocketLauncher; break;
			case RUNNING_NO_GUN: region = runningNoGun.getKeyFrame(stateTimer, true); break;
			case RUNNING_PISTOL: region = runningPistol.getKeyFrame(stateTimer, true); break;
			case RUNNING_RIFLE: region = runningRifle.getKeyFrame(stateTimer, true); break;
			case RUNNING_ROCKET_LAUNCHER: region = runningRocketLauncher.getKeyFrame(stateTimer, true); break;
			case JUMPING_NO_GUN: region = jumpingNoGun; break;
			case JUMPING_PISTOL: region = jumpingPistol; break;
			case JUMPING_RIFLE: region = jumpingRifle; break;
			case JUMPING_ROCKET_LAUNCHER: region = jumpingRocketLauncher; break;
			default: region = idleNoGun; break;
		}
		
		if ((body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
			region.flip(true, false);
			runningRight = false;
		} else if ((body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
			region.flip(true, false);
			runningRight = true;
		}
		
		stateTimer = currentState == previousState ? stateTimer + delta : 0;
		previousState = currentState;
		return region;
	}
	
	public State getState() {
		if (body.getLinearVelocity().y > 0 || body.getLinearVelocity().y < 0)
			 return getJumpingState();
		else if (body.getLinearVelocity().x != 0)
			return getRunningState();
		else 
			return getIdleState();
	}
	
	private State getJumpingState() {
		if (hasNoGun)
			return State.JUMPING_NO_GUN;
		else if (hasPistol)
			return State.JUMPING_PISTOL;
		else if (hasRifle)
			return State.JUMPING_RIFLE;
		else if (hasRocketLauncher)
			return State.JUMPING_ROCKET_LAUNCHER;
		else
			return null;
	}
	
	private State getRunningState() {
		if (hasNoGun)
			return State.RUNNING_NO_GUN;
		else if (hasPistol)
			return State.RUNNING_PISTOL;
		else if (hasRifle)
			return State.RUNNING_RIFLE;
		else if (hasRocketLauncher)
			return State.RUNNING_ROCKET_LAUNCHER;
		else
			return null;
	}
	
	private State getIdleState() {
		if (hasNoGun)
			return State.IDLE_NO_GUN;
		else if (hasPistol)
			return State.IDLE_PISTOL;
		else if (hasRifle)
			return State.IDLE_RIFLE;
		else if (hasRocketLauncher)
			return State.IDLE_ROCKET_LAUNCHER;
		else
			return null;
	}
	
	public void createBody(World world, float x, float y) {
		this.body = BodyBuilder.makeCharacterBody(world, x, y, radius);
		this.body.setUserData(this);
	}
	
	public void decrementHealth(float delta) {
		if (sheild <= 0) {
			float tmp = health;
			if (tmp - delta >= 0) {
				health -= delta;
			} else {
				health = 0;
			}
		} else {
			float tmp = sheild;
			if (tmp - delta >= 0) {
				sheild -= delta;
			} else {
				sheild = 0;
				health -= delta;
			}
		}
	}
	
	public void shoot() {
		
	}
	
	public void update(float delta) {
		setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
		setRegion(getFrame(delta));
	}
}
