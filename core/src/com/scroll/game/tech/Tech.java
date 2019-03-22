package com.scroll.game.tech;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.scroll.game.handler.Asset;

@SuppressWarnings("unused") 
@XmlAccessorType(XmlAccessType.FIELD)
public class Tech {	
	
	@XmlEnum(String.class)
	public enum TechType {
		
		@XmlEnumValue("TILLING")
		TILLING(1),
		
		@XmlEnumValue("WATERING")
		WATERING(2),
		
		@XmlEnumValue("SEEDING")
		SEEDING(3),
		
		@XmlEnumValue("HARVEST")
		HARVEST(4);
	
		public int index;
		
		private TechType(int index) {
			this.index = index;
		}

		public int getRow() {
			return index;
		}
	}

	@XmlAttribute(name="type")
	private TechType type;
	
	@XmlElement(name="require")
	private String[] required;
	
	@XmlElement(name="name")
	private String name;
	
	@XmlElement(name="image")
	private String image;
	
	@XmlElement(name="time")
	private double time;
	
	@XmlElement(name="cost")
	private int cost;
	
	@XmlElement(name="modifier")
	private int modifier;
	
	@XmlElement(name="colOffset")
	private int colOffset;
	
	private int row, col;
	
	private boolean unlocked = false;
	private boolean selected = false;
	private boolean progress = false;
	
	public TechType getType() { return type; }
	public String[] getRequiredTech() { return required; }
	public String getName() { return name; }
	public String getImage() { return image; }
	public double getTime() { return time; }
	public int getCost() { return cost; }
	public int getModifier() { return modifier; }
	
	public void setRow(int row) { this.row = row; }
	public void setCol(int col) { this.col = col; }
	
	public int getRow() { return row; }
	public int getCol() { return col; }
	public void setType(TechType type) {
		this.type = type;
	}
	
	public boolean canResearch(Tech[][] techTree) {
		if(required == null) return true;
		ArrayList<String> unlockedTech = new ArrayList<>();
		for(int y = 0; y < techTree.length; y++) {
			for(int x = 0; x < techTree[y].length; x++) {
				if(techTree[y][x] != null) {
					if(techTree[y][x].isUnlocked()) {
						unlockedTech.add(techTree[y][x].getName());
					}
				}
			}
		}
		
		int count = 0;
		for(int i = 0; i < required.length; i++) {
			if(unlockedTech.contains(required[i])) count++;
		}
		
		return count == required.length;
	}
	
	public void draw(SpriteBatch sb) {
	}
	
	
	public boolean isUnlocked() { return unlocked; }
	public void setUnlocked(boolean b) { unlocked = b; }
	public boolean isSelected() { return selected; }
	public void setSelected(boolean b) { selected = b; }
	public boolean isInProgress() { return progress; }
	public void setProgress(boolean b) { progress = b; }
	public int getColOffset() { return colOffset; }
	
}
