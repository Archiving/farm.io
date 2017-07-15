package com.scroll.game.state.entities;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.scroll.game.handler.Asset;
import com.scroll.game.state.tile.MapObject;
import com.scroll.game.state.tile.TileMap;

public class Truck extends MapObject {
	
	private BitmapFont font;
	
	private boolean showing;
	private float time = 2;
	private float timer;
	
	public Truck(TileMap tileMap) {
		super(tileMap);
		TextureRegion[] reg = new TextureRegion[1];
		reg[0] = new TextureRegion(Asset.instance().getTexture("truck"));
		setAnimation(reg, 0, 1);
		cwidth = width;
		cheight = height;
		font = Asset.instance().getFont("med_font");
	}
	
	public void setShowing() {
		showing = true;	
	}
	
	@Override
	public void update(float dt) {
		if(showing) {
			timer += dt;
			if(timer >= time) {
				timer = 0;
				showing = false;
			}
		}
	}
	
	@Override
	public void render(SpriteBatch sb) {
		super.render(sb);
		
		if(showing) {
			font.draw(sb, "$", x - 20, y + 30 + 30 * (timer / time));
		}
	}
	
}