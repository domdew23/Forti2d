package com.dom.forti2d.items;

public class Gun extends Item {
	
	protected int ammoCount;
	
	public Gun(int tier, String item) {
		super(tier, item);
		this.ammoCount = 0;
	}
	
	public int getAmmo() {
		return ammoCount;
	}
	
	public void setItemDrawable(String item) {
		this.itemDrawable = Drawables.getItem(tier, item);
	}
}
