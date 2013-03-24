package com.blastedstudios.gdxworld.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import com.badlogic.gdx.files.FileHandle;

public class FileUtil {
	/**
	 * Recursively find file in directory
	 */
	public static FileHandle find(FileHandle path, String name){
		if(path.isDirectory())
			for(FileHandle childHandle : path.list()){
				FileHandle found = find(childHandle, name);
				if(found != null)
					return found;
			}
		else if(path.name().equals(name))
			return path;
		return null;
	}

	public static void write(File file, Object obj, boolean compress){
		try{
			FileOutputStream fos = new FileOutputStream(file);
			OutputStream os = /*compress ? new ZipOutputStream(fos) :*/ fos;
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(obj);
			oos.close();
			fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static Object read(File file, boolean compress){
		Object object = null;
		try{
			FileInputStream fin = new FileInputStream(file);
			InputStream in = /*compress ? ZipInputStream(fin) :*/ fin;
			ObjectInputStream oin = new ObjectInputStream(in);
			object = oin.readObject();
			oin.close();
			fin.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return object;
	}
}
