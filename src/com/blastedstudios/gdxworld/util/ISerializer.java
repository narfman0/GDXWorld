package com.blastedstudios.gdxworld.util;

import java.io.FileFilter;

import com.badlogic.gdx.files.FileHandle;

import net.xeoh.plugins.base.Plugin;

public interface ISerializer extends Plugin{
	public Object load(FileHandle selectedFile) throws Exception;
	public void save(FileHandle selectedFile, Object object) throws Exception; 
	public FileFilter getFileFilter();
}
