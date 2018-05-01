package com.dom.forti2d.items;

public class Pistol extends Gun {

	public Pistol(int tier, String item) {
		super(tier, item);
		setItemDrawable();
		setSlotDrawable();
	}
	
	public void setItemDrawable() {
		super.setItemDrawable(item);
	}
}
