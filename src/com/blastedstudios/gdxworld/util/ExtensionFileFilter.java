package com.blastedstudios.gdxworld.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ExtensionFileFilter extends FileFilter{
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
		return FileUtil.getExtension(f).equals(extension);
	}

	@Override public String getDescription() {
		return "GDXWorld " + name + " (*." + extension + ")";
	}
}