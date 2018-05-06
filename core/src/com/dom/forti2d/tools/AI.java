package com.dom.forti2d.tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class AI {

	public static Vector2 seek(Body srcBody, Vector2 target) {
		float maxSpeed = 1f;
		float maxForce = 1f;
		Vector2 velocity = srcBody.getLinearVelocity();
		
		Vector2 src = srcBody.getPosition();
		
		Vector2 desired = target.cpy().sub(src);
		desired.nor();
		desired.scl(maxSpeed);
		
		Vector2 force = desired.sub(velocity);
		force.limit(maxForce);
		return force;
	}
}
