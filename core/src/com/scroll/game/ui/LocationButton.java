package com.scroll.game.ui;

import java.awt.Rectangle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.scroll.game.farm.Seed;
import com.scroll.game.handler.Asset;

public class LocationButton {

	public int x, y, w, h;
	public TextureRegion locationTag;
	public Seed.Region region;
	public TextureRegion[] affectedCrops;
	public String locationName;
	public BitmapFont font;
	public TextureRegion lock;
	
	public boolean isSelected = false;
	public boolean isUnlocked = false;
	public boolean hovered = false;
	
	public LocationButton(int x, int y, int w, int h, Seed.Region region, String locationName, boolean isUnlocked) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.locationName = locationName;
		this.region = region;
		this.isUnlocked = isUnlocked;
		this.region.isUnlocked = isUnlocked;
		
		this.affectedCrops = new TextureRegion[region.affectedCrops.length];
		for(int i = 0; i < affectedCrops.length; i++) {
			this.affectedCrops[i] = region.affectedCrops[i].cropItem; 
		}
		
		locationTag = new TextureRegion(Asset.instance().getTexture("location_tag"));
		font = Asset.instance().getFont("small_font");
		lock = new TextureRegion(Asset.instance().getTexture("lock"))	;
	}
	
	public void draw(SpriteBatch sb) {
		if(isUnlocked()) {
			sb.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			for(int i = 0; i < affectedCrops.length; i++) {
				sb.draw(affectedCrops[i], x - 2 * w + i * w, y - h * 1.25f);
				sb.draw(locationTag, x, y);
				if(isSelected) font.setColor(Color.GREEN);
				font.draw(sb, locationName, x - (locationName.length() * font.getSpaceWidth())/2, (y - h * 1.75f));
				font.setColor(Color.WHITE);
			}
		}
		else if(!isUnlocked()){
			sb.setColor(Color.GRAY);
			sb.draw(lock, x, y - h * 1.25f);
			sb.draw(locationTag, x, y);
			font.draw(sb, locationName, x - (locationName.length() * font.getSpaceWidth())/2, (y - h * 1.75f));
		}

	}
	
	public int getx() { return x; }
	public int gety() { return y; }
	public int getw() { return w; }
	public int geth() { return h; }
	
	public Rectangle getBounds() { return new Rectangle(x,y,w,h); }
	
	public void setUnlocked() { isUnlocked = true; }
	public void setSelected(boolean b) { isSelected = b; }
	public void setHovered() { hovered = true; }
	
	public boolean isUnlocked() { return isUnlocked; }
	public boolean hasHovered() { return hovered; }
	public boolean isSelected() { return isSelected; }
}
