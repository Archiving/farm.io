package com.scroll.game.farm;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.scroll.game.handler.Asset;

/*
 * Automation class for farm tiles
 */

public class Pipe {

	private Patch[][] farm;
	
	private Form form;
	private float x,y,w,h;
	private AutoAction action;
	private int actionRow;
	private int actionCol;
	private float actionTime;
	private float actionTimeRequired;
	
	public enum AutoAction {
		TILLING(1.0f),
		WATERING(1.0f),
		HARVESTING(0.4f);
		public float timeRequired;
		AutoAction(float timeRequired) {
			this.timeRequired = timeRequired;
		}
	};
	
	public enum Form {
		TILL(new TextureRegion(Asset.instance().getTexture("till_pipe")), 50),
		WATER(new TextureRegion(Asset.instance().getTexture("water_pipe")), 50),
		HARVEST(new TextureRegion(Asset.instance().getTexture("harvest_pipe")), 50);
		
		public TextureRegion pipeImage;
		public int cost;
		
		private Form(TextureRegion pipeImage, int cost) {
			this.pipeImage = pipeImage;
			this.cost = cost;
		}
	}
	
}
