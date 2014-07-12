package com.blastedstudios.gdxworld.plugin.serializer.xml;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.Gdx;
import com.blastedstudios.gdxworld.util.ExtensionFileFilter;
import com.blastedstudios.gdxworld.util.FileUtil;
import com.blastedstudios.gdxworld.util.ISerializer;
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
	
	@Override public Object load(File selectedFile) throws Exception {
		if(selectedFile == null){
			Gdx.app.error(this.getClass().getSimpleName() + ".load", "Cannot read null file");
			throw new NullPointerException("selectedFile null");
		}
		else if(!selectedFile.canRead()){
			Gdx.app.error(this.getClass().getSimpleName() + ".load", "Cannot read file: " + selectedFile.getAbsolutePath());
			throw new Exception("Cannot read file " + selectedFile.getAbsolutePath());
		}
		Object object = xStream.fromXML(selectedFile);
		Gdx.app.log(this.getClass().getSimpleName() + ".load", "Successfully loaded " + selectedFile);
		return object;
	}

	@Override public void save(File selectedFile, Object object) throws Exception {
		if(selectedFile == null){
			Gdx.app.error(this.getClass().getSimpleName() + ".save", "Cannot write to null file");
			return;
		}
		selectedFile.getParentFile().mkdirs();
		if(FileUtil.getExtension(selectedFile) != null && !FileUtil.getExtension(selectedFile).equals(extension))
			selectedFile = new File(selectedFile.getAbsolutePath() + "." + extension);
		xStream.toXML(object, new FileOutputStream(selectedFile));
		Gdx.app.log(this.getClass().getSimpleName() + ".save", "Successfully saved " + selectedFile);
	}

	@Override public FileFilter getFileFilter() {
		return new ExtensionFileFilter(extension, "XML");
	}

}
