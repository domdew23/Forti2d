package com.dom.forti2d.tools;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.physics.box2d.World;
import com.dom.forti2d.sprites.BlueElite;
import com.dom.forti2d.sprites.Boss;
import com.dom.forti2d.sprites.Enemy;
import com.dom.forti2d.sprites.Grunt;
import com.dom.forti2d.sprites.RedElite;

public class EnemySpawner {
	
	private final static float y = 32f;

	public static CopyOnWriteArrayList<Enemy> spawnEnemies(World world, float min, float max){
		CopyOnWriteArrayList<Enemy> enemies = new CopyOnWriteArrayList<Enemy>();
		
		int numEnemies = ThreadLocalRandom.current().nextInt(5, 10);
		int offset = Math.round((max - min) / numEnemies);
		
		for (int x = Math.round(min), i = 0; i < numEnemies; i++, x+=offset) {
			if (ThreadLocalRandom.current().nextFloat() <= .6) {
				if (ThreadLocalRandom.current().nextFloat() <= .60) 
					enemies.add(new Grunt(world, x, y));
				else if (ThreadLocalRandom.current().nextFloat() <= .75) 
					enemies.add(new RedElite(world, x, y));
				else
					enemies.add(new BlueElite(world, x, y));
			}
		}
		return enemies;
	}
	
	public static CopyOnWriteArrayList<Enemy> getBoss(World world, CopyOnWriteArrayList<Enemy> enemies){
		enemies.add(new Boss(world, 200f, 32f));
		return enemies;
	}
}