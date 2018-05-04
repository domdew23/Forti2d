package com.dom.forti2d.items;

public class Health extends Item {
	
	public Health() {
		super(1, "Health");
	}
	
	public void setItemDrawable(String item) {
		this.itemDrawable = Drawables.getHealth();
	}
}
