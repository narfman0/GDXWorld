package com.blastedstudios.gdxworld.plugin.mode.animation.live;

import java.util.List;

import com.badlogic.gdx.utils.Array;
import com.blastedstudios.gdxworld.world.animation.GDXAnimation;

public class SelectboxGDXAnimationStruct {
	public final GDXAnimation animation;

	public SelectboxGDXAnimationStruct(GDXAnimation animation){
		this.animation = animation;
	}

	@Override public String toString(){
		return animation.getName();
	}
	
	public static Array<SelectboxGDXAnimationStruct> create(List<GDXAnimation> animations){
		SelectboxGDXAnimationStruct[] structs = new SelectboxGDXAnimationStruct[animations.size()];
		for(int i = 0; i<structs.length; i++)
			structs[i] = new SelectboxGDXAnimationStruct(animations.get(i));
		return new Array<SelectboxGDXAnimationStruct>(structs);
	}
}
