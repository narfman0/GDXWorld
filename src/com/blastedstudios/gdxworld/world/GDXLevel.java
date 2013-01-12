package com.blastedstudios.gdxworld.world;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class GDXLevel implements Serializable{
	private static final long serialVersionUID = 1L;
	private final List<GDXPolygon> polygons = new ArrayList<GDXPolygon>();
	private String name = "";
	private Vector2 coordinates = new Vector2();
	private List<Integer> prerequisites = new ArrayList<Integer>();

	public void add(GDXPolygon polygon){
		polygons.add(polygon);
	}

	public void remove(GDXPolygon polygon){
		polygons.remove(polygon);
	}

	public void clear(){
		polygons.clear();
	}

	public List<GDXPolygon> getPolygons() {
		return polygons;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector2 getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Vector2 coords) {
		this.coordinates = coords;
	}

	public List<Integer> getPrerequisites() {
		return prerequisites;
	}

	public void setPrerequisites(List<Integer> prerequisites) {
		this.prerequisites = prerequisites;
	}

	public void setPrerequisitesString(String prerequisites) {
		this.prerequisites = new ArrayList<Integer>();
		for(String prereq : prerequisites.split(","))
			if(!prereq.equals(""))
				this.prerequisites.add(Integer.parseInt(prereq));
	}
	
	public String getPrerequisitesString() {
		if(prerequisites.isEmpty())
			return "";
		String prereqs = "";
		for(Integer prereq : prerequisites)
			prereqs += prereq + ",";
		return prereqs.substring(0,prereqs.length()-1);
	}
	
	@Override public String toString(){
		return "Level name:" + name + " coords:" + coordinates;
	}
}
