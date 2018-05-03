package com.dom.forti2d.bullets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.dom.forti2d.screens.Level;
import com.dom.forti2d.tools.BodyBuilder;
import com.dom.forti2d.tools.Constants;

public class Explosion extends Sprite {

	private final short firstFrameX = 9, secondFrameX = 41, thirdFrameX = 73, fourthFrameX = 98, fifthFrameX = 127, sixthFrameX = 155;
	private final short spriteWidth = 20, spriteHeight = 32, noGunY = 101;
	private Animation<TextureRegion> explosion;
	private Body body;
	private float stateTimer;
	private Body bulletBody;
	
	public boolean done;
	private World world;
	
	public Explosion(World world, float x, float y, Body bulletBody) {
		super(Constants.ATLAS.findRegion("character"));
		this.world = world;
		this.bulletBody = bulletBody;
		this.stateTimer = 0;
		
		setBounds(getX(), getY(), 18 / Constants.SCALE, 22 / Constants.SCALE);
		setPosition(x, y);
		this.body = BodyBuilder.makeCharacterBody(world, bulletBody.getPosition().x * 100, bulletBody.getPosition().y * 100, 8, this);
		this.body.setGravityScale(0);

		setAnimation();
	}
	
	private void setAnimation() {
		Array<TextureRegion> frames = new Array<TextureRegion>();
		
		frames.add(new TextureRegion(getTexture(), firstFrameX, noGunY, spriteWidth, spriteHeight));
		frames.add(new TextureRegion(getTexture(), secondFrameX, noGunY, spriteWidth, spriteHeight));
		frames.add(new TextureRegion(getTexture(), thirdFrameX, noGunY, spriteWidth, spriteHeight));
		frames.add(new TextureRegion(getTexture(), fourthFrameX, noGunY, spriteWidth, spriteHeight));
		frames.add(new TextureRegion(getTexture(), fifthFrameX, noGunY, spriteWidth, spriteHeight));
		frames.add(new TextureRegion(getTexture(), sixthFrameX, noGunY, spriteWidth, spriteHeight));
		explosion = new Animation<TextureRegion>(0.15f, frames);
		frames.clear();
	}
	
	public void draw(SpriteBatch batch) {
		super.draw(batch);
	}
	
	public void update(float delta) {
		stateTimer += delta;
		setPosition(bulletBody.getPosition().x - getWidth() / 2, bulletBody.getPosition().y - getHeight() / 2);
		setRegion(explosion.getKeyFrame(stateTimer, false));
		
		if (explosion.isAnimationFinished(stateTimer)) {
			done = true;
			Level.explosions.remove(this);
			if (!world.isLocked())
				world.destroyBody(this.body);
		}
	}
}
