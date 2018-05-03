package com.dom.forti2d.bullets;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.dom.forti2d.items.Gun;
import com.dom.forti2d.sprites.Player;
import com.dom.forti2d.tools.BodyBuilder;
import com.dom.forti2d.tools.Constants;

public class Bullet extends Sprite {

	private final float lifeTime;
	private float lifeTimer, dx;
	
	public Body body;
	
	protected Gun gun;
	
	private byte cBits = Constants.BULLET_BITS;
	private byte mBits = Constants.PLAYER_BITS | Constants.NON_INTERACTIVE_BITS;
	
	private boolean remove;
	
	public Bullet(World world, float x, float y, float width, float height, float speed, Player player, Gun gun) {
		super(gun.getBulletTexture());
		this.gun = gun;
		this.lifeTime = 1;
		
		x += .3f;
		y  -= .02f;
		
		if (player.runningRight)
			this.dx = Math.abs(MathUtils.sin(30) * speed);
		else {
			this.dx = MathUtils.sin(30) * speed;
			x -=  .38f;
			flip(true, false);
		}
		
		setBounds(getX(), getY(), width / Constants.SCALE, height / Constants.SCALE);
		setPosition(x, y);
		
		this.body = BodyBuilder.createBox(world, BodyType.DynamicBody, x * Constants.SCALE, y * Constants.SCALE, width, height, cBits, mBits, this);	
		this.body.setGravityScale(0);
		this.body.setLinearVelocity(dx, 0);
	}
	
	public void explode() {
		this.remove = true;
	}
	
	public boolean shouldRemove() {
		return remove;
	}
	
	public Gun getGun() {
		return gun;
	}
	
	public void draw(SpriteBatch batch) {
		if (!remove)
			super.draw(batch);
	}
	
	public void update(float delta) {
		lifeTimer += delta;
		setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
		
		if (lifeTimer > lifeTime)
			remove = true;
	}
}
