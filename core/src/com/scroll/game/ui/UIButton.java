package com.scroll.game.ui;

import java.awt.Rectangle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.scroll.game.handler.Asset;

public class UIButton {

	public int x;
	public int y;
	public int w;
	public int h;
	public TextureRegion image;
	public TextureRegion clickImage;
	public boolean clicked = false;
	
	public UIButton(int x, int y, int w, int h, TextureRegion image, TextureRegion clickImage) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.image = image;
		this.clickImage = clickImage;
	}
	
	public void draw(SpriteBatch sb) {
		sb.draw(image, x, y);
	}
	
	public int getx() { return x; }
	public int gety() { return y; }
	public int getw() { return w; }
	public int geth() { return h; }
	
	public Rectangle getBounds() { return new Rectangle(x,y,w,h); }
	
	public void click() {
		if(!clicked) Asset.instance().getSound("click").play(0.5f);
		image = clickImage;
		clicked = true;
	}
	
}
