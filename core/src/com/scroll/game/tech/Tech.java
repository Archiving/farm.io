package com.scroll.game.tech;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tech {

	public int x,y;
	public Stack<Tech> requiredTech;
	public int researchTime;
	public int cost;
	public TextureRegion image;
	public boolean researched;
	public String techName;
	
	public Tech(String techName, int x, int y, Stack<Tech> requiredTech, TextureRegion image, int researchTime, int cost) {
		this.techName = techName;
		this.x = x;
		this.y = y;
		this.requiredTech = requiredTech;
		this.researchTime = researchTime;
		this.cost = cost;
		this.image = image;
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	public Stack<Tech> getRequiredTech() { return requiredTech; }
	public int getResearchTime() { return researchTime; }
	public int getCost() { return cost; }
	public boolean isResearched() { return researched; }
	
	public void setResearched() { researched = true; }
	
}
