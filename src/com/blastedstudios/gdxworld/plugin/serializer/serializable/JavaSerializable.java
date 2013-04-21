package com.blastedstudios.gdxworld.plugin.serializer.serializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.filechooser.FileFilter;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.Gdx;
import com.blastedstudios.gdxworld.util.ExtensionFileFilter;
import com.blastedstudios.gdxworld.util.Properties;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXWorld;
import com.blastedstudios.gdxworld.world.GDXWorld.IWorldSerializer;

@PluginImplementation
public class JavaSerializable implements IWorldSerializer{
	@Override public GDXWorld load(File selectedFile) throws Exception {
		if(selectedFile == null){
			Gdx.app.error("JavaSerializable.load", "Cannot read null file");
			throw new NullPointerException("selectedFile null");
		}
		else if(!selectedFile.canRead()){
			Gdx.app.error("JavaSerializable.load", "Cannot read file: " + selectedFile.getAbsolutePath());
			throw new Exception("Cannot read file " + selectedFile.getAbsolutePath());
		}
		try{
			GDXWorld world = (GDXWorld) read(selectedFile, false);
			Gdx.app.log("JavaSerializable.load", "Successfully loaded non-split " + selectedFile);
			return world;
		}catch(Exception e){
			GDXWorld world = new GDXWorld();
			for(File file : selectedFile.listFiles())
				world.getLevels().add((GDXLevel)read(file, false));
			Gdx.app.log("JavaSerializable.load", "Successfully loaded split " + selectedFile);
			return world;
		}
	}

	@Override public void save(File selectedFile, GDXWorld world) throws Exception {
		if(selectedFile == null)
			Gdx.app.error("JavaSerializable.save", "Cannot write to null file");
		else{
			selectedFile.getParentFile().mkdirs();
			if(isSplit()){
				selectedFile.delete();
				selectedFile.mkdirs();
				for(int i=0; i<world.getLevels().size(); i++){
					write(new File(selectedFile + File.separator + i), world.getLevels().get(i));
					Gdx.app.log("JavaSerializable.save", "Successfully saved split " + selectedFile);
				}
			}else{
				write(selectedFile, this);
				Gdx.app.log("JavaSerializable.save", "Successfully saved non-split " + selectedFile);
			}
		}
	}

	private boolean isSplit(){
		return Properties.getBool("world.serializer.javaserializable.split", false);
	}

	static void write(File file, Object obj){
		try{
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
			oos.close();
			fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	static Object read(File file, boolean compress) throws Exception{
		FileInputStream fin = new FileInputStream(file);
		ObjectInputStream oin = new ObjectInputStream(fin);
		Object object = oin.readObject();
		oin.close();
		fin.close();
		return object;
	}

	@Override public FileFilter getFileFilter() {
		return new ExtensionFileFilter("ser", "Serializable");
	}
}
