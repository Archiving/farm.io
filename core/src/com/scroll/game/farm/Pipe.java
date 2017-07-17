package com.scroll.game.farm;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;
import com.scroll.game.Var;
import com.scroll.game.handler.Asset;

/*
 * Automation class for farm tiles
 */

public class Pipe {
	
	//patch that the pipe is in
	private Patch patch;
	
	private Form form;
	private TextureRegion image, pixel;
	private float x,y,w,h;
	private float internalTime;
	
	public enum Form {
		TILL(new TextureRegion(Asset.instance().getTexture("till_pipe")), 50),
		WATER(new TextureRegion(Asset.instance().getTexture("water_pipe")), 50);
		
		public TextureRegion pipeImage;
		public int cost;
		
		private Form(TextureRegion pipeImage, int cost) {
			this.pipeImage = pipeImage;
			this.cost = cost;
		}
	}
	
	public Pipe(Form form, float x, float y, float w, float h, Patch patch) {
		this.form = form;
		this.patch = patch;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		image = form.pipeImage;
		pixel = new TextureRegion(Asset.instance().getTexture("pixel"));
	}
	
	public float getx() { return x; }
	public float gety() { return y; }
	public float getw() { return w; }
	public float geth() { return h; }
	
	public Form getForm() { return form; }
	
	//TODO automation goes here
	public void update(float dt) {
		long start = TimeUtils.millis();
		long elapsed = TimeUtils.timeSinceMillis(start);
		if(elapsed > 10000) {
			switch(form) {
			case TILL:
				if(patch.getState() == Patch.State.NORMAL && !patch.hasSeed()) {
					patch.till();
				}
				break;
			case WATER:
				if(patch.getState() == Patch.State.TILLED && !patch.hasSeed()) {
					patch.water();
				}
				break;
			}
			elapsed = 0;
		}
	}
	
	public void render(SpriteBatch sb) {
		sb.draw(image, x, y);
	}
}
