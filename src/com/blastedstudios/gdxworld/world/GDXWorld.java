package com.blastedstudios.gdxworld.world;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.gdxworld.util.FileUtil;
import com.blastedstudios.gdxworld.util.ISerializer;
import com.blastedstudios.gdxworld.util.PluginUtil;

public class GDXWorld implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<GDXLevel> levels;

	public GDXWorld(){
		levels = new ArrayList<GDXLevel>();
	}

	public void add(GDXLevel level){
		levels.add(level);
	}

	public void remove(GDXLevel level){
		levels.remove(level);
	}

	public void clear(){
		levels.clear();
	}

	public boolean contains(GDXLevel level) {
		return levels.contains(level);
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
			FileUtil.getSerializer(selectedFile).save(selectedFile, this);
		}catch(Exception e){
			Gdx.app.error("GDXWorld.save", "Detected serializer failed: " + e.getMessage());
			try{
				PluginUtil.getPlugins(ISerializer.class).iterator().next().save(selectedFile, this);
			}catch(Exception e1){
				Gdx.app.error("GDXWorld.save", "Default serializer failed: " + e.getMessage());
				e1.printStackTrace();
			}
		}
	}
	
	public static GDXWorld load(File selectedFile) {
		try{
			return (GDXWorld) FileUtil.getSerializer(selectedFile).load(selectedFile);
		}catch(Exception e){
			Gdx.app.error("GDXWorld.load", "Serializer error: " + e.getMessage());
			for(ISerializer serializer : getSerializers())
				try{
					return (GDXWorld) serializer.load(selectedFile);
				}catch(Exception e1){}
		}
		return null;
	}
	
	public GDXLevel getClosestLevel(Vector2 coords){
		return getClosestLevel(coords.x, coords.y);
	}

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
		return "[GDXWorld level#:" + levels.size() + "]";
	}
	
	public static Collection<ISerializer> getSerializers(){
		return PluginUtil.getPlugins(ISerializer.class);
	}
}
