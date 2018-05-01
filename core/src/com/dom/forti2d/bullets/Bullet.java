package com.dom.forti2d.bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.dom.forti2d.sprites.Player;
import com.dom.forti2d.tools.BodyBuilder;
import com.dom.forti2d.tools.Constants;

public class Bullet extends Sprite {

	private final float lifeTime, width, height;
	private float lifeTimer, dx;
	
	public Body body;
	// pistol - 6, 2 - 5
	// rocket 16, 6 - 3
	// rifle 8, 4 - 4
	
	private byte cBits = Constants.PLAYER_BITS;
	private byte mBits = Constants.PLAYER_BITS | Constants.NON_INTERACTIVE_BITS;
	
	private boolean remove;
	
	public Bullet(World world, float x, float y, float radians, String textureFile, Player player) {
		super(new Texture(textureFile));
		this.lifeTime = 1;
		this.width = 8;
		this.height = 4;
		
		if (player.runningRight)
			this.dx = Math.abs(MathUtils.sin(radians) * 4f);
		else {
			this.dx = MathUtils.sin(radians) * 4f;
			x -=  20;
			this.flip(true, false);
		}
		
		setBounds(getX(), getY(), width / Constants.SCALE, height / Constants.SCALE);
		setPosition(x / Constants.SCALE, y / Constants.SCALE);
		
		this.body = BodyBuilder.createBox(world, BodyType.DynamicBody, x, y, width, height, cBits, mBits);	
		this.body.setGravityScale(0);
		this.body.setLinearVelocity(dx, 0);
	}
	
	public boolean shouldRemove() {
		return remove;
	}
	
	public void update(float delta) {
		lifeTimer += delta;
		setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
		
		if (lifeTimer > lifeTime)
			remove = true;
	}
}
