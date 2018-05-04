package com.dom.forti2d.items;

public class Sheild extends Item {
	
	public Sheild() {
		super(2, "Sheild");
	}
	
	public void setItemDrawable(String item) {
		this.itemDrawable = Drawables.getSheild();
	}
}
