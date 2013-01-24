package com.blastedstudios.gdxworld.world;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;

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
					GDXLevel level = levels.get(i);
					try{
						FileOutputStream fileOut = new FileOutputStream(selectedFile + File.separator + i);
						ObjectOutputStream out = new ObjectOutputStream(fileOut);
						out.writeObject(level);
						out.close();
						fileOut.close();
					}catch(Exception e){
						e.printStackTrace();
					}
					Gdx.app.log("GDXWorld.save", "Successfully saved split " + selectedFile);
				}
			}else
				try{
					FileOutputStream fileOut = new FileOutputStream(selectedFile);
					ObjectOutputStream out = new ObjectOutputStream(fileOut);
					out.writeObject(this);
					out.close();
					fileOut.close();
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
				FileInputStream fin = new FileInputStream(selectedFile);
				ObjectInputStream in = new ObjectInputStream(fin);
				GDXWorld world = (GDXWorld) in.readObject();
				in.close();
				fin.close();
				Gdx.app.log("GDXWorld.load", "Successfully loaded non-split " + selectedFile);
				return world;
			}catch(Exception e){
				try{
					GDXWorld world = new GDXWorld();
					for(File file : selectedFile.listFiles()){
						FileInputStream fin = new FileInputStream(file);
						ObjectInputStream in = new ObjectInputStream(fin);
						world.levels.add((GDXLevel)in.readObject());
						in.close();
						fin.close();
					}
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
