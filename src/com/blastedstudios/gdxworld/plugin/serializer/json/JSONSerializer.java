package com.blastedstudios.gdxworld.plugin.serializer.json;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.blastedstudios.gdxworld.plugin.serializer.AbstractXStreamSerializer;
import com.blastedstudios.gdxworld.util.ISerializer;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

@PluginImplementation
public class JSONSerializer extends AbstractXStreamSerializer implements ISerializer {
	public JSONSerializer(){
		super(new XStream(new JettisonMappedXmlDriver()), "json", "JSON");
	}
}
