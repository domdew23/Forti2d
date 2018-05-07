package com.dom.forti2d.listeners;

import java.util.Iterator;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.dom.forti2d.bullets.Bullet;
import com.dom.forti2d.objects.Doors;
import com.dom.forti2d.objects.FinalDoor;
import com.dom.forti2d.objects.Interactive;
import com.dom.forti2d.screens.Level;
import com.dom.forti2d.screens.Level1;
import com.dom.forti2d.sprites.Enemy;
import com.dom.forti2d.sprites.Player;

public class CollisionListener implements ContactListener{
	
	public void beginContact(Contact contact) {
		Fixture a = contact.getFixtureA();
		Fixture b = contact.getFixtureB();
		
		boolean aDoor = a.getUserData() instanceof Doors;
		boolean aSensor = a.getUserData() == "sensor";
		boolean aFinalDoor = a.getUserData() instanceof FinalDoor;

		boolean bDoor = b.getUserData() instanceof Doors;
		boolean bSensor = b.getUserData() == "sensor";
		boolean bFinalDoor = b.getUserData() instanceof FinalDoor;
		
		System.out.println( a.getUserData() + " " +  b.getUserData());
		if (((aFinalDoor && bSensor) || (bFinalDoor && aSensor))) {
			System.out.println("here");
			Level1.changeScreen = true;
			Level1.finalHouse = true;
		}
		
		if (((aDoor && bSensor) || (bDoor && aSensor))) {
			Iterator<Float> it = Interactive.doors.keySet().iterator();

			float tmp = 0;
			while (it.hasNext()) {
				float x = it.next();
				Rectangle rect = Interactive.rects.get(x);
				if (Math.abs(Level.playerX - (rect.x / 100)) < 1) {
					tmp = rect.x;
				}
			}
			
			if (Interactive.doors.get(tmp) != null && !Interactive.doors.get(tmp)) {
				Interactive.doors.put(tmp, true);
				Level1.changeScreen = true;
			}

		} else if (a.getUserData() instanceof Bullet || b.getUserData() instanceof Bullet)
			handleBulletCollision(a, b);		
		else if ((a.getUserData() instanceof Enemy && b.getUserData() instanceof Player) || (b.getUserData() instanceof Enemy && a.getUserData() instanceof Player))
			handleEnemyCollision(a, b);
	}
	
	private void handleBulletCollision(Fixture a, Fixture b) {
		Bullet bullet = null;
		Enemy enemy = null;
		Player player = null;
		
		if (a.getUserData() instanceof Bullet)
			bullet = (Bullet) a.getUserData();
		else
			bullet = (Bullet) b.getUserData();
		
		if (a.getUserData() instanceof Enemy)
			enemy = (Enemy) a.getUserData();
		else if (b.getUserData() instanceof Enemy)
			enemy = (Enemy) b.getUserData();
		
		if (a.getUserData() instanceof Player)
			player = (Player) a.getUserData();
		else if (b.getUserData() instanceof Player)
			player = (Player) b.getUserData();	
		
		if (enemy != null)
			enemy.gotShot(bullet);
		
		if (player != null)
			player.gotShot(bullet);
		
		bullet.explode();	
	}
	
	private void handleEnemyCollision(Fixture a, Fixture b) {
		Player player = null;
		Enemy enemy = null;

		if (a.getUserData() instanceof Player)
			player = (Player) a.getUserData();
		else if (b.getUserData() instanceof Player)
			player = (Player) b.getUserData();
		
		if (a.getUserData() instanceof Enemy)
			enemy = (Enemy) a.getUserData();
		else if (b.getUserData() instanceof Enemy)
			enemy = (Enemy) b.getUserData();
		
		if (player != null)
			player.gotHit(enemy);
	}

	public void endContact(Contact contact) {
	}

	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	public void postSolve(Contact contact, ContactImpulse impulse) {
		
	}

}
