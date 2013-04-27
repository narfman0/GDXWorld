package com.blastedstudios.gdxworld.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import net.xeoh.plugins.base.Plugin;

public interface ISerializer extends Plugin{
	public Object load(File selectedFile) throws Exception;
	public void save(File selectedFile, Object object) throws Exception; 
	public FileFilter getFileFilter();
}
