package com.blastedstudios.gdxworld.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.blastedstudios.gdxworld.world.GDXWorld;

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
	
	public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');
        if (i > 0 &&  i < s.length() - 1)
            ext = s.substring(i+1).toLowerCase();
        return ext;
    }
	
	/**
	 * Prepare a file chooser with the different serializers
	 * @return Selected file
	 */
	public static File fileChooser(boolean acceptAllFileFilterUsed, boolean writeable){
		final JFileChooser fc = new JFileChooser();
		fc.setAcceptAllFileFilterUsed(acceptAllFileFilterUsed);
		List<ISerializer> serializers = new ArrayList<>(GDXWorld.getSerializers()); 
		for(ISerializer serializer : serializers)
			fc.addChoosableFileFilter(serializer.getFileFilter());
		fc.showSaveDialog(null);
		File file = fc.getSelectedFile();
		if(file != null){
			if(writeable &&( (file.exists() && !file.canWrite()) || 
				(!file.exists() && !file.getParentFile().canWrite())) ){
				Gdx.app.error("FileUtil.file", "Selected file " + file.getAbsolutePath() + " not writable");
			}else{
				if(fc.getFileFilter() instanceof ExtensionFileFilter){
					ExtensionFileFilter filter = (ExtensionFileFilter) fc.getFileFilter();
					if(!file.getName().endsWith(filter.getExtension()))
						return new File(file + "." + filter.getExtension());
				}
				return file;
			}
		}else
			Gdx.app.error("FileUtil.file", "Selected file null");
		return null;
	}
	
	public static ISerializer getSerializer(File selectedFile){
		for(ISerializer serializer : PluginUtil.getPlugins(ISerializer.class))
			if(serializer.getFileFilter().accept(selectedFile))
				return serializer;
		return null;
	}
}
