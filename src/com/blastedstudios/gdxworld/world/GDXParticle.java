package com.blastedstudios.gdxworld.world;

import java.io.Serializable;

import com.badlogic.gdx.math.Vector2;

public class GDXParticle implements Cloneable,Serializable{
	private static final long serialVersionUID = 1L;
	private static int count = 0;
	private String name = "Particle-" + count++, effectFile = "data/particle.p", imagesDir = "data";
	private int duration = 1;
	private Vector2 position = new Vector2();
	
	public String getEffectFile() {
		return effectFile;
	}
	
	public void setEffectFile(String effectFile) {
		this.effectFile = effectFile;
	}

	public String getImagesDir() {
		return imagesDir;
	}

	public void setImagesDir(String imagesDir) {
		this.imagesDir = imagesDir;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override public String toString(){
		return "[GDXParticle name:" + name + " position:" + position + " effectFile:" + 
				effectFile + " imagesDir:" + imagesDir + " duration:" + duration + "]";
	}
	
	@Override public Object clone(){
		GDXParticle clone = new GDXParticle();
		clone.setPosition(position.cpy());
		clone.setName(getName());
		clone.setDuration(duration);
		clone.setEffectFile(effectFile);
		clone.setImagesDir(imagesDir);
		return clone;
	}
}
