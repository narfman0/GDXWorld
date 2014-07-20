package com.blastedstudios.gdxworld.world.animation;

import java.io.Serializable;

import com.blastedstudios.gdxworld.plugin.quest.manifestation.physics.PhysicsManifestation;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class AnimationStruct implements Cloneable,Serializable{
	private static final long serialVersionUID = 1L;
	AbstractQuestManifestation manifestation = new PhysicsManifestation();
	String objectNames = "";
	long time;
	
	@Override public Object clone(){
		AnimationStruct clone = new AnimationStruct();
		clone.manifestation = manifestation.clone();
		clone.objectNames = objectNames;
		clone.time = time;
		return clone;
	}
}
