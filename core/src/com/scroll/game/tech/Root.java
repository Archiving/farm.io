package com.scroll.game.tech;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Root {

	@XmlElement(name="tech")
	private Tech[] tech;
	
	@XmlElement(name = "col")
	private String col;
	
	@XmlElement(name = "row")
	private String row;
	
	public Tech[] getTech() { return tech; }

	public int getRow() { return Integer.parseInt(row); }
	public int getCol() { return Integer.parseInt(col); }
}
