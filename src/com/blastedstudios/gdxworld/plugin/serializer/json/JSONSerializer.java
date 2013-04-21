package com.blastedstudios.gdxworld.plugin.serializer.json;

import javax.swing.filechooser.FileFilter;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.blastedstudios.gdxworld.plugin.serializer.xml.XMLSerializer;
import com.blastedstudios.gdxworld.util.ExtensionFileFilter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

@PluginImplementation
public class JSONSerializer extends XMLSerializer {
	public JSONSerializer(){
		super(new XStream(new JettisonMappedXmlDriver()), "json");
	}

	@Override public FileFilter getFileFilter() {
		return new ExtensionFileFilter("json", "JSON");
	}
}
