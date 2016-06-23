package com.blastedstudios.gdxworld.util;

import java.io.FileFilter;
import java.io.InputStream;
import java.io.OutputStream;

import net.xeoh.plugins.base.Plugin;

import com.badlogic.gdx.files.FileHandle;

public interface ISerializer extends Plugin{
	Object load(InputStream stream) throws Exception;
	Object load(FileHandle selectedFile) throws Exception;
	void save(FileHandle selectedFile, Object object) throws Exception; 
	void save(OutputStream stream, Object object) throws Exception;
	FileFilter getFileFilter();
}
