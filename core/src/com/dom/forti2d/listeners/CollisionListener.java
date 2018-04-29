package com.dom.forti2d.listeners;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.dom.forti2d.objects.Doors;
import com.dom.forti2d.screens.Level1;

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
		}
		
	}

	public void endContact(Contact contact) {
	}

	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	public void postSolve(Contact contact, ContactImpulse impulse) {
		
	}

}
