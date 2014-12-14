package com.blastedstudios.gdxworld.plugin.serializer.xml;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.blastedstudios.gdxworld.plugin.serializer.xstream.AbstractXStreamSerializer;
import com.blastedstudios.gdxworld.util.ISerializer;
import com.thoughtworks.xstream.XStream;

@PluginImplementation
public class XMLSerializer extends AbstractXStreamSerializer implements ISerializer {
	public XMLSerializer(){
		super(new XStream(), "xml", "XML");
	}
}
