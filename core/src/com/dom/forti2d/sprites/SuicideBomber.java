package com.dom.forti2d.sprites;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.dom.forti2d.GameMain;
import com.dom.forti2d.bullets.Explosion;
import com.dom.forti2d.objects.NonInteractive;
import com.dom.forti2d.objects.Platforms;
import com.dom.forti2d.screens.Level;
import com.dom.forti2d.tools.AI;

public class SuicideBomber extends Player {
	
	private static final float EPSILON = 0.02f;
	private World world;
	private boolean bomb;
	private CopyOnWriteArrayList<Enemy> enemies;
	private boolean jumped;
	private float startY;

	public SuicideBomber(World world, GameMain game) {
		super(world, game);
		this.world = world;
		this.bomb = false;
		this.jumped = false;
		setColor(1f, 0f, 0f, 1f);
	}
	
	public void createBody(World world, float x, float y) {
		super.createBody(world, x, y);
		startY = body.getPosition().y;
	}
	
	public void bomb(CopyOnWriteArrayList<Enemy> enemies, World world) {
		bomb = true;
		this.enemies = enemies;
	}
	
	private void explode() {
		Level.explosions.add(new Explosion(world, getWidth(), getHeight(), body, true));
		
		for (Enemy e : enemies) {
			if (Math.abs(this.body.getPosition().x - e.body.getPosition().x) < .5)
				e.decrementHealth(2f);
		}
		
		if (!world.isLocked())
			world.destroyBody(body);
		
		destroyed = true;
	}
	
	public void update(float delta) {
		super.update(delta);
		if (bomb) {
			Enemy closest = null;
			float distance = Float.MAX_VALUE;
			float tmpDistance = 0;
			
			for (Enemy e : enemies) {
				tmpDistance = Math.abs(body.getPosition().x - e.body.getPosition().x);
				if (tmpDistance < distance) {
					closest = e;
					distance = tmpDistance; 
				}
			}
			
			Vector2 force = seek(closest.body.getPosition(), delta);
			body.applyLinearImpulse(force, body.getWorldCenter(), true);
			
			if (Math.abs(this.body.getPosition().x - closest.body.getPosition().x) < .5)
				explode();

		}
	}
	
	protected Vector2 seek(Vector2 target, float delta) {
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
								
		if  (distance <= .8 && !jumped && body.getPosition().y <= closest.body.getPosition().y) {
			force.y = 1.3f;
			jumped = true;
		} else {
			force.y = 0;
		}
		
		if (body.getPosition().y - startY < EPSILON)
			jumped = false;
		
		return force;
	}
}
