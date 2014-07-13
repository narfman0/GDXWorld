package com.blastedstudios.gdxworld.world;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.gdxworld.util.FileUtil;
import com.blastedstudios.gdxworld.util.ISerializer;
import com.blastedstudios.gdxworld.util.PluginUtil;
import com.blastedstudios.gdxworld.util.Properties;

/**
 * Represents the high level world, or campaign, that hold the levels
 * containing all the gameplay. 
 */
public class GDXWorld implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<GDXLevel> levels = new LinkedList<GDXLevel>();
	private HashMap<String, String> worldProperties = createWorldProperties();

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
	public void save(FileHandle selectedFile) {
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
	
	public static GDXWorld load(FileHandle selectedFile) {
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
	
	private HashMap<String, String> createWorldProperties(){
		worldProperties = new HashMap<String, String>();
		for(String property : Properties.get("world.properties", "background").split(","))
			worldProperties.put(property, "");
		return worldProperties;
	}
	
	public HashMap<String, String> getWorldProperties() {
		if(worldProperties == null)
			worldProperties = createWorldProperties();
		return worldProperties;
	}
}
