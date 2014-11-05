package com.blastedstudios.gdxworld.plugin.serializer.xml;

import java.io.FileFilter;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.blastedstudios.gdxworld.util.ExtensionFileFilter;
import com.blastedstudios.gdxworld.util.ISerializer;
import com.blastedstudios.gdxworld.util.Log;
import com.thoughtworks.xstream.XStream;

@PluginImplementation
public class XMLSerializer implements ISerializer {
	protected final XStream xStream;
	protected final String extension;
	
	public XMLSerializer(){
		this(new XStream(), "xml");
	}
	
	public XMLSerializer(XStream xStream, String extension){
		this.xStream = xStream;
		this.extension = extension;
		xStream.alias("Vector2", com.badlogic.gdx.math.Vector2.class);
		xStream.aliasPackage("world", "com.blastedstudios.gdxworld.world");
		xStream.aliasPackage("plugin", "com.blastedstudios.gdxworld.plugin");
	}
	
	@Override public Object load(FileHandle selectedFile) throws Exception {
		if(selectedFile == null){
			Log.error(this.getClass().getSimpleName() + ".load", "Cannot read null file");
			throw new NullPointerException("selectedFile null");
		}
		Object object = xStream.fromXML(selectedFile.read());
		Log.log(this.getClass().getSimpleName() + ".load", "Successfully loaded " + selectedFile);
		return object;
	}

	@Override public void save(FileHandle selectedFile, Object object) throws Exception {
		if(selectedFile == null){
			Log.error(this.getClass().getSimpleName() + ".save", "Cannot write to null file");
			return;
		}
		xStream.toXML(object, Gdx.files.absolute(selectedFile.file().getAbsolutePath()).write(false));
		Log.log(this.getClass().getSimpleName() + ".save", "Successfully saved " + selectedFile);
	}

	@Override public FileFilter getFileFilter() {
		return new ExtensionFileFilter(extension, "XML");
	}

}
