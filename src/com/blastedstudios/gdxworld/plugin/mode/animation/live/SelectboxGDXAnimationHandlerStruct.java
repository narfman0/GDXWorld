package com.blastedstudios.gdxworld.plugin.mode.animation.live;

import java.util.List;

import com.badlogic.gdx.utils.Array;
import com.blastedstudios.gdxworld.world.animation.GDXAnimationHandler;

public class SelectboxGDXAnimationHandlerStruct {
	public final GDXAnimationHandler handler;

	public SelectboxGDXAnimationHandlerStruct(GDXAnimationHandler handler){
		this.handler = handler;
	}

	@Override public String toString(){
		return "Handler for " + handler.getAnimations().getName();
	}
	
	public static Array<SelectboxGDXAnimationHandlerStruct> create(List<GDXAnimationHandler> handlers){
		SelectboxGDXAnimationHandlerStruct[] structs = new SelectboxGDXAnimationHandlerStruct[handlers.size()];
		for(int i = 0; i<structs.length; i++)
			structs[i] = new SelectboxGDXAnimationHandlerStruct(handlers.get(i));
		return new Array<SelectboxGDXAnimationHandlerStruct>(structs);
	}
}
