package com.blastedstudios.gdxworld.util;

import java.io.File;
import java.io.FileFilter;

public class ExtensionFileFilter implements FileFilter{
	private String extension, name;

	public ExtensionFileFilter(String extension, String name){
		this.extension = extension;
		this.name = name;
	}

	@Override public boolean accept(File f) {
		if(f.isDirectory())
			return true;
		if(FileUtil.getExtension(f) == null)
			return false;
		return FileUtil.getExtension(f).equalsIgnoreCase(extension);
	}

	public String getDescription() {
		return name + " (*." + extension + ")";
	}
	
	public String getExtension(){
		return extension;
	}
}