package com.blastedstudios.gdxworld.world;

import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;

public class GDXParticle implements Cloneable,Serializable{
	private static final long serialVersionUID = 1L;
	private static int count = 0;
	private String name = "Particle-" + count++, effectFile = "data/particles/particle.p",
			imagesDir = "data/particles", emitterName = "", attachedBody = "";
	/**
	 * Duration of particles, -1 means it is continuous
	 */
	private int duration = -1;
	private Vector2 position = new Vector2();
	
	public GDXParticle(){}
	
	public GDXParticle(String name, String effectFile, String imagesDir,
			int duration, Vector2 position, String emitterName, String attachedBody) {
		this.name = name;
		this.effectFile = effectFile;
		this.imagesDir = imagesDir;
		this.emitterName = emitterName;
		this.duration = duration;
		this.position = position;
		this.attachedBody = attachedBody;
	}
	
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
	
	public void setPosition(float x, float y){
		this.position.set(x, y);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmitterName() {
		return emitterName;
	}

	public void setEmitterName(String emitterName) {
		this.emitterName = emitterName;
	}

	public String getAttachedBody() {
		return attachedBody;
	}

	public void setAttachedBody(String attachedBody) {
		this.attachedBody = attachedBody;
	}
	
	@Override public String toString(){
		return "[GDXParticle name:" + name + " position:" + position + " effectFile:" + 
				effectFile + " imagesDir:" + imagesDir + " duration:" + duration + 
				" effectName:" + emitterName + " attachedBody:" + attachedBody + "]";
	}
	
	@Override public Object clone(){
		GDXParticle clone = new GDXParticle();
		clone.setPosition(position.cpy());
		clone.setName(name);
		clone.setDuration(duration);
		clone.setEffectFile(effectFile);
		clone.setImagesDir(imagesDir);
		clone.setEmitterName(emitterName);
		clone.setAttachedBody(attachedBody);
		return clone;
	}
	
	public ParticleEffect createEffect(){
		ParticleEffect effect = new ParticleEffect();
		effect.load(Gdx.files.internal(effectFile), Gdx.files.internal(imagesDir));
		effect.setPosition(position.x, position.y);
		if(duration != -1)
			effect.setDuration(duration);
		return effect;
	}
}
