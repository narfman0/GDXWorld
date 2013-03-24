package com.blastedstudios.gdxworld.world;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.blastedstudios.gdxworld.util.FileUtil;

public class GDXWorld implements Serializable{
	private static final long serialVersionUID = 1L;
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
		save(selectedFile, false);
	}

	/**
	 * Serialize this into filesystem
	 * @param selectedFile location to save world
	 * @param split up levels into different files
	 */
	public void save(File selectedFile, boolean split) {
		if(selectedFile == null)
			Gdx.app.error("GDXWorld.save", "Cannot write to null file");
		else{
			selectedFile.getParentFile().mkdirs();
			if(split){
				selectedFile.delete();
				selectedFile.mkdirs();
				for(int i=0; i<levels.size(); i++){
					FileUtil.write(new File(selectedFile + File.separator + i), levels.get(i), false);
					Gdx.app.log("GDXWorld.save", "Successfully saved split " + selectedFile);
				}
			}else
				try{
					FileUtil.write(selectedFile, this, false);
					Gdx.app.log("GDXWorld.save", "Successfully saved non-split " + selectedFile);
				}catch(Exception e){
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * Deserialize GDXWorld, detects if split or not
	 */
	public static GDXWorld load(File selectedFile) {
		if(selectedFile == null)
			Gdx.app.error("GDXWorld.load", "Cannot read null file");
		else if(!selectedFile.canRead())
			Gdx.app.error("GDXWorld.load", "Cannot read file: " + selectedFile.getAbsolutePath());
		else
			try{
				GDXWorld world = (GDXWorld) FileUtil.read(selectedFile, false);
				Gdx.app.log("GDXWorld.load", "Successfully loaded non-split " + selectedFile);
				return world;
			}catch(Exception e){
				try{
					GDXWorld world = new GDXWorld();
					for(File file : selectedFile.listFiles())
						world.levels.add((GDXLevel)FileUtil.read(file, false));
					Gdx.app.log("GDXWorld.load", "Successfully loaded split " + selectedFile);
					return world;
				}catch(Exception e1){
					Gdx.app.log("GDXWorld.load", "Failed to load " + selectedFile);
					e.printStackTrace();
				}
			}
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
}
