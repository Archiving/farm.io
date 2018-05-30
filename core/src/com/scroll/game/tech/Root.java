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
	
	public Tech[] getTech() { return tech; }
	
}
