package com.blastedstudios.gdxworld.plugin.serializer.serializable;

import java.io.FileFilter;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.files.FileHandle;
import com.blastedstudios.gdxworld.util.ExtensionFileFilter;
import com.blastedstudios.gdxworld.util.ISerializer;
import com.blastedstudios.gdxworld.util.Log;

@PluginImplementation
public class JavaSerializable implements ISerializer{
	@Override public Object load(FileHandle selectedFile) throws Exception {
		if(selectedFile == null){
			Log.error("JavaSerializable.load", "Cannot read null file");
			throw new NullPointerException("selectedFile null");
		}
		Object object = read(selectedFile, false);
		Log.log("JavaSerializable.load", "Successfully loaded non-split " + selectedFile);
		return object;
	}

	@Override public void save(FileHandle selectedFile, Object object) throws Exception {
		if(selectedFile == null)
			Log.error("JavaSerializable.save", "Cannot write to null file");
		else{
			selectedFile.parent().mkdirs();
			write(selectedFile, object);
			Log.log("JavaSerializable.save", "Successfully saved non-split " + selectedFile);
		}
	}

	void write(FileHandle file, Object object){
		try{
			OutputStream fos = file.write(false);
			save(fos, object);
			fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	Object read(FileHandle file, boolean compress) throws Exception{
		InputStream fin = file.read();
		Object object = load(fin);
		fin.close();
		return object;
	}

	@Override public Object load(InputStream stream) throws Exception {
		ObjectInputStream oin = new ObjectInputStream(stream);
		Object object = oin.readObject();
		oin.close();
		return object;
	}

	@Override public void save(OutputStream stream, Object object) throws Exception {
		try{
			ObjectOutputStream oos = new ObjectOutputStream(stream);
			oos.writeObject(object);
			oos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override public FileFilter getFileFilter() {
		return new ExtensionFileFilter("ser", "Serializable");
	}
}
