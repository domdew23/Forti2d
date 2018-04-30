package com.dom.forti2d.guns;

public abstract class Gun {
	
	protected int ammoCount;
	
	public Gun() {
		this.ammoCount = 0;
	}
	
	public int getAmmo() {
		return ammoCount;
	}
}
