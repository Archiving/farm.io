package com.scroll.game.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.scroll.game.handler.Asset;

public class GuideBlock {

	public TextureRegion image;
	public String caption;
	public int x, y, w, h;
	public BitmapFont font;
	
	public GuideBlock(TextureRegion image, String caption, int x, int y, int w, int h) {
		this.image = image;
		this.caption = caption;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		font = Asset.instance().getFont("small_font");
	}
	
	public void draw(SpriteBatch sb) {
		sb.draw(image, x, y);
		font.draw(sb, caption, x+1.2f*w, y+h/2);
	}
	
}
