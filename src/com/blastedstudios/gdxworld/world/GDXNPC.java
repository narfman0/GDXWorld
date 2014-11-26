package com.blastedstudios.gdxworld.world;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.gdxworld.util.Properties;

/**
 * Represents an NPC at a given location. Has configurable properties for
 * different use cases that can be given at startup. The properties are
 * defined in npc.properties.
 * 
 * Example properties that may be desirable: 
 * behavior - string representing which behavior tree this NPC will follow
 * faction - defines how this NPC is aligned with what factions, in what way
 * level - high level property defining how powerful a character tends to be
 * hp/attack/defense - properties defining other characteristics of the NPC
 */
public class GDXNPC implements  Cloneable,Serializable{
	private static final long serialVersionUID = 1L;
	private static int count = 0;
	private String name = "NPC-"+count++;
	private Vector2 coordinates = new Vector2();
	private Map<String, String> properties;
	
	public GDXNPC(){
		createProperties();
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

	public void setCoordinates(Vector2 coordinates) {
		this.coordinates = coordinates;
	}

	public void setProperties(Map<String,String> properties) {
		this.properties = properties;
	}
	
	public String get(String propertyKey){
		return getProperties().get(propertyKey);
	}

	public Map<String,String> getProperties() {
		if(properties == null)
			properties = createProperties();
		return properties;
	}
	
	@Override public String toString(){
		return "[GDXNPC name:" + name + " coordinates:" + coordinates + "]";
	}
	
	@Override public Object clone(){
		GDXNPC npc = new GDXNPC();
		npc.setCoordinates(coordinates.cpy());
		npc.setName(name);
		for(Entry<String, String> entry : getProperties().entrySet())
			npc.getProperties().put(entry.getKey(), entry.getValue());
		return npc;
	}
	
	private static Map<String, String> createProperties() {
		return Properties.parseProperties("npc.properties", "");
	}
}
