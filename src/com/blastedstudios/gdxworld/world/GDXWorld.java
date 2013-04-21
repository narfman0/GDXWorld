package com.blastedstudios.gdxworld.world;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.filechooser.FileFilter;

import net.xeoh.plugins.base.Plugin;

import com.badlogic.gdx.Gdx;
import com.blastedstudios.gdxworld.util.PluginUtil;

public class GDXWorld implements Serializable{
	private static final long serialVersionUID = 1L;
	private static Collection<IWorldSerializer> serializers = PluginUtil.getPlugins(IWorldSerializer.class);
	private List<GDXLevel> levels;

	public GDXWorld(){
		levels = new ArrayList<GDXLevel>();
	}

	public void add(GDXLevel polygon){
		levels.add(polygon);
	}

	public void remove(GDXLevel polygon){
		levels.remove(polygon);
	}

	public void clear(){
		levels.clear();
	}

	public boolean contains(GDXLevel gdxLevel) {
		return levels.contains(gdxLevel);
	}

	public List<GDXLevel> getLevels(){
		return levels;
	}

	/**
	 * Serialize this into filesystem
	 * @param selectedFile location to save world
	 */
	public void save(File selectedFile) {
		try{
			getSerializer(selectedFile).save(selectedFile, this);
		}catch(Exception e){
			Gdx.app.error("GDXWorld.save", "Detected serializer failed: " + e.getMessage());
			try{
				PluginUtil.getPlugins(IWorldSerializer.class).iterator().next().save(selectedFile, this);
			}catch(Exception e1){
				Gdx.app.error("GDXWorld.save", "Default serializer failed: " + e.getMessage());
				e1.printStackTrace();
			}
		}
	}
	
	public static GDXWorld load(File selectedFile) {
		try{
			return getSerializer(selectedFile).load(selectedFile);
		}catch(Exception e){
			Gdx.app.error("GDXWorld.load", "Serializer error: " + e.getMessage());
			for(IWorldSerializer serializer : serializers)
				try{
					return serializer.load(selectedFile);
				}catch(Exception e1){}
		}
		return null;
	}
	
	private static IWorldSerializer getSerializer(File selectedFile){
		for(IWorldSerializer serializer : serializers)
			if(serializer.getFileFilter().accept(selectedFile))
				return serializer;
		return null;
	}

	/**
	 * @return closest GDXLevel to the given coordinates
	 */
	public GDXLevel getClosestLevel(float x, float y) {
		GDXLevel closest = null;
		float closestDistance = Float.MAX_VALUE;
		for(GDXLevel level : levels){
			float distance = level.getCoordinates().dst2(x, y);
			if(closest == null || closestDistance > distance){
				closest = level;
				closestDistance = distance;
			}
		}
		return closest;
	}

	@Override public String toString(){
		return "[GDXWorld]";
	}
	
	public static Collection<IWorldSerializer> getSerializers(){
		return serializers;
	}
	
	public interface IWorldSerializer extends Plugin{
		public GDXWorld load(File selectedFile) throws Exception;
		public void save(File selectedFile, GDXWorld world) throws Exception;
		public FileFilter getFileFilter();
	}
}
