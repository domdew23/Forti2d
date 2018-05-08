package com.dom.forti2d.bullets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.dom.forti2d.screens.Level;
import com.dom.forti2d.tools.Constants;

public class Explosion extends Sprite {
	
	private final short width, height;
	private Animation<TextureRegion> explosion;
	private float stateTimer;
	private Body bulletBody;
	
	public boolean done;
	
	public Explosion(World world, float x, float y, Body bulletBody, boolean bomber) {
		super(Constants.ATLAS.findRegion("explosion"));
		this.bulletBody = bulletBody;
		this.width = 100;
		this.height = 100;
		this.stateTimer = 0;
		
		if (bomber)
			setBounds(getX(), getY(), 80 / Constants.SCALE, 80 / Constants.SCALE);
		else
			setBounds(getX(), getY(), 20 / Constants.SCALE, 20 / Constants.SCALE);

		setPosition(x, y);

		setAnimation();
	}
	
	private void setAnimation() {
		Array<TextureRegion> frames = new Array<TextureRegion>();
		
		for (int y = 2; y < 900; y+= 100) {
			for (int x = 2; x < 900; x+= 100)
				frames.add(new TextureRegion(getTexture(), x, y, width, height));
		}
		
		explosion = new Animation<TextureRegion>(0.01f, frames);
		frames.clear();
	}
	
	public void draw(SpriteBatch batch) {
		super.draw(batch);
	}
	
	public void update(float delta) {
		stateTimer += delta;
		setPosition(bulletBody.getPosition().x - getWidth() / 2, bulletBody.getPosition().y - getHeight() / 2);
		setRegion(explosion.getKeyFrame(stateTimer, false));
		
		if (explosion.isAnimationFinished(stateTimer))
			Level.explosions.remove(this);
	}
}
