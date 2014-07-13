package com.blastedstudios.gdxworld.plugin.serializer.serializable;

import java.io.FileFilter;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.blastedstudios.gdxworld.util.ExtensionFileFilter;
import com.blastedstudios.gdxworld.util.ISerializer;

@PluginImplementation
public class JavaSerializable implements ISerializer{
	@Override public Object load(FileHandle selectedFile) throws Exception {
		if(selectedFile == null){
			Gdx.app.error("JavaSerializable.load", "Cannot read null file");
			throw new NullPointerException("selectedFile null");
		}
		Object object = read(selectedFile, false);
		Gdx.app.log("JavaSerializable.load", "Successfully loaded non-split " + selectedFile);
		return object;
	}

	@Override public void save(FileHandle selectedFile, Object object) throws Exception {
		if(selectedFile == null)
			Gdx.app.error("JavaSerializable.save", "Cannot write to null file");
		else{
			selectedFile.parent().mkdirs();
			write(selectedFile, object);
			Gdx.app.log("JavaSerializable.save", "Successfully saved non-split " + selectedFile);
		}
	}

	static void write(FileHandle file, Object obj){
		try{
			OutputStream fos = file.write(false);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
			oos.close();
			fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	static Object read(FileHandle file, boolean compress) throws Exception{
		InputStream fin = file.read();
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
