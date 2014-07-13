package com.blastedstudios.gdxworld.util;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class FileUtil {
	/**
	 * Recursively find file in directory
	 */
	public static FileHandle find(FileHandle path, String name){
		FileHandle full = Gdx.files.internal(name); 
		if(full.exists())
			return full;
		if(path.name().equals(name))
			return path;
		else if(path.isDirectory())
			for(FileHandle childHandle : path.list()){
				FileHandle found = find(childHandle, name);
				if(found != null)
					return found;
			}
		return null;
	}
	
	public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');
        if (i > 0 &&  i < s.length() - 1)
            ext = s.substring(i+1).toLowerCase();
        return ext;
    }
	
	public static ISerializer getSerializer(File selectedFile){
		for(ISerializer serializer : PluginUtil.getPlugins(ISerializer.class))
			if(serializer.getFileFilter().accept(selectedFile))
				return serializer;
		return null;
	}
}
