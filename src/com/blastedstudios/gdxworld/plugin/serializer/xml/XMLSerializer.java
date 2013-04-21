package com.blastedstudios.gdxworld.plugin.serializer.xml;

import java.io.File;
import java.io.FileOutputStream;

import javax.swing.filechooser.FileFilter;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.Gdx;
import com.blastedstudios.gdxworld.util.ExtensionFileFilter;
import com.blastedstudios.gdxworld.util.FileUtil;
import com.blastedstudios.gdxworld.world.GDXWorld;
import com.blastedstudios.gdxworld.world.GDXWorld.IWorldSerializer;
import com.thoughtworks.xstream.XStream;

@PluginImplementation
public class XMLSerializer implements IWorldSerializer {
	private XStream xStream = new XStream();
	
	public XMLSerializer(){
		xStream.alias("Vector2", com.badlogic.gdx.math.Vector2.class);
		xStream.aliasPackage("world", "com.blastedstudios.gdxworld.world");
	}
	
	@Override public GDXWorld load(File selectedFile) throws Exception {
		if(selectedFile == null){
			Gdx.app.error("XMLSerializer.load", "Cannot read null file");
			throw new NullPointerException("selectedFile null");
		}
		else if(!selectedFile.canRead()){
			Gdx.app.error("XMLSerializer.load", "Cannot read file: " + selectedFile.getAbsolutePath());
			throw new Exception("Cannot read file " + selectedFile.getAbsolutePath());
		}
		GDXWorld world = (GDXWorld) xStream.fromXML(selectedFile);
		Gdx.app.log("XMLSerializer.load", "Successfully loaded " + selectedFile);
		return world;
	}

	@Override public void save(File selectedFile, GDXWorld world) throws Exception {
		if(selectedFile == null)
			Gdx.app.error("XMLSerializer.save", "Cannot write to null file");
		selectedFile.getParentFile().mkdirs();
		if(FileUtil.getExtension(selectedFile) != null && !FileUtil.getExtension(selectedFile).equals("xml"))
			selectedFile = new File(selectedFile.getAbsolutePath() + ".xml");
		xStream.toXML(world, new FileOutputStream(selectedFile));
		Gdx.app.log("XMLSerializer.save", "Successfully saved " + selectedFile);
	}

	@Override public FileFilter getFileFilter() {
		return new ExtensionFileFilter("xml", "XML");
	}

}
