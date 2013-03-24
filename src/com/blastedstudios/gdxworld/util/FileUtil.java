package com.blastedstudios.gdxworld.util;

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
}
