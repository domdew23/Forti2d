package com.dom.forti2d.listeners;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.dom.forti2d.bullets.Bullet;
import com.dom.forti2d.objects.Doors;
import com.dom.forti2d.screens.Level1;
import com.dom.forti2d.sprites.Enemy;

public class CollisionListener implements ContactListener{

	public void beginContact(Contact contact) {
		Fixture a = contact.getFixtureA();
		Fixture b = contact.getFixtureB();
		
		boolean aDoor = a.getUserData() instanceof Doors;
		boolean aSensor = a.getUserData() == "sensor";
		
		boolean bDoor = b.getUserData() instanceof Doors;
		boolean bSensor = b.getUserData() == "sensor";
		
		if ((aDoor && bSensor) || (bDoor && aSensor)) {
			Level1.changeScreen = true;
			return;
		}
		
		if (a.getUserData() instanceof Bullet || b.getUserData() instanceof Bullet) {
			handleBulletCollision(a, b);
		}
		
	}
	
	private void handleBulletCollision(Fixture a, Fixture b) {
		Bullet bullet = null;
		Enemy enemy = null;
		
		if (a.getUserData() instanceof Bullet)
			bullet = (Bullet) a.getUserData();
		else
			bullet = (Bullet) b.getUserData();
		
		if (a.getUserData() instanceof Enemy)
			enemy = (Enemy) a.getUserData();
		else if (b.getUserData() instanceof Enemy)
			enemy = (Enemy) b.getUserData();
		
		if (enemy != null)
			enemy.gotShot(bullet);
		
		bullet.explode();	
	}

	public void endContact(Contact contact) {
	}

	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	public void postSolve(Contact contact, ContactImpulse impulse) {
		
	}

}
