package com.scroll.game.handler;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class BoundCamera extends OrthographicCamera {

	public float xmin,ymin,xmax,ymax;
	
	public void setBounds(float xmin, float ymin, float xmax, float ymax) {
		this.xmin = xmin;
		this.xmax = xmax;
		this.ymin = ymin;
		this.ymax = ymax;
	}
	
	@Override
	public void update() {
		checkBounds();
		super.update();
	}
	
	private void checkBounds() {
		if(position.x < xmin + viewportWidth / 2) {
			position.x = xmin + viewportWidth / 2;
		}
		if(position.x > xmax - viewportWidth / 2) {
			position.x = xmax - viewportWidth / 2;
		}
		if(position.y < ymin + viewportHeight / 2) {
			position.y = ymin + viewportHeight / 2;
		}
		if(position.y > ymax - viewportHeight / 2) {
			position.y = ymax - viewportHeight / 2;
		}
	}
	
}
