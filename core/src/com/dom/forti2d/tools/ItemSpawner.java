package com.dom.forti2d.tools;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.physics.box2d.World;
import com.dom.forti2d.items.BulletItem;
import com.dom.forti2d.items.Health;
import com.dom.forti2d.items.Item;
import com.dom.forti2d.items.Pistol;
import com.dom.forti2d.items.Rifle;
import com.dom.forti2d.items.RocketLauncher;
import com.dom.forti2d.items.Sheild;

public class ItemSpawner {
	
	private static int pistolSpawnRate = 15;
	private static int rifleSpawnRate = pistolSpawnRate + 10;
	private static int rocketLauncherSpawnRate = rifleSpawnRate + 5;
	private static int healthSpawnRate = rocketLauncherSpawnRate + 20;
	private static int sheildSpawnRate = healthSpawnRate + 40;
	private static int ammoSpawnRate = sheildSpawnRate + 30;
	
	private static int greyTierSpawnRate = 50;
	private static int redTierSpawnRate = greyTierSpawnRate + 25;
	private static int blueTierSpawnRate = redTierSpawnRate + 15;
	private static int goldTierSpawnRate = blueTierSpawnRate + 10;
	
	private static int pistolAmmoSpawnRate = 50;
	private static int rifleAmmoSpawnRate = pistolAmmoSpawnRate + 30;
	private static int rocketAmmoSpawnRate = rifleAmmoSpawnRate + 20;
	
	private static int numItems = 16;
	private static float y = 100f;

	public static CopyOnWriteArrayList<Item> spawnItems(World world, float min, float max){
		CopyOnWriteArrayList<Item> items = new CopyOnWriteArrayList<Item>();
		int[] possibleItems = initItemSpawnRates();
		int[] possibleTiers = initTierSpawnRates();
		int[] possibleAmmo = initAmmoSpawnRates();

		int offset = Math.round((max - min) / numItems);
		
		for (int x = Math.round(min), i = 0; i < numItems; i++, x+=offset) {
			if (ThreadLocalRandom.current().nextDouble() <= .75) {
				int itemID = possibleItems[ThreadLocalRandom.current().nextInt(100)];
				int tierID = possibleTiers[ThreadLocalRandom.current().nextInt(100)];
				if (itemID == 0 || itemID == 1 || itemID == 2)
					items.add(getGun(world, itemID, tierID, x));
				else if (itemID == 3 || itemID == 4)
					items.add(getHeal(world, itemID, x));
				else
					items.add(getAmmo(world, possibleAmmo, x));
			}
		}
		return items;
	}
	
	private static int[] initItemSpawnRates() {
		int[] possibleItems = new int[100]; /* 0 - pistol, 1 - rifle, 2 - rocketLauncher, 3 - health, 4 - sheild, - 5 - ammo */
		
		for (int i = 0; i < possibleItems.length; i++) {
			if (i <= pistolSpawnRate)
				possibleItems[i] = 0;
			else if (i <= rifleSpawnRate)
				possibleItems[i] = 1;
			else if (i <= rocketLauncherSpawnRate)
				possibleItems[i] = 2;
			else if (i<= healthSpawnRate)
				possibleItems[i] = 3;
			else if (i <= sheildSpawnRate)
				possibleItems[i] = 4;
			else if (i <= ammoSpawnRate)
				possibleItems[i] = 5;
		}
		
		return possibleItems;
	}
	
	private static int[] initTierSpawnRates() {
		int[] possibleTiers = new int[100]; /* 0 - grey, 1 - red, 2 - blue, 3 - gold */
		
		for (int i = 0; i < possibleTiers.length; i++) {
			if (i <= greyTierSpawnRate)
				possibleTiers[i] = 0;
			else if (i <= redTierSpawnRate)
				possibleTiers[i] = 1;
			else if (i <= blueTierSpawnRate)
				possibleTiers[i] = 2;
			else if (i<= goldTierSpawnRate)
				possibleTiers[i] = 3;
		}
		
		return possibleTiers;
	}
	
	private static int[] initAmmoSpawnRates() {
		int[] possibleAmmo = new int[100]; /* 0 - pistol, 1 - rifle, 2 - rockets */
		
		for (int i = 0; i < possibleAmmo.length; i++) {
			if (i <= pistolAmmoSpawnRate)
				possibleAmmo[i] = 0;
			else if (i <= rifleAmmoSpawnRate)
				possibleAmmo[i] = 1;
			else if (i <= rocketAmmoSpawnRate)
				possibleAmmo[i] = 2;
		}
		
		return possibleAmmo;
	}
	
	private static Item getGun(World world, int itemID, int tier, float x) {
		switch (itemID) {
			case 0:
				return new Pistol(world, x, y, tier, "Pistol");
			case 1:
				return new Rifle(world, x, y, tier, "Rifle");
			case 2:
				return new RocketLauncher(world, x, y, tier, "RocketLauncher");
			default:
				return null;
		}
	}
	
	private static Item getHeal(World world, int itemID, float x) {
		switch (itemID) {
			case 3:
				return new Health(world, x, y);
			case 4:
				return new Sheild(world, x, y);
			default:
				return null;
		}
	}
	
	private static Item getAmmo(World world, int[] possibleAmmo, float x) {
		int itemID = possibleAmmo[ThreadLocalRandom.current().nextInt(100)];
		switch (itemID) {
			case 0:
				return new BulletItem(world, x, y, -1, "Pistol");
			case 1:
				return new BulletItem(world, x, y, -1, "Rifle");
			case 2:
				return new BulletItem(world, x, y, -1, "RocketLauncher");
			default:
				return null;
		}
	}
}
