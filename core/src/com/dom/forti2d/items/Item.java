package com.dom.forti2d.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.dom.forti2d.tools.BodyBuilder;
import com.dom.forti2d.tools.Constants;

public abstract class Item {
	
	public boolean isEquipped;
	
	protected TextureRegionDrawable slotDrawable;
	protected TextureRegionDrawable itemDrawable;
		
	protected int tier; /* 0 - grey, 1 - red, 2 - blue, 3 - gold */
	
	protected String item;
	
	public Body body;
	protected Sprite sprite;
	
	protected int count;
	
	public boolean isPickedUp, isDestroyed, wasDropped;
	
	private final byte cBits = Constants.ITEM_BITS;
	private final byte mBits = Constants.NON_INTERACTIVE_BITS;
	private float radius = 8;
			
	public Item(World world, float x, float y, int tier, String item) {
		this.tier = tier;
		this.item = item;
		this.isEquipped = false;
		this.isPickedUp = false;
		this.isDestroyed = false;
		this.wasDropped = false;
		this.count = 1;
		
		if (tier == -1)
			this.radius = 4;
		
		this.body = BodyBuilder.makeItemBody(world, x, y, radius, cBits, mBits, this);

		this.sprite = new Sprite(Drawables.getItem(tier, item).getRegion().getTexture());
		
		this.sprite.setBounds(x / Constants.SCALE, y / Constants.SCALE, radius * 2 / Constants.SCALE, radius * 2 / Constants.SCALE);
	}
	
	public Item() {
		this.tier = -1;
		this.isEquipped = false;
	}
	
	public String getItem() {
		return item;
	}
	
	public int getTier() {
		return tier;
	}
	
	public Item setWasDropped() {
		this.wasDropped = true;
		return this;
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
		if (tier != -1)
			setItemDrawable(item);
		return itemDrawable;
	}
	
	public Item setCount(int count) {
		this.count = count;
		return this;
	}
	
	public int getCount() {
		return count;
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
	
	public void draw(SpriteBatch batch) {
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
		sprite.draw(batch);
	}
	
	public void pickUp(Vector2 target) {
		//Vector2 force = AI.seek(body.getPosition(), target);
	}
	
	public abstract void setItemDrawable(String item);
}
