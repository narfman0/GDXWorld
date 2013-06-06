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
import com.blastedstudios.gdxworld.util.ISerializer;

@PluginImplementation
public class JavaSerializable implements ISerializer{
	@Override public Object load(File selectedFile) throws Exception {
		if(selectedFile == null){
			Gdx.app.error("JavaSerializable.load", "Cannot read null file");
			throw new NullPointerException("selectedFile null");
		}
		else if(!selectedFile.canRead()){
			Gdx.app.error("JavaSerializable.load", "Cannot read file: " + selectedFile.getAbsolutePath());
			throw new Exception("Cannot read file " + selectedFile.getAbsolutePath());
		}
		Object object = read(selectedFile, false);
		Gdx.app.log("JavaSerializable.load", "Successfully loaded non-split " + selectedFile);
		return object;
	}

	@Override public void save(File selectedFile, Object object) throws Exception {
		if(selectedFile == null)
			Gdx.app.error("JavaSerializable.save", "Cannot write to null file");
		else{
			selectedFile.getParentFile().mkdirs();
			write(selectedFile, object);
			Gdx.app.log("JavaSerializable.save", "Successfully saved non-split " + selectedFile);
		}
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
