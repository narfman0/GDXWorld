package com.blastedstudios.gdxworld.world.animation;

import java.io.Serializable;

import com.blastedstudios.gdxworld.plugin.quest.manifestation.physics.PhysicsManifestation;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class AnimationStruct implements Cloneable,Serializable{
	private static final long serialVersionUID = 1L;
	public AbstractQuestManifestation manifestation = new PhysicsManifestation();
	public long time;
	
	public AnimationStruct(){}
	public AnimationStruct(AbstractQuestManifestation manifestation, long time){
		this.manifestation = manifestation;
		this.time = time;
	}
	
	@Override public Object clone(){
		AnimationStruct clone = new AnimationStruct();
		clone.manifestation = manifestation.clone();
		clone.time = time;
		return clone;
	}
	
	@Override public String toString(){
		return "[AnimationStruct time: " + time + " manifestation: " + manifestation.toString() + "]";
	}
}
