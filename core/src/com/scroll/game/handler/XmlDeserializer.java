package com.scroll.game.handler;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class XmlDeserializer {

	public static Object deserialize(String key, Class<?> c) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(c);
		
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		File xml = Asset.instance().getXML(key);
		Object o = unmarshaller.unmarshal(xml);
		
		return o;
	}
	
}
