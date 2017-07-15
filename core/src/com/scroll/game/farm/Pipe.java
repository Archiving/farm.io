package com.scroll.game.farm;

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
		TILLING,
		WATERING,
		HARVESTING
		
		
	}
	
}
