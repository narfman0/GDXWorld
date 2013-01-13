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
	private String name;

	public GDXWorld(String name){
		this.name = name;
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

	public void save(File selectedFile) {
		if(selectedFile == null)
			Gdx.app.error("GDXWorld.save", "Cannot write to null file");
		else
			try{
				selectedFile.getParentFile().mkdirs();
				FileOutputStream fileOut = new FileOutputStream(selectedFile);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(this);
				out.close();
				fileOut.close();
				Gdx.app.log("GDXWorld.save", "Successfully saved " + selectedFile);
			}catch(Exception e){
				e.printStackTrace();
			}
	}

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
				Gdx.app.log("GDXWorld.load", "Successfully loaded " + selectedFile);
				return world;
			}catch(Exception e){
				e.printStackTrace();
			}
		return null;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override public String toString(){
		return "[GDXWorld: " + name + "]";
	}
}
