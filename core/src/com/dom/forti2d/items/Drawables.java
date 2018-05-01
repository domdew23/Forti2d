package com.dom.forti2d.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Drawables {
	private static TextureRegionDrawable nonEquippedEmpty = new TextureRegionDrawable(new TextureRegion(new Texture("sprites/gunSlots/slot.png")));
	private static TextureRegionDrawable equippedEmpty = new TextureRegionDrawable(new TextureRegion(new Texture("sprites/gunSlots/slotEquipped.png")));

	private static TextureRegionDrawable nonEquippedGrey = new TextureRegionDrawable(new TextureRegion(new Texture("sprites/gunSlots/greySlot.png")));
	private static TextureRegionDrawable equippedGrey = new TextureRegionDrawable(new TextureRegion(new Texture("sprites/gunSlots/greySlotEquipped.png")));

	private static TextureRegionDrawable nonEquippedRed = new TextureRegionDrawable(new TextureRegion(new Texture("sprites/gunSlots/redSlot.png")));
	private static TextureRegionDrawable equippedRed = new TextureRegionDrawable(new TextureRegion(new Texture("sprites/gunSlots/redSlotEquipped.png")));

	private static TextureRegionDrawable nonEquippedBlue = new TextureRegionDrawable(new TextureRegion(new Texture("sprites/gunSlots/blueSlot.png")));
	private static TextureRegionDrawable equippedBlue = new TextureRegionDrawable(new TextureRegion(new Texture("sprites/gunSlots/blueSlotEquipped.png")));
	
	private static TextureRegionDrawable nonEquippedGold = new TextureRegionDrawable(new TextureRegion(new Texture("sprites/gunSlots/goldSlot.png")));
	private static TextureRegionDrawable equippedGold = new TextureRegionDrawable(new TextureRegion(new Texture("sprites/gunSlots/goldSlotEquipped.png")));

	private static TextureRegionDrawable greyPistol = new TextureRegionDrawable(new TextureRegion(new Texture("sprites/guns/greyPistol.png")));
	private static TextureRegionDrawable redPistol = new TextureRegionDrawable(new TextureRegion(new Texture("sprites/guns/redPistol.png")));
	private static TextureRegionDrawable bluePistol = new TextureRegionDrawable(new TextureRegion(new Texture("sprites/guns/bluePistol.png")));
	private static TextureRegionDrawable goldPistol = new TextureRegionDrawable(new TextureRegion(new Texture("sprites/guns/goldPistol.png")));

	private static TextureRegionDrawable greyRifle = new TextureRegionDrawable(new TextureRegion(new Texture("sprites/guns/greyRifle.png")));
	private static TextureRegionDrawable redRifle = new TextureRegionDrawable(new TextureRegion(new Texture("sprites/guns/redRifle.png")));
	private static TextureRegionDrawable blueRifle = new TextureRegionDrawable(new TextureRegion(new Texture("sprites/guns/blueRifle.png")));
	private static TextureRegionDrawable goldRifle = new TextureRegionDrawable(new TextureRegion(new Texture("sprites/guns/goldRifle.png")));
	
	private static TextureRegionDrawable greyRocketLauncher = new TextureRegionDrawable(new TextureRegion(new Texture("sprites/guns/greyRocketLauncher.png")));
	private static TextureRegionDrawable redRocketLauncher = new TextureRegionDrawable(new TextureRegion(new Texture("sprites/guns/redRocketLauncher.png")));
	private static TextureRegionDrawable blueRocketLauncher = new TextureRegionDrawable(new TextureRegion(new Texture("sprites/guns/blueRocketLauncher.png")));
	private static TextureRegionDrawable goldRocketLauncher = new TextureRegionDrawable(new TextureRegion(new Texture("sprites/guns/goldRocketLauncher.png")));
	
	public static TextureRegionDrawable getEmptySlot(boolean equipped) {
		if (equipped)
			return equippedEmpty;
		return nonEquippedEmpty;
	}
	
	public static TextureRegionDrawable getGreySlot(boolean equipped) {
		if (equipped)
			return equippedGrey;
		return nonEquippedGrey;
	}
	
	public static TextureRegionDrawable getRedSlot(boolean equipped) {
		if (equipped)
			return equippedRed;
		return nonEquippedRed;
	}
	
	public static TextureRegionDrawable getBlueSlot(boolean equipped) {
		if (equipped)
			return equippedBlue;
		return nonEquippedBlue;
	}
	
	public static TextureRegionDrawable getGoldSlot(boolean equipped) {
		if (equipped)
			return equippedGold;
		return nonEquippedGold;
	}
	
	public static TextureRegionDrawable getItem(int tier, String item) {
		switch (tier) {
			case 0:
				if (item.equals("Pistol"))
					return greyPistol;
				if (item.equals("Rifle"))
					return greyRifle;
				if (item.equals("RocketLauncher"))
					return greyRocketLauncher;
			case 1:
				if (item.equals("Pistol"))
					return redPistol;
				if (item.equals("Rifle"))
					return redRifle;
				if (item.equals("RocketLauncher"))
					return redRocketLauncher;
			case 2:
				if (item.equals("Pistol"))
					return bluePistol;
				if (item.equals("Rifle"))
					return blueRifle;
				if (item.equals("RocketLauncher"))
					return blueRocketLauncher;
			case 3:
				if (item.equals("Pistol"))
					return goldPistol;
				if (item.equals("Rifle"))
					return goldRifle;
				if (item.equals("RocketLauncher"))
					return goldRocketLauncher;
			default:
				return null;
		}
	}
	
}
