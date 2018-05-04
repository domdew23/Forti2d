package com.dom.forti2d.items;

public class BlankItem extends Item {
	
	public BlankItem() {
		super();
	}

	public void setItemDrawable(String item) {
		this.itemDrawable = Drawables.getEmptySlot(isEquipped);
	}
}
