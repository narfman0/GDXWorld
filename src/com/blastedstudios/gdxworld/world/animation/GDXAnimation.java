package com.blastedstudios.gdxworld.world.animation;

import java.io.Serializable;
import java.util.LinkedList;

public class GDXAnimation implements Cloneable,Serializable{
	private static final long serialVersionUID = 1L;
	private static int count = 0;
	private LinkedList<AnimationStruct> animations = new LinkedList<>();
	private String name = "Animation-"+count++;
	private long totalTime;
	private boolean repeat;
	
	public LinkedList<AnimationStruct> getAnimations() {
		return animations;
	}

	public void setAnimations(LinkedList<AnimationStruct> animations) {
		this.animations = animations;
	}
	
	public long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	public boolean isRepeat() {
		return repeat;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override public Object clone(){
		GDXAnimation clone = new GDXAnimation();
		clone.setName(name);
		LinkedList<AnimationStruct> animationsClone = new LinkedList<>();
		for(AnimationStruct animation : animations)
			animationsClone.add((AnimationStruct) animation.clone());
		clone.setAnimations(animationsClone);
		clone.setRepeat(repeat);
		clone.setTotalTime(totalTime);
		return clone;
	}
	
	@Override public String toString(){
		return name;
	}
}
