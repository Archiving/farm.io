package com.scroll.game.farm;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.scroll.game.handler.Asset;
import com.scroll.game.state.entities.Player;

/*
 * Automation class for farm tiles
 */

public class Pipe {
	
	
	private Form form;
	private TextureRegion image, pixel;
	private float x,y,w,h;
	private float internalTime;
	private Player player;
	
	private int actionRow, actionCol;
	
	public enum Form {
		TILL(new TextureRegion(Asset.instance().getTexture("till_pipe")), 50, 10),
		WATER(new TextureRegion(Asset.instance().getTexture("water_pipe")), 50, 10);
		
		public TextureRegion pipeImage;
		public int cost;
		public int autoTime;
		
		private Form(TextureRegion pipeImage, int cost, int autoTime) {
			this.pipeImage = pipeImage;
			this.cost = cost;
			this.autoTime = autoTime;
		}
	}
	
	public Pipe(Form form, Player player, int actionRow, int actionCol, float x, float y, float w, float h) {
		this.form = form;
		this.player = player;
		this.actionRow = actionRow;
		this.actionCol = actionCol;
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
		internalTime += 2880 * dt;
		System.out.println("internalTime:" + internalTime);
		if(internalTime > form.autoTime*1000) {
			switch(form) {
			case TILL:
				player.getFarm()[actionRow][actionCol].till();
				break;
			case WATER:
				player.getFarm()[actionRow][actionCol].water();
				break;
			}
			internalTime = 0;
		}
	}
	
	public void render(SpriteBatch sb) {
		sb.draw(image, x, y);
	}
}
