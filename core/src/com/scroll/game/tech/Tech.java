package com.scroll.game.tech;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlSeeAlso;

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
	
	@XmlElement(name="required")
	private Tech[] required;
	
	@XmlElement(name="name")
	private String name;
	
	@XmlElement(name="image")
	private String image;
	
	@XmlElement(name="time")
	private int time;
	
	@XmlElement(name="cost")
	private int cost;
	
	@XmlElement(name="modifier")
	private int modifier;

	private int row, col;
	
	private boolean unlocked = false;
	private boolean selected = false;
	private boolean progress = false;
	
	public TechType getType() { return type; }
	public Tech[] getRequiredTech() { return required; }
	public String getName() { return name; }
	public String getImage() { return image; }
	public int getTime() { return time; }
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
		ArrayList<Tech> unlockedTech = new ArrayList<>();
		for(int y = 0; y < techTree.length; y++) {
			for(int x = 0; x < techTree[y].length; x++) {
				if(techTree[y][x] != null) {
					if(techTree[y][x].isUnlocked()) unlockedTech.add(techTree[y][x]);
				}
			}
		}
		
		int count = 0;
		for(int i = 0; i < required.length; i++) {
			System.out.println(required[i].getType().name());
			if(unlockedTech.contains(required[i])) count++;
		}
		
		return count == required.length;
	}
	
	
	public boolean isUnlocked() { return unlocked; }
	public void setUnlocked(boolean b) { unlocked = b; }
	public boolean isSelected() { return selected; }
	public void setSelected(boolean b) { selected = b; }
	public boolean isInProgress() { return progress; }
	public void setProgress(boolean b) { progress = b; }
	
}
