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
	/**
	 * Contains list of level names this level depends on before being playable
	 */
	private List<String> prerequisites = new ArrayList<String>();

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

	public List<String> getPrerequisites() {
		return prerequisites;
	}

	public void setPrerequisites(List<String> prerequisites) {
		this.prerequisites = prerequisites;
	}

	public void setPrerequisitesString(String prerequisites) {
		this.prerequisites = new ArrayList<String>();
		for(String prereq : prerequisites.split(","))
			if(!prereq.equals(""))
				this.prerequisites.add(prereq);
	}

	public String getPrerequisitesString() {
		if(prerequisites.isEmpty())
			return "";
		String prereqs = "";
		for(String prereq : prerequisites)
			prereqs += prereq + ",";
		return prereqs.substring(0,prereqs.length()-1);
	}

	public GDXPolygon getClosestPolygon(float x, float y) {
		GDXPolygon closest = null;
		float closestDistance = Float.MAX_VALUE;
		for(GDXPolygon level : polygons){
			for(Vector2 vertex : level.getVertices()){
				float distance = vertex.dst2(x, y);
				if(closest == null || closestDistance > distance){
					closest = level;
					closestDistance = distance;
				}
			}
		}
		return closest;
	}

	@Override public String toString(){
		return "[GDXLevel name:" + name + " coords:" + coordinates + "]";
	}
}
