package com.blastedstudios.gdxworld.world;

import java.io.Serializable;

public class GDXSound implements Cloneable,Serializable{
	private static final long serialVersionUID = 1L;
	private static int count = 0;
	private String name = "Sound-"+count++, filename = "";
	private float volume = 1f, pan, pitch;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getVolume() {
		return volume;
	}

	public void setVolume(float volume) {
		this.volume = volume;
	}

	public float getPan() {
		return pan;
	}

	public void setPan(float pan) {
		this.pan = pan;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	@Override public Object clone(){
		GDXSound clone = new GDXSound();
		clone.setName(name);
		clone.setFilename(filename);
		clone.setPan(pan);
		clone.setPitch(pitch);
		clone.setVolume(volume);
		return clone;
	}
	
	@Override public String toString(){
		return "[GDXSound name:" + getName() + "]";
	}
}
