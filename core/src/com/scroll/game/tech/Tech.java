package com.scroll.game.tech;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("unused") 
@XmlRootElement
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
		
		@XmlEnumValue("HARVESTING")
		HARVESTING(4);
	
		public int index;
	
		private TechType(int index) {
			this.index = index;
		}

		public int getRow() {
			return index;
		}
	}
	
	@XmlElement(name="required")
	private ArrayList<Tech> required;
	
	@XmlAttribute(name="type")
	private TechType type;
	
	private String name;
	private String image;
	private int time;
	private int cost;
	private int modifier;
	
	private int row = type.index;
	private int col = Integer.parseInt(image.replace("(^[0-9])+", ""));
	
	public TechType getType() { return type; }
	public String getImage() { return image; }
	public int getTime() { return time; }
	public int getCost() { return cost; }
	public int getModifier() { return modifier; }
	
	public int getRow() { return row; }
	public int getCol() { return col; }
}
