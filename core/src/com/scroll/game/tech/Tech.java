package com.scroll.game.tech;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

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
	private ArrayList<Tech> required;
	
	private String name;
	
	@XmlElement(name="image")
	private String image;
	private int time;
	private int cost;
	private int modifier;

	private int row, col;
	
	private boolean unlocked = false;
	private boolean selected = false;
	private boolean progress = false;
	
	public TechType getType() { return type; }
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
	
	public boolean isUnlocked() { return unlocked; }
	public void setUnlocked(boolean b) { unlocked = b; }
	public boolean isSelected() { return selected; }
	public void setSelected(boolean b) { selected = b; }
	public boolean isInProgress() { return progress; }
	public void setProgress(boolean b) { progress = b; }
	
}
