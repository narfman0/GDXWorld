package com.blastedstudios.gdxworld.world;

import java.io.Serializable;

import com.badlogic.gdx.math.Vector2;

public class GDXNPC implements  Cloneable,Serializable{
	private static final long serialVersionUID = 1L;
	private static int count = 0;
	private String name = "NPC-"+count++, faction = "", resource = "";
	/**
	 * A named reference to a path in the GDXLevel the npc will traverse
	 */
	private String path = "";
	/**
	 * A named reference to an a.i. behavior this NPC will execute
	 */
	private String behavior = "";
	/**
	 * Starting NPC coordinates 
	 */
	private Vector2 coordinates = new Vector2();
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Vector2 getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Vector2 coordinates) {
		this.coordinates = coordinates;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getBehavior() {
		return behavior;
	}

	public void setBehavior(String behavior) {
		this.behavior = behavior;
	}

	public String getFaction() {
		return faction;
	}

	public void setFaction(String faction) {
		this.faction = faction;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
	
	@Override public String toString(){
		return "[GDXNPC: " + name + coordinates + " path:" + path + " behavior: " + behavior + "]";
	}
	
	@Override public Object clone(){
		GDXNPC npc = new GDXNPC();
		npc.setBehavior(behavior);
		npc.setCoordinates(coordinates.cpy());
		npc.setFaction(faction);
		npc.setName(name);
		npc.setPath(path);
		npc.setResource(resource);
		return npc;
	}
}
