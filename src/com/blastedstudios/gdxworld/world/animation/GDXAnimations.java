package com.blastedstudios.gdxworld.world.animation;

import java.io.Serializable;
import java.util.LinkedList;

public class GDXAnimations implements Cloneable,Serializable{
	private static final long serialVersionUID = 1L;
	private LinkedList<GDXAnimation> animations = new LinkedList<GDXAnimation>();
	private String name = "", defaultAnimation = "";

	public LinkedList<GDXAnimation> getAnimations() {
		return animations;
	}

	public void setAnimations(LinkedList<GDXAnimation> animations) {
		this.animations = animations;
	}
	
	public GDXAnimation getAnimation(String name){
		for(GDXAnimation animation : animations)
			if(animation.getName().equals(name))
				return animation;
		return null;
	}

	public String getDefaultAnimation() {
		return defaultAnimation;
	}

	public void setDefaultAnimation(String defaultAnimation) {
		this.defaultAnimation = defaultAnimation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override public Object clone(){
		GDXAnimations clone = new GDXAnimations();
		clone.setName(name);
		LinkedList<GDXAnimation> animationsClone = new LinkedList<>();
		for(GDXAnimation animation : animations)
			animationsClone.add((GDXAnimation) animation.clone());
		clone.setAnimations(animationsClone);
		clone.setDefaultAnimation(defaultAnimation);
		return clone;
	}
}
