package com.dom.forti2d.items;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public abstract class Item {
	
	public boolean isEquipped;
	
	protected TextureRegionDrawable slotDrawable;
	protected TextureRegionDrawable itemDrawable;
		
	protected int tier; /* 0 - grey, 1 - red, 2 - blue, 3 - gold */
	
	protected String item;
	
	public Item(int tier, String item) {
		this.tier = tier;
		this.item = item;
		this.isEquipped = false;
	}
	
	public Item() {
		this.tier = -1;
		this.isEquipped = false;
	}
	
	public void setEquipped() {
		this.isEquipped = true;
	}
	
	public void setNotEquipped() {
		this.isEquipped = false;
	}
	
	public TextureRegionDrawable getSlotDrawable() {
		setSlotDrawable();
		return slotDrawable;
	}
	
	public TextureRegionDrawable getItemDrawable() {
		setItemDrawable(item);
		return itemDrawable;
	}
	
	public void setSlotDrawable() {	
		switch (tier) {
			case -1:
				this.slotDrawable = Drawables.getEmptySlot(isEquipped);
				break;
			case 0: 
				this.slotDrawable = Drawables.getGreySlot(isEquipped);
				break;
			case 1:
				this.slotDrawable = Drawables.getRedSlot(isEquipped);
				break; 
			case 2:
				this.slotDrawable = Drawables.getBlueSlot(isEquipped);
				break;
			case 3:
				this.slotDrawable = Drawables.getGoldSlot(isEquipped);
				break; 
		}
	}
	
	public abstract void setItemDrawable(String item);
}
