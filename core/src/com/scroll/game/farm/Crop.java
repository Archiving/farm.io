package com.scroll.game.farm;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Crop {
	
	private TextureRegion image;
	
	private int value;
	private float x;
	private float y;
	private float w;
	private float h;
	
	public Crop(Seed seed) {
		image = seed.getType().cropTile;
		value = seed.getType().value;
		x = seed.getx();
		y = seed.gety();
		w = seed.getw();
		h = seed.geth();
	}
	
	public int getValue() {
		return value;
	}
	
	public void render(SpriteBatch sb) {
		sb.draw(image, x - w / 2, y - h / 2);
	}
	
}