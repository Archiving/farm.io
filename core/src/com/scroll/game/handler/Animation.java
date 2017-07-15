package com.scroll.game.handler;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {
	
	private TextureRegion[] images;
	private int index;
	private float interval;
	private float time;
	private int numFrames;
	
	private int timesPlayed;
	
	public void setImages(TextureRegion[] images) {
		this.images = images;
		timesPlayed = 0;
		index = 0;
	}
	
	public void setInterval(float interval) {
		this.interval = interval;
	}
	
	public void setNumFrames(int numFrames) {
		this.numFrames = numFrames;
	}
	
	public void update(float dt) {
		if(interval == 0) {
			return;
		}
		time += dt;
		while(time >= interval) {
			time -= interval;
			index++;
			if(index == numFrames) {
				index = 0;
				timesPlayed++;
			}
		}
	}
	
	public TextureRegion getImage() {
		return images[index];
	}
	
	public boolean hasPlayedOnce() {
		return timesPlayed >= 1;
	}
	
	public TextureRegion[] getAnimation() {
		return images;
	}
	
}